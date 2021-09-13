package com.b2bsg.common.util;

import com.b2bsg.common.logging.LoggerHandler;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;


/**
 * Clase de utilidades para el manejo de tipos númericos.
 *
 * @author Edgar Prieto
 * @version Sep 05, 2004
 */
public class NumericUtils
{
	/** Constante DEFAULT_BIG_DECIMAL_VALUE. */
	public static final BigDecimal DEFAULT_BIG_DECIMAL_VALUE = BigDecimal.valueOf(0L);

	/** Constante DEFAULT_BIG_INTEGER_VALUE. */
	public static final BigInteger DEFAULT_BIG_INTEGER_VALUE = BigInteger.valueOf(0L);

	/** Constante DEFAULT_DOUBLE_VALUE. */
	public static final double DEFAULT_DOUBLE_VALUE = 0D;

	/** Constante DEFAULT_FLOAT_VALUE. */
	public static final float DEFAULT_FLOAT_VALUE = 0F;

	/** Constante DEFAULT_INT_VALUE. */
	public static final int DEFAULT_INT_VALUE = 0;

	/** Constante DEFAULT_LONG_VALUE. */
	public static final long DEFAULT_LONG_VALUE = 0L;

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(NumericUtils.class);

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto double
	 * @return el valor de big decimal
	 */
	public static BigDecimal getBigDecimal(double ad_d)
	{
		return getBigDecimal(getDoubleWrapper(ad_d));
	}

