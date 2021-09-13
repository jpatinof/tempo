package com.b2bsg.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


/**
 * Clase de utilidades para el manejo de colecciones.
 *
 * @author Fredy Posada
 */
public class CollectionUtils
{
	/**
	 * Retorna el valor de data source.
	 *
	 * @param am_m correspondiente al valor del tipo de objeto Map
	 * @param ao_key correspondiente al valor del tipo de objeto Object
	 * @return el valor de data source
	 */
	public static DataSource getDataSource(Map am_m, Object ao_key)
	{
		return ConverterUtils.getDataSource(getObject(am_m, ao_key));
	}

	/**
	 * Retorna Objeto o variable de valor mail session.
	 *
	 * @param am_m de am m
	 * @param ao_key de ao key
	 * @return el valor de mail session
	 */
	public static javax.mail.Session getMailSession(Map am_m, Object ao_key)
	{
		return ConverterUtils.getMailSession(getObject(am_m, ao_key));
	}

	/**
	 * Retorna Objeto o variable de valor object.
	 *
	 * @param ai_i de ai i
	 * @return el valor de object
	 */
	public static Object getObject(Iterator ai_i)
	{
		return (ai_i != null) ? ai_i.next() : null;
	}

	/**
	 * Retorna Objeto o variable de valor string.
	 *
	 * @param ai_i de ai i
	 * @return el valor de string
	 */
	public static String getString(Iterator ai_i)
	{
		return ConverterUtils.getString(getObject(ai_i));
	}

	/**
	 * Retorna Objeto o variable de valor string.
	 *
	 * @param ao_o de ao o
	 * @return el valor de string
	 */
	public static String getString(Object ao_o)
	{
		return ((ao_o != null) && ao_o instanceof String) ? (String)ao_o : null;
	}

	/**
	 * Determinar si un objeto <code>java.util.Collection</code> está instanciado y no es vacío
	 *
	 * @author        Edgar Prieto
	 * @param ac_c    <code>java.util.Collection</code> a validar
	 * @return        <code>true</code> si <i>ac_c</i> no es null y tiene elementos.
	 *                <code>false</code> de lo contrario
	 */
	public static boolean isValidCollection(Collection ac_c)
	{
		return (ac_c != null) && !ac_c.isEmpty();
	}

	/**
	 * Valida la propiedad valid collection.
	 *
	 * @param am_m correspondiente al valor del tipo de objeto Map
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid collection
	 */
	public static boolean isValidCollection(Map am_m)
	{
		return (am_m != null) && !am_m.isEmpty();
	}

	/**
	 * Valida la propiedad valid collection.
	 *
	 * @param loa_a correspondiente al valor del tipo de objeto Object[]
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid collection
	 */
	public static boolean isValidCollection(Object[] loa_a)
	{
		return (loa_a != null) && (loa_a.length > 0);
	}

	/**
	 * Retorna el valor del objeto de Collection.
	 *
	 * @param ac_collection correspondiente al valor del tipo de objeto Collection
	 * @return devuelve el valor de Collection
	 *
	 */
	public static Collection sort(Collection ac_collection)
	{
		return sort(ac_collection, null, false);
	}

	/**
	 * Retorna el valor de object.
	 *
	 * @param am_m correspondiente al valor del tipo de objeto Map
	 * @param ao_key correspondiente al valor del tipo de objeto Object
	 * @return el valor de object
	 */
	private static Object getObject(Map am_m, Object ao_key)
	{
		return ((am_m != null) && (ao_key != null)) ? am_m.get(ao_key) : null;
	}

	/**
	 * Retorna el valor del objeto de Collection.
	 *
	 * @param ac_collection correspondiente al valor del tipo de objeto Collection
	 * @param ac_comparator correspondiente al valor del tipo de objeto Comparator
	 * @param ab_reverse correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de Collection
	 *
	 */
	private static Collection sort(Collection ac_collection, Comparator ac_comparator, boolean ab_reverse)
	{
		List ll_list;

		ll_list = null;

		if(isValidCollection(ac_collection))
		{
			if(ac_collection instanceof List)
				ll_list = (List)ac_collection;
			else
				ll_list = Collections.synchronizedList(new java.util.ArrayList(ac_collection));

			if(ac_comparator != null)
				Collections.sort(ll_list, ac_comparator);
			else
				Collections.sort(ll_list);

			if(ab_reverse)
				Collections.reverse(ll_list);
		}

		return ll_list;
	}
}
