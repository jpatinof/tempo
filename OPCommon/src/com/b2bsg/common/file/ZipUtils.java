package com.b2bsg.common.file;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Iterator;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Clase que contiene todos las propiedades de ZipUtils para manejo de archivos comprimidos
 *
 * @author Edgar Prieto
 */
public class ZipUtils
{
	/** Constante BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 131072;

	/**
	 * Retorna el valor del objeto de byte[].
	 *
	 * @param aczeu_entries correspondiente al valor del tipo de objeto Collection<ZipEntryUtil>
	 * @return devuelve el valor de byte[]
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public static byte[] generateZip(Collection<ZipEntryUtil> aczeu_entries)
	    throws B2BException
	{
		byte[] lba_zip;

		if(CollectionUtils.isValidCollection(aczeu_entries))
		{
			try
			{
				ByteArrayOutputStream lbaos_zip;
				ZipOutputStream       lzos_destination;

				lbaos_zip            = new ByteArrayOutputStream();
				lzos_destination     = new ZipOutputStream(lbaos_zip);

				ZipUtils.compressEntries(lzos_destination, aczeu_entries);

				lba_zip = lbaos_zip.toByteArray();

				if((lba_zip != null) && (lba_zip.length == 0))
					lba_zip = null;
			}
			catch(Exception le_e)
			{
				throw new B2BException(le_e.getMessage());
			}
		}
		else
			lba_zip = null;

		return lba_zip;
	}

	/**
	 * Compress.
	 *
	 * @param azos_zip correspondiente al valor del tipo de objeto ZipOutputStream
	 * @param aisa_streamsToCompress correspondiente al valor del tipo de objeto InputStream[]
	 * @param asa_entryNames correspondiente al valor del tipo de objeto String[]
	 * @throws IOException Señala que se ha producido una excepción de E / S.
	 */
	private static void compress(
	    ZipOutputStream azos_zip, InputStream[] aisa_streamsToCompress, String[] asa_entryNames
	)
	    throws IOException
	{
		if((azos_zip != null) && (aisa_streamsToCompress != null))
		{
			byte[] lba_data;
			int    li_entryNamesLength;

			lba_data                = new byte[BUFFER_SIZE];
			li_entryNamesLength     = (asa_entryNames != null) ? asa_entryNames.length : 0;

			for(
			    int li_i = 0, li_streamsToCompressLength = aisa_streamsToCompress.length;
				    li_i < li_streamsToCompressLength; li_i++
			)
			{
				InputStream lis_stream;

				lis_stream = aisa_streamsToCompress[li_i];

				if(lis_stream != null)
				{
					String ls_entryName;

					ls_entryName = null;

					if(li_i < li_entryNamesLength)
						ls_entryName = asa_entryNames[li_i];

					if(!StringUtils.isValidString(ls_entryName))
					{
						StringBuilder lsb_sb;

						lsb_sb = new StringBuilder();

						lsb_sb.append("entry");
						lsb_sb.append(li_i);

						ls_entryName = lsb_sb.toString();
					}

					azos_zip.putNextEntry(new ZipEntry(ls_entryName));

					{
						boolean lb_markSupported;
						int     li_bufferLength;

						lb_markSupported = lis_stream.markSupported();

						if(lb_markSupported)
							lis_stream.mark(0);

						while((li_bufferLength = lis_stream.read(lba_data)) > 0)
							azos_zip.write(lba_data, 0, li_bufferLength);

						if(lb_markSupported)
							lis_stream.reset();

						lis_stream.close();
					}

					azos_zip.closeEntry();
				}
			}

			azos_zip.flush();
			azos_zip.close();
		}
	}

	/**
	 * Compress entries.
	 *
	 * @param azos_zip correspondiente al valor del tipo de objeto ZipOutputStream
	 * @param aczeu_entries correspondiente al valor del tipo de objeto Collection<ZipEntryUtil>
	 * @throws IOException Señala que se ha producido una excepción de E / S.
	 */
	private static void compressEntries(ZipOutputStream azos_zip, Collection<ZipEntryUtil> aczeu_entries)
	    throws IOException
	{
		if((azos_zip != null) && CollectionUtils.isValidCollection(aczeu_entries))
		{
			int                    li_entry;
			int                    li_entries;
			InputStream[]          lisa_streamsToCompress;
			Iterator<ZipEntryUtil> lizeu_entries;
			String[]               lsa_entryNames;

			li_entries                 = aczeu_entries.size();
			li_entry                   = 0;
			lizeu_entries              = aczeu_entries.iterator();
			lisa_streamsToCompress     = new InputStream[li_entries];
			lsa_entryNames             = new String[li_entries];

			while(lizeu_entries.hasNext())
			{
				ZipEntryUtil lzeu_entry;

				lzeu_entry = lizeu_entries.next();

				if(lzeu_entry != null)
				{
					InputStream lis_stream;
					String      ls_entryName;

					lis_stream       = lzeu_entry.getStream();
					ls_entryName     = lzeu_entry.getEntryName();

					if((lis_stream != null) && StringUtils.isValidString(ls_entryName))
					{
						lisa_streamsToCompress[li_entry]     = lis_stream;
						lsa_entryNames[li_entry]             = ls_entryName;

						li_entry++;
					}
				}
			}

			if(li_entry > 0)
				compress(azos_zip, lisa_streamsToCompress, lsa_entryNames);
		}
	}
}
