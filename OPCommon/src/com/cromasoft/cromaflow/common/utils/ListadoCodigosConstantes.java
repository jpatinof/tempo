package com.cromasoft.cromaflow.common.utils;

import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase para generar codigos separados por coma en un mapa de strings.
 *
 * @author ccalderon
 */
public class ListadoCodigosConstantes
{
	/**
	 *  Método encargado de listar los codigos separados por coma en un mapa de strings.
	 *
	 * @param as_caracter Nombre de la constante a consultar.
	 * @return mapa de strings con los codigos consultados.
	 */
	public static Map<String, String> generarCodigos(String as_caracter)
	{
		Map<String, String> lmss_mss;
		lmss_mss = new HashMap<String, String>();

		if(StringUtils.isValidString(as_caracter))
		{
			String[] lsa_codigos;

			lsa_codigos = as_caracter.split(",");

			if(CollectionUtils.isValidCollection(lsa_codigos))
			{
				for(String ls_iterador : lsa_codigos)
				{
					if(StringUtils.isValidString(ls_iterador))
						lmss_mss.put(ls_iterador, ls_iterador);
				}
			}
		}

		return lmss_mss;
	}

	/**
	 * Método de consulta para separar la constante dentro de una colección.
	 *
	 * @param as_caracter de as caracter
	 * @return el valor de collection
	 */
	public static Collection<String> generarCodigosCollection(String as_caracter)
	{
		return generarCodigosCollection(as_caracter, ",");
	}

	/**
	 * Método de consulta para separar la constante dentro de una colección.
	 *
	 * @param as_caracter de as caracter
	 * @param as_separador Argumento de tipo <code>String</code> que contiene el criterio separador.
	 * @return el valor de collection
	 */
	public static Collection<String> generarCodigosCollection(String as_caracter, String as_separador)
	{
		Collection<String> lmss_mss;

		lmss_mss = new ArrayList<String>();

		if(StringUtils.isValidString(as_caracter))
		{
			String[] lsa_codigos;

			lsa_codigos = as_caracter.split(as_separador);

			if(CollectionUtils.isValidCollection(lsa_codigos))
			{
				for(String ls_iterador : lsa_codigos)
				{
					if(StringUtils.isValidString(ls_iterador))
						lmss_mss.add(ls_iterador);
				}
			}
		}

		return lmss_mss;
	}
}
