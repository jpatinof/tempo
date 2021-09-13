package com.b2bsg.common.util;

import com.cromasoft.cromaflow.constants.EstadoCommon;
import com.cromasoft.cromaflow.constants.IdentificadoresCommon;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Clase de utilidades para el manejo de cadenas.
 *
 * @author Edgar Prieto
 */
public class StringUtils
{
	/**
	 * Retorna Objeto o variable de valor normalized boolean value.
	 *
	 * @param as_s de as s
	 * @return el valor de normalized true string
	 */
	public static String getNormalizedBooleanValue(String as_s)
	{
		String ls_string;

		ls_string = null;

		if(isValidString(as_s))
		{
			if(
			    as_s.equalsIgnoreCase("Y") || as_s.equalsIgnoreCase("YES") || as_s.equalsIgnoreCase("S")
				    || as_s.equalsIgnoreCase("SI") || as_s.equalsIgnoreCase("SÍ")
			)
				ls_string = "S";

			if(as_s.equalsIgnoreCase("N") || as_s.equalsIgnoreCase("NO"))
				ls_string = "N";
		}

		return ls_string;
	}

	/**
	  * Método para saber si una cadena de String está únicamente compuesta por números
	  *
	  * @param as_s de as s
	  * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en numeric
	  * @author Carlos Calderon
	  */
	public static boolean isNumeric(String as_s)
	{
		boolean lb_return;

		try
		{
			Integer.parseInt(as_s);
			lb_return = true;
		}
		catch(NumberFormatException lnfe_e)
		{
			lb_return = false;
		}

		return lb_return;
	}

	/**
	 * Método encargado de generar un select dinamico en el cual se consulta un campo de una tabla especifica.
	 *
	 * @param as_tabla de as tabla.
	 * @param as_campo de as campo.
	 * @param as_filtro de as filtro.
	 * @param ao_filtro de ao filtro.
	 * @return Select generado.
	 */
	public static String getSelectOne(
	    String as_tabla, String as_schema, String as_campo, String as_filtro, Object ao_filtro
	)
	{
		String ls_return;

		ls_return = null;

		if(StringUtils.isValidString(as_filtro) && (ao_filtro != null))
		{
			Map<String, Object> lmso_filtros;

			lmso_filtros = new HashMap<String, Object>();

			lmso_filtros.put(as_filtro, ao_filtro);

			ls_return = getSelectOne(as_tabla, as_campo, as_schema, lmso_filtros);
		}

		return ls_return;
	}

	/**
	 * Obtener un objeto <code>java.lang.String</code> a partir de un objeto
	 * <code>java.util.Date</code> dado un formato de representación.
	 *
	 * @author Edgar Prieto
	 * @param ad_d <code>java.util.Date</code> a convertir.
	 * @param as_format        Formato de representación de fecha.
	 * @return Un objeto <code>java.lang.String</code> representando a <i>ad_d </i> en el formato
	 *         <i>as_format </i>. <code>null</code> en caso de que la conversión no se pueda
	 *         realizar.
	 */
	public static String getString(Date ad_d, String as_format)
	{
		return getString(ad_d, as_format, Locale.getDefault());
	}

	/**
	 * Obtener el valor la representación <code>String</code> de un <code>boolean</code>.
	 *
	 * @author Jorge Patiño
	 * @param ab_b    <code>boolean</code> el cual será interpretado como un <code>String</code>.
	 * @return        <b>S</b> si ab_b es <code>true</code> y <b>N</b> si ab_b es <code>false</code>
	 */
	public static String getString(boolean ab_b)
	{
		return ab_b ? EstadoCommon.S : EstadoCommon.N;
	}

