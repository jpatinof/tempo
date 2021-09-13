package com.b2bsg.common.util;


/**
 * Clase que contiene un comparados
 *
 * @author Edgar Prieto
 */
public class ComparatorUtils
{
	/**
	 * Retorna el valor del objeto de int.
	 *
	 * @param al_l1 correspondiente al valor del tipo de objeto long
	 * @param al_l2 correspondiente al valor del tipo de objeto long
	 * @return devuelve el valor de int
	 */
	public static int compare(long al_l1, long al_l2)
	{
		int  li_compare;
		long ll_compare;

		ll_compare = al_l1 - al_l2;

		if(ll_compare < 0L)
			li_compare = -1;
		else if(ll_compare > 0L)
			li_compare = 1;
		else
			li_compare = 0;

		return li_compare;
	}
}
