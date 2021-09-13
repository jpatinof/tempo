package com.cromasoft.cromaflow.common.utils;

import com.b2bsg.common.util.NumericUtils;
import com.b2bsg.common.util.StringUtils;


/**
 * Clase para convertir nuemeros en letras.
 *
 * @author Edgar Prieto
 */
public class Convertidor
{
	/** Constante cl_0_000. */
	private static final long cl_0_000 = 0L;

	/** Constante cl_0_001. */
	private static final long cl_0_001 = 1L;

	/** Constante cl_0_002. */
	private static final long cl_0_002 = 2L;

	/** Constante cl_0_003. */
	private static final long cl_0_003 = 3L;

	/** Constante cl_0_010. */
	private static final long cl_0_010 = 10L;

	/** Constante cl_0_020. */
	private static final long cl_0_020 = cl_0_010 * cl_0_002;

	/** Constante cl_0_030. */
	private static final long cl_0_030 = cl_0_010 * cl_0_003;

	/** Constante cl_0_100. */
	private static final long cl_0_100 = cl_0_010 * cl_0_010;

	/** Constante cl_1_000. */
	private static final long cl_1_000 = cl_0_100 * cl_0_010;

	/** Constante cs_CERO. */
	private static final String cs_CERO = " CERO ";

	/** Constante cs_UN. */
	private static final String cs_UN = " UN ";

	/** Constante cs_DOS. */
	private static final String cs_DOS = " DOS ";

	/** Constante cs_TRES. */
	private static final String cs_TRES = " TRES ";

	/** Constante cs_CUATRO. */
	private static final String cs_CUATRO = " CUATRO ";

	/** Constante cs_CINCO. */
	private static final String cs_CINCO = " CINCO ";

	/** Constante cs_SEIS. */
	private static final String cs_SEIS = " SEIS ";

	/** Constante cs_SIETE. */
	private static final String cs_SIETE = " SIETE ";

	/** Constante cs_OCHO. */
	private static final String cs_OCHO = " OCHO ";

	/** Constante cs_NUEVE. */
	private static final String cs_NUEVE = " NUEVE ";

	/** Constante cs_DIEZ. */
	private static final String cs_DIEZ = " DIEZ ";

	/** Constante cs_ONCE. */
	private static final String cs_ONCE = " ONCE ";

	/** Constante cs_DOCE. */
	private static final String cs_DOCE = " DOCE ";

	/** Constante cs_TRECE. */
	private static final String cs_TRECE = " TRECE ";

	/** Constante cs_CATORCE. */
	private static final String cs_CATORCE = " CATORCE ";

	/** Constante cs_QUINCE. */
	private static final String cs_QUINCE = " QUINCE ";

	/** Constante cs_DIECISEIS. */
	private static final String cs_DIECISEIS = " DIECISEIS ";

	/** Constante cs_DIECISIETE. */
	private static final String cs_DIECISIETE = " DIECISIETE ";

	/** Constante cs_DIECIOCHO. */
	private static final String cs_DIECIOCHO = " DIECIOCHO ";

	/** Constante cs_DIECINUEVE. */
	private static final String cs_DIECINUEVE = " DIECINUEVE ";

	/** Constante cs_VEINTE. */
	private static final String cs_VEINTE = " VEINTE ";

	/** Constante cs_VEINTI. */
	private static final String cs_VEINTI = " VEINTI ";

	/** Constante cs_TREINTA. */
	private static final String cs_TREINTA = " TREINTA ";

	/** Constante cs_CUARENTA. */
	private static final String cs_CUARENTA = " CUARENTA ";

	/** Constante cs_CINCUENTA. */
	private static final String cs_CINCUENTA = " CINCUENTA ";

	/** Constante cs_SESENTA. */
	private static final String cs_SESENTA = " SESENTA ";

	/** Constante cs_SETENTA. */
	private static final String cs_SETENTA = " SETENTA ";

	/** Constante cs_OCHENTA. */
	private static final String cs_OCHENTA = " OCHENTA ";

	/** Constante cs_NOVENTA. */
	private static final String cs_NOVENTA = " NOVENTA ";

	/** Constante cs_CIEN. */
	private static final String cs_CIEN = " CIEN ";

	/** Constante cs_CIENTO. */
	private static final String cs_CIENTO = " CIENTO ";

	/** Constante cs_DOSCIENTOS. */
	private static final String cs_DOSCIENTOS = " DOSCIENTOS ";

	/** Constante cs_TRESCIENTOS. */
	private static final String cs_TRESCIENTOS = " TRESCIENTOS ";

	/** Constante cs_CUATROCIENTOS. */
	private static final String cs_CUATROCIENTOS = " CUATROCIENTOS ";

