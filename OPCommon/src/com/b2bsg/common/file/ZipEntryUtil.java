package com.b2bsg.common.file;

import java.io.InputStream;


/**
 * Clase que contiene todos las propiedades de ZipEntryUtil para manejo de archivos comprimidos
 *
 * @author Edgar Prieto
 */
public class ZipEntryUtil implements java.io.Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = -8678200654883674586L;

	/** Propiedad iis stream. */
	private InputStream iis_stream;

	/** Propiedad is entry name. */
	private String is_entryName;

	/**
	 * Instancia un nuevo objeto zip entry util.
	 *
	 * @param as_entryName correspondiente al valor del tipo de objeto String
	 * @param ais_stream correspondiente al valor del tipo de objeto InputStream
	 */
	public ZipEntryUtil(String as_entryName, InputStream ais_stream)
	{
		setEntryName(as_entryName);
		setStream(ais_stream);
	}

	/**
	 * Retorna el valor de entry name.
	 *
	 * @return el valor de entry name
	 */
	String getEntryName()
	{
		return is_entryName;
	}

	/**
	 * Retorna el valor de stream.
	 *
	 * @return el valor de stream
	 */
	InputStream getStream()
	{
		return iis_stream;
	}

	/**
	 * Modifica el valor de entry name.
	 *
	 * @param as_s asigna el valor a la propiedad entry name
	 */
	private void setEntryName(String as_s)
	{
		is_entryName = as_s;
	}

	/**
	 * Modifica el valor de stream.
	 *
	 * @param ais_is asigna el valor a la propiedad stream
	 */
	private void setStream(InputStream ais_is)
	{
		iis_stream = ais_is;
	}
}
