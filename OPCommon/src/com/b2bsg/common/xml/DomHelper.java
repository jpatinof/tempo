package com.b2bsg.common.xml;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.BitUtils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

import org.xml.sax.InputSource;

import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Clase para la represetación de arbol de objetos de una cadena de texto en formato XML
 * @author Edgar
 */
public class DomHelper
{
	private static final LoggerHandler clh_LOGGER   = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(DomHelper.class);
	private static Map                 im_builders  = new Hashtable();
	private static Map                 im_factories = new Hashtable();

	/**
	 * Obtiene una represetación org.w3c.dom.Document a partir de un XML en formato texto
	 * @param as_xml XML en fomrato texto
	 */
	public static Document getDocument(String as_xml)
	{
		try
		{
			DocumentBuilder        ldb_builder;
			DocumentBuilderFactory ldbf_builderFactory;

			synchronized(im_builders)
			{
				String ls_key;

				{
					StringBuffer lsb_key;

					lsb_key = new StringBuffer();

					lsb_key.append(2);

					ls_key = lsb_key.toString();
				}

				ldb_builder = (DocumentBuilder)im_builders.get(ls_key);

				if(ldb_builder == null)
				{
					ldbf_builderFactory     = getDocumentBuilderFactory(2);
					ldb_builder             = ldbf_builderFactory.newDocumentBuilder();

					im_builders.put(ls_key, ldb_builder);
				}
			}

			return ldb_builder.parse(new InputSource(new java.io.StringReader(as_xml)));
		}
		catch(Exception le_e)
		{
			String ls_message;

			ls_message = le_e.getMessage();

			clh_LOGGER.error("getDocument", le_e);

			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, ls_message);
		}
	}

	/**
	 * Obtiene un constructor de documentos XML, configurado según el tipo
	 * @param ai_type Modo de coniguracion del constructor de documentos XML
	 * */
	private static DocumentBuilderFactory getDocumentBuilderFactory(int ai_type)
	{
		DocumentBuilderFactory ldbf_factory;
		String                 ls_key;

		{
			StringBuffer lsb_key;

			lsb_key = new StringBuffer();

			lsb_key.append(ai_type);

			ls_key = lsb_key.toString();
		}

		synchronized(im_factories)
		{
			ldbf_factory = (DocumentBuilderFactory)im_factories.get(ls_key);

			if(ldbf_factory == null)
			{
				ldbf_factory = DocumentBuilderFactory.newInstance();

				ldbf_factory.setCoalescing(BitUtils.isFlagSet(ai_type, 1));
				ldbf_factory.setIgnoringComments(BitUtils.isFlagSet(ai_type, 3));
				ldbf_factory.setNamespaceAware(BitUtils.isFlagSet(ai_type, 16));

				ldbf_factory.setExpandEntityReferences(BitUtils.isFlagSet(ai_type, 2));

				ldbf_factory.setIgnoringElementContentWhitespace(BitUtils.isFlagSet(ai_type, 8));

				{
					boolean lb_validate;

					lb_validate = BitUtils.isFlagSet(ai_type, 32);

					ldbf_factory.setValidating(lb_validate);

					if(lb_validate)
						ldbf_factory.setAttribute("http://apache.org/xml/features/validation/schema", Boolean.TRUE);
				}

				im_factories.put(ls_key, ldbf_factory);
			}
		}

		return ldbf_factory;
	}
}