	/** Constante cs_QUINIENTOS. */
	private static final String cs_QUINIENTOS = " QUINIENTOS ";

	/** Constante cs_SEISCIENTOS. */
	private static final String cs_SEISCIENTOS = " SEISCIENTOS ";

	/** Constante cs_SETECIENTOS. */
	private static final String cs_SETECIENTOS = " SETECIENTOS ";

	/** Constante cs_OCHOCIENTOS. */
	private static final String cs_OCHOCIENTOS = " OCHOCIENTOS ";

	/** Constante cs_NOVECIENTOS. */
	private static final String cs_NOVECIENTOS = " NOVECIENTOS ";

	/** Constante cs_MIL. */
	private static final String cs_MIL = " MIL ";

	/** Constante cs_MILLON. */
	private static final String cs_MILLON = " MILLON ";

	/** Constante cs_MILLONES. */
	private static final String cs_MILLONES = " MILLONES ";

	/** Constante cs_ERROR. */
	private static final String cs_ERROR = " ERROR ";

	/** Constante cs_MENOS. */
	private static final String cs_MENOS = " MENOS ";

	/** Constante cs_Y. */
	private static final String cs_Y = " Y ";

	/** Arreglo de unidades. */
	private static final String[] csa_UNIDADES = 
		{
			cs_CERO, cs_ERROR, cs_DOS, cs_TRES, cs_CUATRO, cs_CINCO, cs_SEIS, cs_SIETE, cs_OCHO, cs_NUEVE
		};

	/** Arreglo de decenas. */
	private static final String[] csa_DECENA_1 = 
		{
			cs_DIEZ, cs_ONCE, cs_DOCE, cs_TRECE, cs_CATORCE, cs_QUINCE, cs_DIECISEIS, cs_DIECISIETE, cs_DIECIOCHO,
			cs_DIECINUEVE
		};

	/** Arreglo de decenas. */
	private static final String[] csa_DECENAS = 
		{
			cs_ERROR, cs_ERROR, cs_VEINTI, cs_TREINTA, cs_CUARENTA, cs_CINCUENTA, cs_SESENTA, cs_SETENTA, cs_OCHENTA,
			cs_NOVENTA
		};

	/** Arreglo de cientos. */
	private static final String[] csa_CIENTOS = 
		{
			cs_ERROR, cs_CIENTO, cs_DOSCIENTOS, cs_TRESCIENTOS, cs_CUATROCIENTOS, cs_QUINIENTOS, cs_SEISCIENTOS,
			cs_SETECIENTOS, cs_OCHOCIENTOS, cs_NOVECIENTOS
		};