	/**
	 * Retorna el valor de big decimal de una represetación en formao String.
	 *
	 * @param as_s String a convertir en BigDecimal
	 * @return el valor de big decimal
	 */
	public static BigDecimal getBigDecimal(String as_s)
	{
		return getBigDecimal(getDoubleWrapper(as_s));
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @return el valor de big decimal
	 */
	public static BigDecimal getBigDecimal(Double ad_d)
	{
		BigDecimal lbd_return;

		if(ad_d == null)
			lbd_return = null;
		else
		{
			DecimalFormat ldf_formatter;

			ldf_formatter = new DecimalFormat();

			ldf_formatter.setGroupingUsed(false);

			{
				DecimalFormatSymbols ldfs_symbols;

				ldfs_symbols = ldf_formatter.getDecimalFormatSymbols();

				ldfs_symbols.setDecimalSeparator('.');
				ldf_formatter.setDecimalFormatSymbols(ldfs_symbols);
			}

			lbd_return = getBigDecimal(ad_d, ldf_formatter);
		}

		return lbd_return;
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @return el valor de big decimal
	 */
	public static BigDecimal getBigDecimal(BigDecimal abd_bd)
	{
		return getBigDecimal(abd_bd, DEFAULT_BIG_DECIMAL_VALUE);
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto int
	 * @return el valor de big decimal
	 */
	public static BigDecimal getBigDecimal(int ai_i)
	{
		return getBigDecimal(getDoubleWrapper(ai_i));
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de big decimal
	 */
	public static BigDecimal getBigDecimal(long al_l)
	{
		return getBigDecimal(getDoubleWrapper(al_l));
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @return el valor de big integer
	 */
	public static BigInteger getBigInteger(Long al_l)
	{
		BigInteger lbi_return;

		if(al_l == null)
			lbi_return = null;
		else
		{
			DecimalFormat ldf_formatter;

			ldf_formatter = new DecimalFormat();

			ldf_formatter.setGroupingUsed(false);

			{
				DecimalFormatSymbols ldfs_symbols;

				ldfs_symbols = ldf_formatter.getDecimalFormatSymbols();

				ldfs_symbols.setDecimalSeparator('.');
				ldf_formatter.setDecimalFormatSymbols(ldfs_symbols);
			}

			lbi_return = getBigInteger(al_l, ldf_formatter);
		}

		return lbi_return;
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto int
	 * @return el valor de big integer
	 */
	public static BigInteger getBigInteger(int ai_i)
	{
		return getBigInteger(getLongWrapper(ai_i));
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de big integer
	 */
	public static BigInteger getBigInteger(long al_l)
	{
		return getBigInteger(getLongWrapper(al_l));
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de big integer
	 */
	public static BigInteger getBigInteger(String as_s)
	{
		return getBigInteger(as_s, DEFAULT_BIG_INTEGER_VALUE);
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de big integer
	 */
	public static BigInteger getBigInteger(String as_s, BigInteger abi_default)
	{
		return (as_s != null) ? new BigInteger(as_s) : abi_default;
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de double
	 */
	public static double getDouble(String as_s)
	{
		return getDouble(as_s, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @return el valor de double
	 */
	public static double getDouble(Double ad_d)
	{
		return getDouble(ad_d, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Obtener la representación <code>double</code> de un <code>int</code>.
	 *
	 * @author Edgar Prieto
	 * @param ai_i <code>int</code> del cual se obtendrá la representación <code>double</code>.
	 * @return    Representación <code>double</code> de <i>ai_i</i>.
	 */
	public static double getDouble(int ai_i)
	{
		return getDouble(getInteger(ai_i));
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @return el valor de double
	 */
	public static double getDouble(Integer ai_i)
	{
		return getDouble(ai_i, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 */
	public static double getDouble(BigDecimal abd_bd, double ad_default)
	{
		return (abd_bd != null) ? abd_bd.doubleValue() : ad_default;
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @return el valor de double
	 */
	public static double getDouble(Long al_l)
	{
		return getDouble(al_l, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(double ad_d)
	{
		return Double.valueOf(ad_d);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param ao_o correspondiente al valor del tipo de objeto Object
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(Object ao_o)
	{
		Double ld_d;

		if(ao_o != null)
		{
			if(ao_o instanceof Double)
				ld_d = (Double)ao_o;
			else if(ao_o instanceof Float)
				ld_d = getDoubleWrapper((Float)ao_o);
			else if(ao_o instanceof Integer)
				ld_d = getDoubleWrapper((Integer)ao_o);
			else if(ao_o instanceof Long)
				ld_d = getDoubleWrapper((Long)ao_o);
			else if(ao_o instanceof Short)
				ld_d = getDoubleWrapper((Short)ao_o);
			else if(ao_o instanceof String)
				ld_d = getDoubleWrapper((String)ao_o);
			else if(ao_o instanceof BigDecimal)
				ld_d = getDoubleWrapper((BigDecimal)ao_o);
			else if(ao_o instanceof BigInteger)
				ld_d = getDoubleWrapper((BigInteger)ao_o);
			else
				ld_d = null;
		}
		else
			ld_d = null;

		return ld_d;
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(Integer ai_i)
	{
		return getDoubleWrapper(ai_i, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(Long al_l)
	{
		return getDoubleWrapper(al_l, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(String as_s)
	{
		return getDoubleWrapper(as_s, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(BigDecimal abd_bd)
	{
		return getDoubleWrapper(abd_bd, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(BigInteger abi_bi)
	{
		return getDoubleWrapper(abi_bi, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(long al_l)
	{
		return getDoubleWrapper(getLongWrapper(al_l));
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto int
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(int ai_i)
	{
		return getDoubleWrapper(getInteger(ai_i));
	}

	/**
	 * Retorna el valor de float.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto int
	 * @return el valor de float
	 */
	public static float getFloat(int ai_i)
	{
		return getFloat(getInteger(ai_i));
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto double
	 * @return el valor de int
	 */
	public static int getInt(double ad_d)
	{
		return getInt(getDoubleWrapper(ad_d));
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @return el valor de int
	 */
	public static int getInt(Double ad_d)
	{
		return getInt(ad_d, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @return el valor de int
	 */
	public static int getInt(Integer ai_i)
	{
		return getInt(ai_i, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de int
	 */
	public static int getInt(long al_l)
	{
		return getInt(getLongWrapper(al_l));
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @return el valor de int
	 */
	public static int getInt(Long al_l)
	{
		return getInt(al_l, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto short
	 * @return el valor de int
	 */
	public static int getInt(short as_s)
	{
		return getInt(getShortWrapper(as_s));
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @return el valor de int
	 */
	public static int getInt(BigDecimal abd_bd)
	{
		return getInt(abd_bd, DEFAULT_INT_VALUE);
	}

	/**
	 * Obtener el valor <code>java.lang.String</code> representado como <code>int</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> del cual se obtendrá el valor <code>int</code>.
	 * @return    <code>int</code> si <i>as_s</i> representa a un <code>int</code>. <code>0</code> de
	 *            lo contrario.
	 */
	public static int getInt(String as_s)
	{
		return getInt(as_s, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @return el valor de int
	 */
	public static int getInt(BigInteger abi_bi)
	{
		return getInt(abi_bi, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de integer.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto int
	 * @return el valor de integer
	 */
	public static Integer getInteger(int ai_i)
	{
		return Integer.valueOf(ai_i);
	}

	/**
	 * Retorna el valor de integer.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de integer
	 */
	public static Integer getInteger(long al_l)
	{
		return getInteger(getLongWrapper(al_l));
	}

	/**
	 * Retorna el valor de integer.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @return el valor de integer
	 */
	public static Integer getInteger(Long al_l)
	{
		return getInteger(al_l, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de integer.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de integer
	 */
	public static Integer getInteger(String as_s)
	{
		return getInteger(as_s, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de integer.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de integer
	 */
	public static Integer getInteger(String as_s, int ai_default)
	{
		Integer li_i;

		try
		{
			li_i = Integer.valueOf(StringUtils.getString(as_s));
		}
		catch(Exception le_e)
		{
			li_i = getInteger(ai_default);
		}

		return li_i;
	}

	/**
	 * Retorna el valor de integer wrapper.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @return el valor de integer wrapper
	 */
	public static Integer getIntegerWrapper(BigDecimal abd_bd)
	{
		return getIntegerWrapper(abd_bd, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @return el valor de long
	 */
	public static long getLong(BigDecimal abd_bd)
	{
		return getLong(abd_bd, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param af_f correspondiente al valor del tipo de objeto float
	 * @return el valor de long
	 */
	public static long getLong(float af_f)
	{
		return getLong(getFloatWrapper(af_f));
	}

	/**
	 * Obtener la representación <code>long</code> de un <code>int</code>.
	 *
	 * @author Edgar Prieto
	 * @param ai_i <code>int</code> del cual se obtendrá la representación <code>long</code>.
	 * @return    Representación <code>long</code> de <i>ai_i</i>.
	 */
	public static long getLong(int ai_i)
	{
		return getLong(getInteger(ai_i));
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @return el valor de long
	 */
	public static long getLong(Integer ai_i)
	{
		return getLong(ai_i, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @return el valor de long
	 */
	public static long getLong(Long al_l)
	{
		return getLong(al_l, DEFAULT_LONG_VALUE);
	}

	/**
	 * Obtener el valor <code>java.lang.String</code> representado como <code>long</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> del cual se obtendrá el valor <code>long</code>.
	 * @return    <code>long</code> si <i>as_s</i> representa a un <code>long</code>.
	 *            <code>0</code> de lo contrario.
	 */
	public static long getLong(String as_s)
	{
		return getLong(as_s, DEFAULT_LONG_VALUE);
	}

	/**
	 * Obtener el valor <code>java.lang.String</code> representado como <code>long</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> del cual se obtendrá el valor <code>long</code>.
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return    <code>long</code> si <i>as_s</i> representa a un <code>long</code>.
	 *            <code>0</code> de lo contrario.
	 */
	public static long getLong(String as_s, long al_default)
	{
		return getLong(getLongWrapper(as_s, al_default), al_default);
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long
	 */
	public static long getLong(Long al_l, long al_default)
	{
		return (al_l != null) ? al_l.longValue() : al_default;
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long
	 */
	public static long getLong(BigDecimal abd_bd, long al_default)
	{
		return (abd_bd != null) ? abd_bd.longValue() : al_default;
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto long
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(long al_l)
	{
		return Long.valueOf(al_l);
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(BigInteger abi_bi)
	{
		return getLongWrapper(abi_bi, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(BigDecimal abd_bd)
	{
		return getLongWrapper(abd_bd, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto double
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(double ad_d)
	{
		return getLongWrapper(getDoubleWrapper(ad_d));
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(String as_s)
	{
		return getLongWrapper(as_s, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto int
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(int ai_i)
	{
		return getLongWrapper(getInteger(ai_i));
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(Integer ai_i)
	{
		return getLongWrapper(ai_i, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long wrapper
	 */
	public static Long getLongWrapper(String as_s, long al_default)
	{
		Long ll_l;

		try
		{
			ll_l = Long.valueOf(StringUtils.getString(as_s));
		}
		catch(Exception le_e)
		{
			ll_l = getLongWrapper(al_default);
		}

		return ll_l;
	}

	/**
	 * Determina si un objeto <code>java.math.BigDecimal</code> es <code>null</code> o si representa
	 * un valor menor a 0.
	 *
	 * @author Camilo Restrepo
	 * @param abd_bd <code>java.math.BigDecimal</code> a validar.
	 * @return    <code>true</code> si <i>abd_bd</i> representa un valor mayor a -1 o
	 *            <code>false</code> si <i>abd_bd</i> es <code>null</code> o representa un valor menor
	 *             a <code>0</code>.
	 */
	public static boolean isValidBigDecimal(BigDecimal abd_bd)
	{
		return isValidBigDecimal(abd_bd, DEFAULT_BIG_DECIMAL_VALUE);
	}

	/**
	 * Valida la propiedad valid big decimal.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param abd_compare correspondiente al valor del tipo de objeto BigDecimal
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid big decimal
	 */
	public static boolean isValidBigDecimal(BigDecimal abd_bd, BigDecimal abd_compare)
	{
		return ((abd_bd != null) && (abd_compare != null)) && (abd_bd.compareTo(abd_compare) >= 0);
	}

	/**
	 * Valida la propiedad valid big integer.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid big integer
	 */
	public static boolean isValidBigInteger(BigInteger abi_bi)
	{
		return isValidBigInteger(abi_bi, DEFAULT_BIG_INTEGER_VALUE);
	}

	/**
	 * Valida la propiedad valid big integer.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @param abi_compare correspondiente al valor del tipo de objeto BigInteger
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid big integer
	 */
	public static boolean isValidBigInteger(BigInteger abi_bi, BigInteger abi_compare)
	{
		return ((abi_bi != null) && (abi_compare != null)) && (abi_bi.compareTo(abi_compare) >= 0);
	}

	/**
	 * Determina si un objeto <code>java.lang.Float</code> es <code>null</code> o si representa
	 * un valor menor a 0.
	 *
	 * @author Edgar Prieto
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @return    <code>true</code> si <i>af_f</i> representa un valor mayor a -1 o
	 *            <code>false</code> si <i>af_f</i> es <code>null</code> o representa un valor menor
	 *             a <code>0</code>.
	 */
	public static boolean isValidDouble(Double ad_d)
	{
		return isValidDouble(ad_d, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Valida la propiedad valid double.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param ad_compare correspondiente al valor del tipo de objeto double
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid double
	 */
	public static boolean isValidDouble(Double ad_d, double ad_compare)
	{
		return isValidDouble(ad_d, ad_compare, true);
	}

	/**
	 * Valida la propiedad valid double.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param ad_compare correspondiente al valor del tipo de objeto double
	 * @param ab_accion correspondiente al valor del tipo de objeto boolean
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid double
	 */
	public static boolean isValidDouble(Double ad_d, double ad_compare, boolean ab_accion)
	{
		boolean lb_return;

		lb_return = false;

		if(ad_d != null)
		{
			if(ab_accion)
				lb_return = getDouble(ad_d) >= ad_compare;
			else
				lb_return = getDouble(ad_d) > ad_compare;
		}

		return lb_return;
	}

	/**
	 * Determina si un objeto <code>java.lang.Integer</code> es <code>null</code> o si representa
	 * un valor menor a 0.
	 *
	 * @author Edgar Prieto
	 * @param ai_i <code>java.lang.Integer</code> a validar.
	 * @return    <code>true</code> si <i>ai_i</i> representa un valor mayor a -1 o
	 *            <code>false</code> si <i>ai_i</i> es <code>null</code> o representa un valor menor
	 *             a <code>0</code>.
	 */
	public static boolean isValidInteger(Integer ai_i)
	{
		return isValidInteger(ai_i, DEFAULT_INT_VALUE);
	}

	/**
	 * Valida la propiedad valid integer.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param ai_compare correspondiente al valor del tipo de objeto int
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid integer
	 */
	public static boolean isValidInteger(Integer ai_i, int ai_compare)
	{
		return (ai_i != null) && (getInt(ai_i) >= ai_compare);
	}

	/**
	 * Valida la propiedad valid long.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid long
	 */
	public static boolean isValidLong(Long al_l)
	{
		return isValidLong(al_l, DEFAULT_LONG_VALUE);
	}

	/**
	 * Valida la propiedad valid long.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param al_compare correspondiente al valor del tipo de objeto long
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en valid long
	 */
	public static boolean isValidLong(Long al_l, long al_compare)
	{
		return (al_l != null) && (getLong(al_l) >= al_compare);
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param ad_valor correspondiente al valor del tipo de objeto Double
	 * @param adf_format correspondiente al valor del tipo de objeto DecimalFormat
	 * @return devuelve el valor de String
	 */
	public static String conversionCientifica(Double ad_valor, DecimalFormat adf_format)
	{
		String ls_conversion;

		ls_conversion = null;

		if((ad_valor != null) && (adf_format != null))
			ls_conversion = adf_format.format(ad_valor);

		return ls_conversion;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param dNumero correspondiente al valor del tipo de objeto double
	 * @param ab_pesos correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de String
	 */
	public static String convertirLetras(double dNumero, boolean ab_pesos)
	{
		String ls_retorno;

		ls_retorno = "";

		try
		{
			DecimalFormat adf_formato;
			String        ls_numero;

			adf_formato     = new DecimalFormat("##################.00");
			ls_numero       = adf_formato.format(dNumero).replace(',', '.');

			if(StringUtils.isValidString(ls_numero))
			{
				ls_numero = ls_numero.substring(0, ls_numero.indexOf("."));

				if(StringUtils.isValidString(ls_numero))
					ls_retorno = convertNumberToLetter(Integer.parseInt(ls_numero), ab_pesos);
			}
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("convertirLetras", le_e);
		}

		return (ls_retorno != null) ? ls_retorno.trim() : "";
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param numero correspondiente al valor del tipo de objeto int
	 * @param ab_pesos correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de String
	 */
	public static String convertirLetras(int numero, boolean ab_pesos)
	{
		String sRet = "";

		try
		{
			sRet = convertNumberToLetter(numero, ab_pesos);
		}
		catch(Exception le_e)
		{
			clh_LOGGER.error("convertirLetras", le_e);
		}

		return sRet.trim();
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param anf_formatter correspondiente al valor del tipo de objeto NumberFormat
	 * @return el valor de big decimal
	 */
	private static BigDecimal getBigDecimal(Double ad_d, NumberFormat anf_formatter)
	{
		BigDecimal lbd_return;

		if(ad_d == null)
			lbd_return = null;
		else
			lbd_return = (anf_formatter != null) ? new BigDecimal(anf_formatter.format(ad_d))
				                                 : BigDecimal.valueOf(ad_d.doubleValue());

		return lbd_return;
	}

	/**
	 * Retorna el valor de big decimal.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param abd_default correspondiente al valor del tipo de objeto BigDecimal
	 * @return el valor de big decimal
	 */
	private static BigDecimal getBigDecimal(BigDecimal abd_bd, BigDecimal abd_default)
	{
		return (abd_bd != null) ? abd_bd : abd_default;
	}

	/**
	 * Retorna el valor de big integer.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param anf_formatter correspondiente al valor del tipo de objeto NumberFormat
	 * @return el valor de big integer
	 */
	private static BigInteger getBigInteger(Long al_l, NumberFormat anf_formatter)
	{
		BigInteger lbi_return;

		if(al_l == null)
			lbi_return = null;
		else
			lbi_return = (anf_formatter != null) ? new BigInteger(anf_formatter.format(al_l))
				                                 : BigInteger.valueOf(al_l.longValue());

		return lbi_return;
	}

	/**
	 * Obtener el valor <code>java.lang.String</code> representado como <code>double</code>.
	 *
	 * @author Edgar Prieto
	 * @param as_s <code>java.lang.String</code> del cual se obtendrá el valor <code>double</code>.
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return    <code>double</code> si <i>as_s</i> representa a un <code>double</code>.
	 *            <code>0</code> de lo contrario.
	 */
	private static double getDouble(String as_s, double ad_default)
	{
		return getDouble(getDoubleWrapper(as_s, ad_default), ad_default);
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 */
	private static double getDouble(Double ad_d, double ad_default)
	{
		return (ad_d != null) ? ad_d.doubleValue() : ad_default;
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param af_f correspondiente al valor del tipo de objeto Float
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 */
	private static double getDouble(Float af_f, double ad_default)
	{
		return (af_f != null) ? af_f.doubleValue() : ad_default;
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 */
	private static double getDouble(Integer ai_i, double ad_default)
	{
		return (ai_i != null) ? ai_i.doubleValue() : ad_default;
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto BigInteger
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 */
	private static double getDouble(BigInteger ai_i, double ad_default)
	{
		return (ai_i != null) ? ai_i.doubleValue() : ad_default;
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 */
	private static double getDouble(Long al_l, double ad_default)
	{
		return (al_l != null) ? al_l.doubleValue() : ad_default;
	}

	/**
	 * Retorna el valor de double.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Short
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double
	 */
	private static double getDouble(Short as_s, double ad_default)
	{
		return (as_s != null) ? as_s.doubleValue() : ad_default;
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(String as_s, double ad_default)
	{
		Double ld_d;

		try
		{
			ld_d = Double.valueOf(StringUtils.getString(as_s));
		}
		catch(Exception le_e)
		{
			ld_d = getDoubleWrapper(ad_default);
		}

		return ld_d;
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param af_f correspondiente al valor del tipo de objeto Float
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(Float af_f)
	{
		return getDoubleWrapper(af_f, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param af_f correspondiente al valor del tipo de objeto Float
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(Float af_f, double ad_default)
	{
		return getDoubleWrapper(getDouble(af_f, ad_default));
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(Integer ai_i, double ad_default)
	{
		return getDoubleWrapper(getDouble(ai_i, ad_default));
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(Long al_l, double ad_default)
	{
		return getDoubleWrapper(getDouble(al_l, ad_default));
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Short
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(Short as_s)
	{
		return getDoubleWrapper(as_s, DEFAULT_DOUBLE_VALUE);
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Short
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(Short as_s, double ad_default)
	{
		return getDoubleWrapper(getDouble(as_s, ad_default));
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	public static Double getDoubleWrapper(BigDecimal abd_bd, double ad_default)
	{
		return getDoubleWrapper(getDouble(abd_bd, ad_default));
	}

	/**
	 * Retorna el valor de double wrapper.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @param ad_default correspondiente al valor del tipo de objeto double
	 * @return el valor de double wrapper
	 */
	private static Double getDoubleWrapper(BigInteger abi_bi, double ad_default)
	{
		return getDoubleWrapper(getDouble(abi_bi, ad_default));
	}

	/**
	 * Retorna el valor de float.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @return el valor de float
	 */
	private static float getFloat(Integer ai_i)
	{
		return getFloat(ai_i, DEFAULT_FLOAT_VALUE);
	}

	/**
	 * Retorna el valor de float.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param af_default correspondiente al valor del tipo de objeto float
	 * @return el valor de float
	 */
	private static float getFloat(Integer ai_i, float af_default)
	{
		return (ai_i != null) ? ai_i.floatValue() : af_default;
	}

	/**
	 * Retorna el valor de float wrapper.
	 *
	 * @param af_f correspondiente al valor del tipo de objeto float
	 * @return el valor de float wrapper
	 */
	private static Float getFloatWrapper(float af_f)
	{
		return Float.valueOf(af_f);
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de int
	 */
	private static int getInt(Long al_l, int ai_default)
	{
		return (al_l != null) ? al_l.intValue() : ai_default;
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de int
	 */
	private static int getInt(Double ad_d, int ai_default)
	{
		return (ad_d != null) ? ad_d.intValue() : ai_default;
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de int
	 */
	private static int getInt(Integer ai_i, int ai_default)
	{
		return (ai_i != null) ? ai_i.intValue() : ai_default;
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Short
	 * @return el valor de int
	 */
	private static int getInt(Short as_s)
	{
		return getInt(as_s, DEFAULT_INT_VALUE);
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param al_default correspondiente al valor del tipo de objeto int
	 * @return el valor de int
	 */
	private static int getInt(BigDecimal abd_bd, int al_default)
	{
		return (abd_bd != null) ? abd_bd.intValue() : al_default;
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto Short
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de int
	 */
	private static int getInt(Short as_s, int ai_default)
	{
		return (as_s != null) ? as_s.intValue() : ai_default;
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto String
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de int
	 */
	private static int getInt(String as_s, int ai_default)
	{
		return getInt(getInteger(as_s, ai_default), ai_default);
	}

	/**
	 * Retorna el valor de int.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de int
	 */
	private static int getInt(BigInteger abi_bi, int ai_default)
	{
		return (abi_bi != null) ? abi_bi.intValue() : ai_default;
	}

	/**
	 * Retorna el valor de integer.
	 *
	 * @param al_l correspondiente al valor del tipo de objeto Long
	 * @param ai_default correspondiente al valor del tipo de objeto int
	 * @return el valor de integer
	 */
	private static Integer getInteger(Long al_l, int ai_default)
	{
		return getInteger(getInt(al_l, ai_default));
	}

	/**
	 * Retorna el valor de integer wrapper.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param al_default correspondiente al valor del tipo de objeto int
	 * @return el valor de integer wrapper
	 */
	private static Integer getIntegerWrapper(BigDecimal abd_bd, int al_default)
	{
		return getInteger(getInt(abd_bd, al_default));
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long
	 */
	private static long getLong(BigInteger abi_bi, long al_default)
	{
		return (abi_bi != null) ? abi_bi.longValue() : al_default;
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long
	 */
	private static long getLong(Double ad_d, long al_default)
	{
		return (ad_d != null) ? ad_d.longValue() : al_default;
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param af_f correspondiente al valor del tipo de objeto Float
	 * @return el valor de long
	 */
	private static long getLong(Float af_f)
	{
		return getLong(af_f, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param af_f correspondiente al valor del tipo de objeto Float
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long
	 */
	private static long getLong(Float af_f, long al_default)
	{
		return (af_f != null) ? af_f.longValue() : al_default;
	}

	/**
	 * Retorna el valor de long.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long
	 */
	private static long getLong(Integer ai_i, long al_default)
	{
		return (ai_i != null) ? ai_i.longValue() : al_default;
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param abi_bi correspondiente al valor del tipo de objeto BigInteger
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long wrapper
	 */
	private static Long getLongWrapper(BigInteger abi_bi, long al_default)
	{
		return getLongWrapper(getLong(abi_bi, al_default));
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @return el valor de long wrapper
	 */
	private static Long getLongWrapper(Double ad_d)
	{
		return getLongWrapper(ad_d, DEFAULT_LONG_VALUE);
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param ad_d correspondiente al valor del tipo de objeto Double
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long wrapper
	 */
	private static Long getLongWrapper(Double ad_d, long al_default)
	{
		return getLongWrapper(getLong(ad_d, al_default));
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param abd_bd correspondiente al valor del tipo de objeto BigDecimal
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long wrapper
	 */
	private static Long getLongWrapper(BigDecimal abd_bd, long al_default)
	{
		return getLongWrapper(getLong(abd_bd, al_default));
	}

	/**
	 * Retorna el valor de long wrapper.
	 *
	 * @param ai_i correspondiente al valor del tipo de objeto Integer
	 * @param al_default correspondiente al valor del tipo de objeto long
	 * @return el valor de long wrapper
	 */
	private static Long getLongWrapper(Integer ai_i, long al_default)
	{
		return getLongWrapper(getLong(ai_i, al_default));
	}

	/**
	 * Retorna el valor de short wrapper.
	 *
	 * @param as_s correspondiente al valor del tipo de objeto short
	 * @return el valor de short wrapper
	 */
	private static Short getShortWrapper(short as_s)
	{
		return Short.valueOf(as_s);
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param ai_unidad correspondiente al valor del tipo de objeto int
	 * @param ai_decena correspondiente al valor del tipo de objeto int
	 * @param ai_centena correspondiente al valor del tipo de objeto int
	 * @return devuelve el valor de String
	 */
	private static String Centena(int ai_unidad, int ai_decena, int ai_centena)
	{
		String ls_retorno = "";

		switch(ai_centena)
		{
			case 1:

				if((ai_decena + ai_unidad) == 0)
					ls_retorno = "cien ";
				else
					ls_retorno = "ciento ";

				break;

			case 2:
				ls_retorno = "doscientos ";

				break;

			case 3:
				ls_retorno = "trescientos ";

				break;

			case 4:
				ls_retorno = "cuatrocientos ";

				break;

			case 5:
				ls_retorno = "quinientos ";

				break;

			case 6:
				ls_retorno = "seiscientos ";

				break;

			case 7:
				ls_retorno = "setecientos ";

				break;

			case 8:
				ls_retorno = "ochocientos ";

				break;

			case 9:
				ls_retorno = "novecientos ";

				break;

			default:
				ls_retorno = "";

				break;
		}

		return ls_retorno;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param ai_unidad correspondiente al valor del tipo de objeto int
	 * @param ai_decena correspondiente al valor del tipo de objeto int
	 * @return devuelve el valor de String
	 */
	private static String Decena(int ai_unidad, int ai_decena)
	{
		String ls_retorno = "";

		switch(ai_decena)
		{
			case 1:

				switch(ai_unidad)
				{
					case 0:
						ls_retorno = "diez ";

						break;

					case 1:
						ls_retorno = "once ";

						break;

					case 2:
						ls_retorno = "doce ";

						break;

					case 3:
						ls_retorno = "trece ";

						break;

					case 4:
						ls_retorno = "catorce ";

						break;

					case 5:
						ls_retorno = "quince ";

						break;

					default:
						ls_retorno = "dieci";

						break;
				}

				break;

			case 2:

				if(ai_unidad == 0)
					ls_retorno = "veinte ";
				else if(ai_unidad > 0)
					ls_retorno = "veinti";

				break;

			case 3:
				ls_retorno = "treinta ";

				break;

			case 4:
				ls_retorno = "cuarenta ";

				break;

			case 5:
				ls_retorno = "cincuenta ";

				break;

			case 6:
				ls_retorno = "sesenta ";

				break;

			case 7:
				ls_retorno = "setenta ";

				break;

			case 8:
				ls_retorno = "ochenta ";

				break;

			case 9:
				ls_retorno = "noventa ";

				break;

			default:
				ls_retorno = "";

				break;
		}

		if((ai_unidad > 0) && (ai_decena > 2))
			ls_retorno += "y ";

		return ls_retorno;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param uni correspondiente al valor del tipo de objeto int
	 * @param dec correspondiente al valor del tipo de objeto int
	 * @return devuelve el valor de String
	 */
	private static String Unidad(int uni, int dec)
	{
		String ls_retorno = "";

		if(dec != 1)
		{
			switch(uni)
			{
				case 1:
					ls_retorno = "uno ";

					break;

				case 2:
					ls_retorno = "dos ";

					break;

				case 3:
					ls_retorno = "tres ";

					break;

				case 4:
					ls_retorno = "cuatro ";

					break;

				case 5:
					ls_retorno = "cinco ";

					break;

				default:
					break;
			}
		}

		switch(uni)
		{
			case 6:
				ls_retorno = "seis ";

				break;

			case 7:
				ls_retorno = "siete ";

				break;

			case 8:
				ls_retorno = "ocho ";

				break;

			case 9:
				ls_retorno = "nueve ";

				break;

			default:
				break;
		}

		return ls_retorno;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param Numero correspondiente al valor del tipo de objeto double
	 * @param ab_pesos correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de String
	 */
	private static String convertNumberToLetter(double Numero, boolean ab_pesos)
	{
		String NumTmp   = "";
		int    c01;
		int    c02;
		int    pos;
		int    dig;
		int    cen      = 0;
		int    dec      = 0;
		int    uni      = 0;
		String letra1;
		String letra2;
		String letra3;
		String Leyenda  = "";
		String TFNumero = " ";

		if(Numero < 0)
			Numero = Math.abs(Numero);

		NumTmp = (new DecimalFormat("##################.00").format(Numero));

		String sTmp = "0000000000000000" + NumTmp;
		NumTmp       = sTmp.substring(sTmp.length() - 18);
		c01          = 1;
		pos          = 0;
		TFNumero     = "";

		while(c01 <= 5)
		{
			c02 = 1;

			while(c02 <= 3)
			{
				dig = Integer.parseInt(NumTmp.substring(pos, pos + 1));

				switch(c02)
				{
					case 1:
						cen = dig;

						break;

					case 2:
						dec = dig;

						break;

					case 3:
						uni = dig;

						break;

					default:
						break;
				}

				c02++;
				pos++;
			}

			letra3     = Centena(uni, dec, cen);
			letra2     = Decena(uni, dec);
			letra1     = Unidad(uni, dec);

			switch(c01)
			{
				case 1:

					if((cen + dec + uni) == 1)
						Leyenda = "Billon ";
					else if((cen + dec + uni) > 1)
						Leyenda = "Billones ";

					break;

				case 2:

					if(((cen + dec + uni) >= 1) && (Integer.parseInt(NumTmp.substring(6, 8)) == 0))
						Leyenda = "Mil Millones ";
					else if((cen + dec + uni) >= 1)
						Leyenda = "Mil ";

					break;

				case 3:

					if(((cen + dec) == 0) && (uni == 1))
						Leyenda = "Millon ";
					else if((cen > 0) || (dec > 0) || (uni > 1))
						Leyenda = "Millones ";

					break;

				case 4:

					if((cen + dec + uni) >= 1)
						Leyenda = "Mil ";

					break;

				case 5:

					if((cen + dec + uni) >= 1)
						Leyenda = "";

					break;

				default:
					break;
			}

			c01++;
			TFNumero += (letra3 + letra2 + letra1 + Leyenda);
			Leyenda     = "";
			letra1      = "";
			letra2      = "";
			letra3      = "";
		}

		letra3       = TFNumero.toUpperCase();
		TFNumero     = letra3.replace("UNO MILLON", "UN MILLON").replace("IUNO MIL", "IUN MIL")
				                 .replace("Y UNO MIL", "Y UN MIL").trim()
				                 .replace(
				    "UNO MIL",
				    (((Numero >= 1000D) && (Numero < 2000D)) || ((Numero >= 1000000000D) && (Numero < 2000000000D)))
				    ? "MIL" : "UN MIL"
				).trim();

		if(StringUtils.isValidString(TFNumero))
		{
			if(TFNumero.substring(TFNumero.length() - 3).equalsIgnoreCase("UNO"))
			{
				TFNumero     = TFNumero.trim();
				TFNumero     = TFNumero.substring(0, TFNumero.length() - 1);
			}

			if(TFNumero.endsWith("ILLONES") || TFNumero.endsWith("ILLON"))
				TFNumero = TFNumero + " DE";
		}

		{
			NumberFormat lnf_format;
			lnf_format = NumberFormat.getInstance();

			if(lnf_format instanceof DecimalFormat)
			{
				char                 lc_decimalSeparator;
				DecimalFormat        ldf_format;
				DecimalFormatSymbols ldfs_symbols;
				int                  li_inicioCentavos;

				ldf_format              = (DecimalFormat)lnf_format;
				ldfs_symbols            = ldf_format.getDecimalFormatSymbols();
				lc_decimalSeparator     = ldfs_symbols.getDecimalSeparator();

				li_inicioCentavos = NumTmp.lastIndexOf(String.valueOf(lc_decimalSeparator));

				if(li_inicioCentavos > 0)
				{
					String ls_centavos;
					ls_centavos = NumTmp.substring(li_inicioCentavos + 1);

					if(StringUtils.isValidString(ls_centavos))
					{
						String ls_decena;
						String ls_unidad;

						int li_decena;
						int li_unidad;

						ls_decena     = ls_centavos.substring(0, 1);
						ls_unidad     = ls_centavos.substring(1);

						li_decena     = NumericUtils.getInt(ls_decena);
						li_unidad     = NumericUtils.getInt(ls_unidad);

						if((li_decena > 0) || (li_unidad > 0))
						{
							ls_decena     = Decena(li_unidad, li_decena);
							ls_unidad     = Unidad(li_unidad, li_decena);

							TFNumero = TFNumero + (ab_pesos ? " PESOS CON " : "") + ls_decena + ls_unidad + "CENTAVOS";
						}
						else
							TFNumero = TFNumero + (ab_pesos ? " PESOS" : "");

						TFNumero     = TFNumero.toUpperCase();
						TFNumero     = TFNumero.replace("UNO CENTAVOS", "UN CENTAVO");
					}
				}
			}
		}

		return TFNumero;
	}
}