	/**
	 * Retorna el valor de string.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Date
	 * @param as_format correspondiente al valor del tipo de objeto String
	 * @param al_locale correspondiente al valor del tipo de objeto Locale
	 * @return el valor de string
	 */
	public static String getString(Date ad_d, String as_format, Locale al_locale)
	{
		String ls_date;

		if((ad_d != null) && isValidString(as_format))
		{
			SimpleDateFormat lsdf_sdf;

			/*
			 * Obtener un objeto para el formato de fachas. La operación de formato debe ser
			 * estricta
			 */
			lsdf_sdf = new SimpleDateFormat(as_format, (al_locale != null) ? al_locale : Locale.getDefault());

			lsdf_sdf.setLenient(false);

			try
			{
				StringBuilder lsb_date;

				/* Obtener la cadena que representa a la fecha dada */
				lsb_date     = new StringBuilder(
					    lsdf_sdf.format(ad_d, new StringBuffer(), new FieldPosition(DateFormat.YEAR_FIELD))
					);

				ls_date = lsb_date.toString();
			}
			catch(Exception le_e)
			{
				ls_date = null;
			}
		}
		else
			ls_date = null;

		return ls_date;
	}

	/**
	 * Convertir un <code>double</code> en un objeto <code>java.lang.String</code>.
	 *
	 * @author Edgar Prieto
	 * @param ad_d <code>double</double> a convertir.
	 * @return    <code>java.lang.String</code> con formato xxxxxxxxx.yy representando a <i>ad_d</i>.
	 */
	public static String getString(double ad_d)
	{
		DecimalFormat ldf_f;

		ldf_f = new DecimalFormat();

		/* El indicador de separadores siempre debe estar presente */
		ldf_f.setDecimalSeparatorAlwaysShown(false);

		/* No utilizar agrupamiento */
		ldf_f.setGroupingUsed(false);

		return ldf_f.format(ad_d);
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y después del
	 *         contenido de la cadena. <code>null</code> si <i>as_s </i> es <code>null</code>.
	 */
	public static String getString(String as_s)
	{
		return (as_s == null) ? null : as_s.trim();
	}

	/**
	 * Retorna el valor de string.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Object
	 * @return el valor de string
	 */
	public static String getString(Object as_s)
	{
		return (as_s == null) ? null : as_s.toString();
	}

	/**
	 * Obtiene una cadena de caracteres y la almacena en un arreglo.
	 *
	 * @param as_s Cadena de caracteres a almacenar en un arreglo
	 * @return arreglo de salida contenedor de la cadena de caracteres
	 */
	public static String[] getStringArray(String as_s)
	{
		String[] lsa_sa;

		if(StringUtils.isValidString(as_s))
		{
			lsa_sa        = new String[1];
			lsa_sa[0]     = as_s;
		}
		else
			lsa_sa = null;

		return lsa_sa;
	}

	/**
	 * Retorna el valor de string array.
	 *
	 * @param as_string correspondiente al valor del tipo de objeto String
	 * @param as_separador correspondiente al valor del tipo de objeto String
	 * @return el valor de string array
	 */
	public static String[] getStringArray(String as_string, String as_separador)
	{
		String[] lsa_return;

		lsa_return = null;

		if(
		    StringUtils.isValidString(as_string) && StringUtils.isValidString(as_separador)
			    && as_string.contains(as_separador)
		)
			lsa_return = as_string.split(as_separador);

		return lsa_return;
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code> y convertir los caracteres restantes a su representación en
	 * minúsculas.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y después del
	 *         contenido de la cadena y con los caracteres restantes convertidos a su representación
	 *         en minúsculas. <code>null</code> si <i>as_s </i> es <code>null</code>.
	 */
	public static String getStringLowerCase(String as_s)
	{
		return (as_s == null) ? null : as_s.trim().toLowerCase();
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>. Reemplazar todas las secuencias de caracteres en blanco
	 * intermedias y reeplazarlas por un caracter espacio. Convertir todos los caracteres a su
	 * representación en mayúsculas. Reemplazar las ocurrencias de vocales acentuadas por su
	 * respectiva vocal sin acento.
	 *
	 * @author Edgar Prieto
	 * @param as_s        <code>java.lang.String</code> a normalizar.
	 * @return <code>java.lang.String</code> normalizada. <code>null</code> si <i>as_s </i> es
	 *         <code>null</code>.
	 */
	public static String getStringNormalized(String as_s)
	{
		String ls_s;

		if((ls_s = getStringTrim(as_s)) != null)
		{
			ls_s     = ls_s.toUpperCase();
			ls_s     = ls_s.replace('À', 'A');
			ls_s     = ls_s.replace('Á', 'A');
			ls_s     = ls_s.replace('Â', 'A');
			ls_s     = ls_s.replace('Ã', 'A');
			ls_s     = ls_s.replace('Ä', 'A');
			ls_s     = ls_s.replace('Å', 'A');
			ls_s     = ls_s.replace('È', 'E');
			ls_s     = ls_s.replace('É', 'E');
			ls_s     = ls_s.replace('Ê', 'E');
			ls_s     = ls_s.replace('Ë', 'E');
			ls_s     = ls_s.replace('Ì', 'I');
			ls_s     = ls_s.replace('Í', 'I');
			ls_s     = ls_s.replace('Î', 'I');
			ls_s     = ls_s.replace('Ï', 'I');
			ls_s     = ls_s.replace('Ò', 'O');
			ls_s     = ls_s.replace('Ó', 'O');
			ls_s     = ls_s.replace('Ô', 'O');
			ls_s     = ls_s.replace('Õ', 'O');
			ls_s     = ls_s.replace('Ö', 'O');
			ls_s     = ls_s.replace('Ù', 'U');
			ls_s     = ls_s.replace('Ú', 'U');
			ls_s     = ls_s.replace('Û', 'U');
			ls_s     = ls_s.replace('Ü', 'U');
		}

		return ls_s;
	}

	/**
	 * Retorna el valor de string not empty.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de string not empty
	 */
	public static String getStringNotEmpty(String as_s)
	{
		String ls_s;

		ls_s = getStringNotNull(as_s);

		if(ls_s.length() < 1)
			ls_s = " ";

		return ls_s;
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y después del
	 *         contenido de la cadena. Objeto <code>java.lang.String</code> sin contenido si
	 *         <i>as_s </i> es <code>null</code>.
	 */
	public static String getStringNotNull(String as_s)
	{
		return (as_s == null) ? new String() : as_s.trim();
	}

	/**
	 * Retorna Objeto o variable de valor string of collection.
	 *
	 * @param acs_coleccion de acs coleccion
	 * @return el valor de string of collection
	 */
	public static String getStringOfCollection(Collection<String> acs_coleccion)
	{
		String ls_return;

		ls_return = null;

		if(CollectionUtils.isValidCollection(acs_coleccion))
		{
			StringBuilder lsb_builder;
			int           li_contador;

			lsb_builder     = new StringBuilder();
			li_contador     = 0;

			for(String ls_texto : acs_coleccion)
			{
				if(StringUtils.isValidString(ls_texto))
				{
					lsb_builder.append(ls_texto);

					if(li_contador < (acs_coleccion.size() - 1))
						lsb_builder.append(IdentificadoresCommon.SIMBOLO_COMA);

					li_contador++;
				}
			}

			ls_return = lsb_builder.toString();
		}

		return ls_return;
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code> y convertir los caracteres restantes a su representación en
	 * mayúsculas.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y después del
	 *         contenido de la cadena y con los caracteres restantes convertidos a su representación
	 *         en mayúsculas. <code>null</code> si <i>as_s </i> es <code>null</code>
	 */
	public static String getStringUpperCase(String as_s)
	{
		return (as_s == null) ? null : as_s.trim().toUpperCase();
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code> y convertir los caracteres restantes a su representación en
	 * mayúsculas.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y después del
	 *         contenido de la cadena y con los caracteres restantes convertidos a su representación
	 *         en mayúsculas. <code>null</code> si <i>as_s </i> es <code>null</code>
	 */
	public static String getStringUpperCaseWithoutSpaces(String as_s)
	{
		return (as_s == null) ? null : as_s.trim().toUpperCase().replace(" ", "_");
	}

	/**
	 * Retorna Objeto o variable de valor string with first letter in upper case.
	 *
	 * @param as_s de as s
	 * @return el valor de string with first letter in upper case
	 */
	public static String getStringWithFirstLetterInUpperCase(String as_s)
	{
		String ls_string;

		ls_string = getStringLowerCase(as_s);

		if(isValidString(ls_string))
		{
			String ls_firstCharacter;

			ls_firstCharacter = ls_string.substring(0, 1);

			if(isValidString(ls_firstCharacter))
				ls_string = getStringUpperCase(ls_firstCharacter) + ls_string.substring(1);
		}

		return ls_string;
	}

	/**
	 * Retorna el valor de true string.
	 *
	 * @return el valor de true string
	 */
	public static String getTrueString()
	{
		return Boolean.TRUE.toString();
	}

	/**
	 * Valida la propiedad upper case.
	 *
	 * @param as_character de as character
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en upper case
	 */
	public static boolean isUpperCase(String as_character)
	{
		return as_character.equals(as_character.toUpperCase());
	}

	/**
	 * Valida la propiedad valid characters.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @param as_caracters correspondiente al valor del tipo de objeto String
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid characters
	 */
	public static boolean isValidCharacters(String as_s, String as_caracters)
	{
		boolean lb_b;
		lb_b = true;

		if(isValidString(as_s) && isValidString(as_caracters))
		{
			for(int li_i = 0, li_lenght = as_caracters.length(); li_i < li_lenght; li_i++)
			{
				if(as_s.contains(String.valueOf(as_caracters.charAt(li_i))))
				{
					lb_b = false;

					break;
				}
			}
		}

		return lb_b;
	}

	/**
	 * Determina si un objeto <code>java.lang.String</code> es <code>null</code> o si no
	 * contiene caracteres diferentes a caracteres en blanco.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a validar.
	 * @return <code>true</code> si <i>as_s </i> tiene caracteres diferentes a caracteres en
	 *         blanco o <code>false</code> si <i>as_s </i> es <code>null</code> o no tiene
	 *         caracteres diferentes a caracteres en blanco.
	 */
	public static boolean isValidString(String as_s)
	{
		return (as_s != null) && (as_s.trim().length() > 0);
	}

	/**
	 * Clean broken tags of document.
	 *
	 * @param as_document de as document
	 * @return el valor de string
	 * @author Jorge Esteban Patiño Fonseca
	 */
	public static synchronized String cleanBrokenTagsOfDocument(String as_document)
	{
		String ls_documentFixed;

		ls_documentFixed = null;

		if(isValidString(as_document))
		{
			boolean       lb_activateSearch;
			boolean       lb_canBeANumber;
			String[]      lsa_documentArray;
			StringBuilder lsb_cleanDocument;

			lb_activateSearch     = false;
			lb_canBeANumber       = false;
			lsa_documentArray     = as_document.split("");
			lsb_cleanDocument     = new StringBuilder();

			if(CollectionUtils.isValidCollection(lsa_documentArray))
			{
				for(int li_iterator = 0, li_length = lsa_documentArray.length; li_iterator < li_length;
					    li_iterator++
				)
				{
					String ls_character;

					ls_character = lsa_documentArray[li_iterator];

					if(ls_character != null)
					{
						if(lb_activateSearch)
						{
							if(lb_canBeANumber && ls_character.equals("}"))
							{
								String ls_numbers;

								ls_numbers = returnNumbers(lsa_documentArray, li_iterator, 1, true);

								if(isValidString(ls_numbers))
									lsb_cleanDocument.append(ls_numbers);
							}

							if(
							    !ls_character.equals("/") && !ls_character.equals("\\") && !ls_character.equals("{")
								    && !ls_character.equals("}") && !ls_character.equals(" ")
								    && !StringUtils.isNumeric(ls_character) && !ls_character.equals("\n")
								    && !ls_character.equals("\r")
							)
							{
								if(ls_character.equals("_"))
								{
									lsb_cleanDocument.append(ls_character);

									{
										String ls_numbers;

										ls_numbers = returnNumbers(lsa_documentArray, li_iterator, 1, false);

										if(isValidString(ls_numbers))
											lsb_cleanDocument.append(ls_numbers);
										else
											lb_canBeANumber = true;
									}
								}
								else if(ls_character.equals(">"))
								{
									lsb_cleanDocument.append(ls_character);
									lb_activateSearch     = false;
									lb_canBeANumber       = false;
								}
								else if(isUpperCase(ls_character))
								{
									lsb_cleanDocument.append(ls_character);
									lb_canBeANumber = false;
								}
							}
						}
						else if(ls_character.equals("<"))
						{
							lb_activateSearch = true;
							lsb_cleanDocument.append(ls_character);
						}
						else
							lsb_cleanDocument.append(ls_character);
					}
				}

				ls_documentFixed = lsb_cleanDocument.toString();
			}
		}

		return ls_documentFixed;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param as_cadena correspondiente al valor del tipo de objeto String
	 * @return devuelve el valor de String
	 */
	public static String reemplazarCaracteresUTF8(String as_cadena)
	{
		if(StringUtils.isValidString(as_cadena))
		{
			as_cadena     = as_cadena.replace("Á", "\\u193\\'3f");
			as_cadena     = as_cadena.replace("É", "\\u201\\'3f");
			as_cadena     = as_cadena.replace("Í", "\\u205\\'3f");
			as_cadena     = as_cadena.replace("Ó", "\\u211\\'3f");
			as_cadena     = as_cadena.replace("Ú", "\\u218\\'3f");
			as_cadena     = as_cadena.replace("Ñ", "\\u209\\'3f");
			as_cadena     = as_cadena.replace("Ü", "\\u220\\'3f");
			as_cadena     = as_cadena.replace("á", "\\u225\\'3f");
			as_cadena     = as_cadena.replace("é", "\\u233\\'3f");
			as_cadena     = as_cadena.replace("í", "\\u237\\'3f");
			as_cadena     = as_cadena.replace("ó", "\\u243\\'3f");
			as_cadena     = as_cadena.replace("ú", "\\u250\\'3f");
			as_cadena     = as_cadena.replace("ñ", "\\u241\\'3f");
			as_cadena     = as_cadena.replace("ü", "\\u252\\'3f");
		}

		return as_cadena;
	}

	/**
	 * Método encargado de generar un select dinamico en el cual se consulta un campo de una tabla especifica.
	 *
	 * @param as_tabla de as tabla.
	 * @param as_campo de as campo.
	 * @param amso_filtros de am filtros.
	 * @return Select generado.
	 */
	private static String getSelectOne(
	    String as_tabla, String as_campo, String as_schema, Map<String, Object> amso_filtros
	)
	{
		StringBuilder lsb_return;

		lsb_return = new StringBuilder();

		if(
		    StringUtils.isValidString(as_tabla) && StringUtils.isValidString(as_campo)
			    && CollectionUtils.isValidCollection(amso_filtros) && StringUtils.isValidString(as_schema)
		)
		{
			int li_count;

			li_count = 1;

			lsb_return.append("SELECT ");
			lsb_return.append(as_campo);
			lsb_return.append(" FROM ");
			lsb_return.append(as_schema);
			lsb_return.append(as_tabla);
			lsb_return.append(" WHERE ");

			for(Map.Entry<String, Object> lm_entry : amso_filtros.entrySet())
			{
				if(li_count > 1)
					lsb_return.append(" AND ");

				lsb_return.append(lm_entry.getKey());
				lsb_return.append("=");
				lsb_return.append(lm_entry.getValue());

				li_count++;
			}
		}

		return lsb_return.toString().isEmpty() ? null : lsb_return.toString();
	}

	/**
	 * Eliminar los caracteres en blanco iniciales y finales de un objeto
	 * <code>java.lang.String</code>. Reemplazar todas las secuencias de caracteres en blanco
	 * intermedias y reeplazarlas por un caracter espacio.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> a eliminar los caracteres en blanco.
	 * @return <code>java.lang.String</code> sin caracteres en blanco antes y después del
	 *         contenido de la cadena y las secuencias intermedias de caracteres en blanco
	 *         reemplazadas por el caracter espacio. <code>null</code> si <i>as_s </i> es
	 *         <code>null</code>.
	 */
	private static String getStringTrim(String as_s)
	{
		String ls_s;

		ls_s = null;

		if(as_s != null)
		{
			boolean       lb_hasSpace;
			char          lc_c;
			StringBuilder lsb_sb;

			lb_hasSpace     = false;
			lsb_sb          = new StringBuilder(getString(as_s));

			for(int li_i = 0; li_i < lsb_sb.length();)
			{
				lc_c = lsb_sb.charAt(li_i);

				if(Character.isWhitespace(lc_c) || Character.isSpaceChar(lc_c))
				{
					if(lb_hasSpace)
						lsb_sb.deleteCharAt(li_i);
					else
					{
						lsb_sb.replace(li_i, li_i + 1, " ");
						li_i++;
					}

					lb_hasSpace = true;
				}
				else
				{
					li_i++;
					lb_hasSpace = false;
				}
			}

			ls_s = lsb_sb.toString();
		}

		return ls_s;
	}

	/**
	 * Builds the numbers list.
	 *
	 * @param asa_documentArray de asa document array
	 * @param ai_iterator de ai iterator
	 * @param ai_counter de ai counter
	 * @param lls_numbers de lls numbers
	 * @return el valor de list
	 * @author Jorge Esteban Patiño Fonseca
	 *      */
	private static List<String> buildNumbersList(
	    String[] asa_documentArray, int ai_iterator, int ai_counter, List<String> lls_numbers, boolean ab_backWard
	)
	{
		String ls_characterNumber;

		ls_characterNumber = asa_documentArray[ab_backWard ? (ai_iterator - ai_counter) : (ai_iterator + ai_counter)];

		if(isNumeric(ls_characterNumber))
		{
			lls_numbers.add(ls_characterNumber);
			buildNumbersList(asa_documentArray, ai_iterator, ai_counter + 1, lls_numbers, ab_backWard);
		}

		return lls_numbers;
	}

	/**
	 * Return numbers.
	 *
	 * @param asa_documentArray de asa document array
	 * @param ai_iterator de ai iterator
	 * @param ai_counter de ai counter
	 * @return el valor de string
	 * @author Jorge Esteban Patiño Fonseca
	 */
	private static String returnNumbers(
	    String[] asa_documentArray, int ai_iterator, int ai_counter, boolean lb_backWard
	)
	{
		List<String>  lls_numbers;
		StringBuilder lsb_string;

		lls_numbers     = buildNumbersList(
			    asa_documentArray, ai_iterator, ai_counter, new ArrayList<String>(1), lb_backWard
			);
		lsb_string      = new StringBuilder();

		if(CollectionUtils.isValidCollection(lls_numbers))
		{
			if(lb_backWard)
				Collections.reverse(lls_numbers);

			for(String ls_string : lls_numbers)
			{
				if(isValidString(ls_string))
					lsb_string.append(ls_string);
			}
		}

		return lsb_string.toString();
	}
}