	/**
	 * Convierte numeros en letras .
	 *
	 * @param al_numero numero a transformar en letras
	 * @return cadena de texto correspondiente al numero convertido
	 */
	public static String convertirLetras(long al_numero)
	{
		String ls_numero;

		ls_numero = StringUtils.getString(signo(al_numero));

		if(StringUtils.isValidString(ls_numero))
		{
			do
				ls_numero = ls_numero.replaceAll("  ", " ");
			while(ls_numero.indexOf("  ") > -1);

			{
				String ls_nueva;
				String ls_original;

				ls_nueva        = StringUtils.getString(cs_VEINTI);
				ls_original     = ls_nueva.concat(" ");

				do
					ls_numero = ls_numero.replaceAll(ls_original, ls_nueva);
				while(ls_numero.indexOf(ls_original) > -1);
			}
		}

		return ls_numero;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param al_numero correspondiente al valor del tipo de objeto long
	 * @return devuelve el valor de String
	 */
	private static String e00Unidades(final long al_numero)
	{
		return ((al_numero >= cl_0_000) && (al_numero < cl_0_010))
		? ((al_numero == cl_0_001) ? cs_UN : csa_UNIDADES[NumericUtils.getInt(al_numero)]) : cs_ERROR;
	}

	/**
	 * Convierte numero en decenas.
	 *
	 * @param al_numero numero a convertir en letras
	 * @return cadena de texto correspondiente al numero convertido
	 */
	private static String e01Decenas(final long al_numero)
	{
		String ls_numero;

		if((al_numero >= cl_0_010) && (al_numero < cl_0_020))
			ls_numero = csa_DECENA_1[NumericUtils.getInt(al_numero - cl_0_010)];
		else if((al_numero >= cl_0_000) && (al_numero < cl_0_100))
		{
			long ll_modulo;
			long ll_parteEntera;

			ll_modulo          = al_numero % cl_0_010;
			ll_parteEntera     = (al_numero - ll_modulo) / cl_0_010;
			ls_numero          = (ll_parteEntera == cl_0_000) ? new String()
				                                              : ((al_numero == cl_0_020) ? cs_VEINTE
				                                                                         : csa_DECENAS[NumericUtils
					.getInt(ll_parteEntera)]);

			if((ll_modulo > cl_0_000) || (ll_parteEntera == cl_0_000))
				ls_numero = ls_numero.concat((al_numero < cl_0_030) ? new String() : cs_Y).concat(
					    e00Unidades(ll_modulo)
					);
		}
		else
			ls_numero = cs_ERROR;

		return ls_numero;
	}

	/**
	 * Convierte numero en cientos.
	 *
	 * @param al_numero correspondiente al valor del tipo de objeto long
	 * @return cadena de texto correspondiente al numero convertido
	 */
	private static String e02Cientos(final long al_numero)
	{
		String ls_numero;

		if((al_numero >= cl_0_000) && (al_numero < cl_1_000))
		{
			long ll_modulo;
			long ll_parteEntera;

			ll_modulo          = al_numero % cl_0_100;
			ll_parteEntera     = (al_numero - ll_modulo) / cl_0_100;
			ls_numero          = (ll_parteEntera == cl_0_000) ? new String()
				                                              : ((al_numero == cl_0_100) ? cs_CIEN
				                                                                         : csa_CIENTOS[NumericUtils
					.getInt(ll_parteEntera)]);

			if((ll_modulo > cl_0_000) || (ll_parteEntera == cl_0_000))
				ls_numero = ls_numero.concat(e01Decenas(ll_modulo));
		}
		else
			ls_numero = cs_ERROR;

		return ls_numero;
	}

	/**
	 * Fracciona numero de acuerdo a a una posicion.
	 *
	 * @param ala_numero arreglo de numero a convertir
	 * @param ai_posicion posicion que indica la parte a fraccionar
	 * @return numero entero fraccionado
	 */
	private static int e04Fraccionar(long[] ala_numero, final int ai_posicion)
	{
		int  li_posicion;
		long ll_numero;

		li_posicion     = ai_posicion;
		ll_numero       = (ala_numero != null) ? ala_numero[li_posicion] : (-1L);

		if(ll_numero >= cl_1_000)
		{
			long ll_modulo;
			long ll_parteEntera;

			ll_modulo          = ll_numero % cl_1_000;
			ll_parteEntera     = (ll_numero - ll_modulo) / cl_1_000;

			ala_numero[li_posicion]     = ll_modulo;
			li_posicion                 = li_posicion + 1;
			ala_numero[li_posicion]     = ll_parteEntera;
			li_posicion                 = e04Fraccionar(ala_numero, li_posicion);
		}

		return li_posicion;
	}

	/**
	 * Devuelve el valor absoluto de un valor. Si el argumento no es negativo, se devuelve el argumento.
	 * Si el argumento es negativo, se devuelve la negación del argumento.
	 * @param al_numero el argumento cuyo valor absoluto debe determinarse
	 * @return el valor absoluto del argumento.
	 */
	private static String signo(final long al_numero)
	{
		boolean lb_menos;
		int     li_tamano;
		long    ll_numero;
		String  ls_numero;

		li_tamano     = 10;
		lb_menos      = al_numero < cl_0_000;
		ll_numero     = lb_menos ? Math.abs(al_numero) : al_numero;
		ls_numero     = new String();

		{
			int    li_maximaPotencia;
			long[] lla_numero;

			lla_numero        = new long[li_tamano];
			lla_numero[0]     = ll_numero;

			for(int li_i = 1; li_i < li_tamano; li_i++)
				lla_numero[li_i] = -1L;

			li_maximaPotencia = e04Fraccionar(lla_numero, 0);

			for(int li_potencia = li_maximaPotencia; li_potencia >= 0; li_potencia--)
			{
				boolean lb_mil;
				boolean lb_millon;
				long    ll_tmp;

				ll_tmp        = lla_numero[li_potencia];
				lb_mil        = (li_potencia % 2) == 1;
				lb_millon     = li_potencia == 2;

				if(!((lb_mil) && (ll_tmp == cl_0_001)) && !((li_maximaPotencia > 0) && (ll_tmp == cl_0_000)))
					ls_numero = ls_numero.concat(e02Cientos(ll_tmp));

				if((lb_mil) && (ll_tmp > cl_0_000))
					ls_numero = ls_numero.concat(cs_MIL);

				if(lb_millon && ((ll_tmp > cl_0_000) || (li_potencia >= 2)))
					ls_numero = ls_numero.concat(
						    ((ll_tmp == cl_0_001) && (li_potencia == 2)) ? cs_MILLON : cs_MILLONES
						);
			}
		}

		if(lb_menos)
			ls_numero = cs_MENOS.concat(ls_numero);

		return ls_numero;
	}
}
