package com.b2bsg.common.util;


/**
* Clase de utilidades para el manejo de bits
* @author Edgar Prieto
*/
public class BitUtils
{
	/**
	* Ajusta si el valor del <i>bit</i> <i>ai_bit</i> de la representación binaria de
	* <i>ai_value</i> en el valor indicado por <i>ab_set</i>.
	* @param    ai_value    <code>int</code> ajustar valor del bit.
	* @param    ai_bit        Bit a ser ajustado.
	* @param    ab_set        Valor del valor.
	* @return    <code>int</code> representado el valor binario de <i>ai_value</i> con el <i>bit</i>
	*            <i>ai_bit</i> ajustado en el valor <i>ab_set</i>.
	* @author Edgar Prieto
	*/
	public static int setBit(int ai_value, int ai_bit, boolean ab_set)
	{
		int li_value;

		li_value = ai_value;

		if(isBitSet(ai_value, ai_bit) != ab_set)
			li_value += NumericUtils.getInt(Math.pow(2, ai_bit));

		return li_value;
	}

	/**
	* Determina si el valor del <i>bit</i> <i>ai_bit</i> de la representación binaria de
	* <i>ai_value</i> está ajustado en 1.
	* @param    ai_value    <code>int</code> a validar.
	* @param    ai_bit        Bit cuyo valor debe estas ajustado en 1.
	* @return    <code>true</code> si el bit <i>ai_bit</i> de la representación binaria del valor de
	*            <i>ai_value</i> esta ajustado en 1 o <code>false</code> de lo contrario.
	* @author Edgar Prieto
	*/
	public static boolean isBitSet(int ai_value, int ai_bit)
	{
		return isBitSet(ai_value, NumericUtils.getInt(Math.pow(2, ai_bit)), ai_bit);
	}

	/**
	* Determina si el valor del <i>bit</i> <i>ai_bit</i> de la representación binaria de la mascara está ajustado en 1
	* @param    ai_value    <code>int</code> a validar.
	* @param    ai_mask        Mascara de validación binarias
	* @return    <code>true</code> si el bit <i>ai_bit</i> de la representación binaria del valor de
	*            <i>ai_value</i> esta ajustado en 1 o <code>false</code> de lo contrario.
	* @author Edgar Prieto
	*/
	public static boolean isFlagSet(int ai_value, int ai_mask)
	{
		return isBitSet(ai_value, ai_mask, NumericUtils.getInt(Math.pow(ai_mask, 0.5d)));
	}

	/**
	* Determina si el valor del <i>bit</i> <i>ai_bit</i> de la representación binaria de
	* <i>ai_value</i> está ajustado en 1 usando la mascara indicada.
	* @param    ai_value    <code>int</code> a validar.
	* @param    ai_mask        Mascara de validación binarias
	* @param    ai_bit        Bit cuyo valor debe estas ajustado en 1.
	* @return    <code>true</code> si el bit <i>ai_bit</i> de la representación binaria del valor de
	*            <i>ai_value</i> esta ajustado en 1 o <code>false</code> de lo contrario.
	* @author Edgar Prieto
	*/
	private static boolean isBitSet(int ai_value, int ai_mask, int ai_bit)
	{
		return ((ai_value & ai_mask) >> ai_bit) == 1;
	}
}
