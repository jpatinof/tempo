package com.cromasoft.cromaflow.common.utils;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.util.StringUtils;


/**
 * Clase para convertir textos
 * @author jpatino
 *
 */
public class ConversionTextos
{
	/**
	 * Convertir texto salto linea.
	 *
	 * @param as_texto de as texto
	 * @return el valor de string
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public static String convertirTextoSaltoLinea(String as_texto)
	    throws B2BException
	{
		if(StringUtils.isValidString(as_texto))
		{
			if(as_texto.contains("\n"))
				as_texto = as_texto.replace("\n", "{\\pard\\qj \\par}");
		}

		return as_texto;
	}

	/**
	 *
	 * @param as_texto parametro con la cadena String que será alterada
	 * @param as_caracterAReemplazar caracter que será reemplazado
	 * @param as_nuevoCaracter caracter que será introducido a la cadena String
	 * @return String reemplazado
	 * @throws B2BException
	 */
	public static String convertirTextos(String as_texto, String as_caracterAReemplazar, String as_nuevoCaracter)
	    throws B2BException
	{
		String ls_return;

		ls_return = null;

		if(
		    StringUtils.isValidString(as_texto) && StringUtils.isValidString(as_caracterAReemplazar)
			    && as_texto.contains(as_caracterAReemplazar)
		)
			ls_return = as_texto.replace(as_caracterAReemplazar, as_nuevoCaracter);
		else
			ls_return = as_texto;

		return ls_return;
	}

	/**
	 * método que convierte el texto para ser presentado en pantalla
	 * @param as_texto texto a ser modificado
	 * @return String modificado
	 * @throws B2BException
	 */
	public static String convertirTextosFormatosCertificados(String as_texto)
	    throws B2BException
	{
		if(StringUtils.isValidString(as_texto))
		{
			if(as_texto.contains("\\par}{\\pard\\qj"))
				as_texto = as_texto.replace("\\par}{\\pard\\qj", "<br/>");

			if(as_texto.contains("{\\pard\\qj \\par}"))
				as_texto = as_texto.replace("{\\pard\\qj \\par}", "<br/>");

			if(as_texto.contains("{\\pard\\qj"))
				as_texto = as_texto.replace("{\\pard\\qj", "");

			if(as_texto.contains("\\par}"))
				as_texto = as_texto.replace("\\par}", "");

			if(as_texto.contains("\n"))
				as_texto = as_texto.replace("\n", "<br/>");
		}

		return as_texto;
	}
}
