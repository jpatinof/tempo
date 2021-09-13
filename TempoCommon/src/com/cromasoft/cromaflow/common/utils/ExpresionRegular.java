package com.cromasoft.cromaflow.common.utils;

import java.util.regex.Pattern;


/**
 * Clase para validar Strings con expresiones regulares.
 *
 * @author mblanco
 */
public class ExpresionRegular
{
	/** Propiedad ier expresion regular. */
	private static ExpresionRegular ier_expresionRegular;

	/** Constante cs_CARACTERES_ESPECIALES. */
	private static final String cs_CARACTERES_ESPECIALES = "¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]+|\\{+|\\}+|\\^+|\\<+|\\>+|\\”+|\\-+|\\_+|\\/_+";

	/** Constante cs_CARACTERES_ESPECIALES_SIN_GUION. */
	private static final String cs_CARACTERES_ESPECIALES_SIN_GUION = "¿+|\\?+|\\°+|\\¬+|\\|+|\\!+|\\#+|\\$+|\\%+|\\&+|\\+|\\=+|\\’+|\\¡+|\\++|\\*+|\\~+|\\[+|\\]+|\\{+|\\}+|\\^+|\\<+|\\>+|\\”+|\\+|\\_+|\\/_+";

	/** Constante cs_CORREO_ELECTRONICO. */
	private static final String cs_CORREO_ELECTRONICO = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

	/** Constante cs_DIRECCION. */
	private static final String cs_DIRECCION = "[a-zA-Z0-9 #.\\-\"/\\\\]*";

	/** Constante cs_SOLO_LETRAS. */
	private static final String cs_SOLO_LETRAS = "[a-zA-Z]*";

	/** Constante cs_TELEFONO. */
	private static final String cs_TELEFONO = "[0-9]*";

	/** Constante cs_DOCUMENTO_IDENTIDAD. */
	private static final String cs_DOCUMENTO_IDENTIDAD = "[a-zA-Z0-9]*";

	/** Propiedad ip CARACTERE S ESPECIALES. */
	private Pattern ip_CARACTERES_ESPECIALES;

	/** Propiedad ip CORRE O ELECTRONICO. */
	private Pattern ip_CORREO_ELECTRONICO;

	/** Propiedad ip DIRECCION. */
	private Pattern ip_DIRECCION;

	/** Propiedad ip DOCUMENT O IDENTIDAD. */
	private Pattern ip_DOCUMENTO_IDENTIDAD;

	/** Propiedad ip SOL O LETRAS. */
	private Pattern ip_SOLO_LETRAS;

	/** Propiedad ip TELEFONO. */
	private Pattern ip_TELEFONO;

	/**
	 * Constructor.
	 */
	private ExpresionRegular()
	{
	}

	/**
	 * Retorna instancia de objeto de manera estatica.
	 *
	 * @return ier_expresionRegular instancia de objeto ExpresionRegular
	 */
	public static ExpresionRegular getExpresionRegular()
	{
		if(ier_expresionRegular == null)
			ier_expresionRegular = new ExpresionRegular();

		return ier_expresionRegular;
	}

	/**
	 * Valida String si contiene caracteres especiales.
	 *
	 * @param as_documento La secuencia de caracteres que debe coincidir.
	 * @return verdadero si, y solo si, una subsecuencia de la secuencia de entrada coincide con el patrón de este emparejador.
	 */
	public boolean contieneCaracteresEspeciales(String as_documento)
	{
		return getPatronCaracteresEspeciales().matcher(as_documento).find();
	}

	/**
	 * Valida String si contiene caracteres especiales sin guion.
	 *
	 * @param as_param La secuencia de caracteres que debe coincidir.
	 * @return verdadero si, y solo si, una subsecuencia de la secuencia de entrada coincide con el patrón de este emparejador.
	 */
	public boolean contieneCaracteresEspecialesSinGuion(String as_param)
	{
		return getPatronCaracteresEspecialesSinGuion().matcher(as_param).find();
	}

	/**
	 * Valida String si es un correo electronico valido.
	 *
	 * @param as_correoElectronico La secuencia de caracteres que debe coincidir.
	 * @return verdadero si, y solo si, una subsecuencia de la secuencia de entrada coincide con el patrón de este emparejador.
	 */
	public boolean esCorreoElectronico(String as_correoElectronico)
	{
		return getPatronCorreoElectronico().matcher(as_correoElectronico).matches();
	}

	/**
	 * Valida String si es una direccion.
	 *
	 * @param as_direccion La secuencia de caracteres que debe coincidir.
	 * @return verdadero si, y solo si, una subsecuencia de la secuencia de entrada coincide con el patrón de este emparejador.
	 */
	public boolean esDireccion(String as_direccion)
	{
		return getPatronDireccion().matcher(as_direccion).matches();
	}

	/**
	 * Valida String si es un documento de identidad.
	 *
	 * @param as_cadena La secuencia de caracteres que debe coincidir.
	 * @return verdadero si, y solo si, una subsecuencia de la secuencia de entrada coincide con el patrón de este emparejador.
	 */
	public boolean esDocumentoIdentidad(String as_cadena)
	{
		return getPatronDocumentoIdentidad().matcher(as_cadena).matches();
	}

	/**
	 * Valida String si es solo letras.
	 *
	 * @param as_cadena La secuencia de caracteres que debe coincidir.
	 * @return verdadero si, y solo si, una subsecuencia de la secuencia de entrada coincide con el patrón de este emparejador.
	 */
	public boolean esSoloLetras(String as_cadena)
	{
		return getPatronSoloLetras().matcher(as_cadena).matches();
	}

	/**
	 * Valida String si es telefono.
	 *
	 * @param as_telefono La secuencia de caracteres que debe coincidir.
	 * @return verdadero si, y solo si, una subsecuencia de la secuencia de entrada coincide con el patrón de este emparejador.
	 */
	public boolean esTelefono(String as_telefono)
	{
		return getPatronTelefono().matcher(as_telefono).matches();
	}

	/**
	 * Compila la expresion regular dentro de un patron para caracteres especiales.
	 *
	 * @return la expresión regular dada compilada en un patrón
	 */
	private Pattern getPatronCaracteresEspeciales()
	{
		if(ip_CARACTERES_ESPECIALES == null)
			ip_CARACTERES_ESPECIALES = Pattern.compile(cs_CARACTERES_ESPECIALES);

		return ip_CARACTERES_ESPECIALES;
	}

	/**
	 * Compila la expresion regular dentro de un patron para caracteres especiales sin guion.
	 *
	 * @return la expresión regular dada compilada en un patrón
	 */
	private Pattern getPatronCaracteresEspecialesSinGuion()
	{
		if(ip_CARACTERES_ESPECIALES == null)
			ip_CARACTERES_ESPECIALES = Pattern.compile(cs_CARACTERES_ESPECIALES_SIN_GUION);

		return ip_CARACTERES_ESPECIALES;
	}

	/**
	 * Compila la expresion regular dentro de un patron para correo electronico.
	 *
	 * @return la expresión regular dada compilada en un patrón
	 */
	private Pattern getPatronCorreoElectronico()
	{
		if(ip_CORREO_ELECTRONICO == null)
			ip_CORREO_ELECTRONICO = Pattern.compile(cs_CORREO_ELECTRONICO, Pattern.CASE_INSENSITIVE);

		return ip_CORREO_ELECTRONICO;
	}

	/**
	 * Compila la expresion regular dentro de un patron para direccion.
	 *
	 * @return la expresión regular dada compilada en un patrón
	 */
	private Pattern getPatronDireccion()
	{
		if(ip_DIRECCION == null)
			ip_DIRECCION = Pattern.compile(cs_DIRECCION);

		return ip_DIRECCION;
	}

	/**
	 * Compila la expresion regular dentro de un patron para documento de identidad.
	 *
	 * @return la expresión regular dada compilada en un patrón
	 */
	private Pattern getPatronDocumentoIdentidad()
	{
		if(ip_DOCUMENTO_IDENTIDAD == null)
			ip_DOCUMENTO_IDENTIDAD = Pattern.compile(cs_DOCUMENTO_IDENTIDAD);

		return ip_DOCUMENTO_IDENTIDAD;
	}

	/**
	 * Compila la expresion regular dentro de un patron para solo letras.
	 *
	 * @return la expresión regular dada compilada en un patrón
	 */
	private Pattern getPatronSoloLetras()
	{
		if(ip_SOLO_LETRAS == null)
			ip_SOLO_LETRAS = Pattern.compile(cs_SOLO_LETRAS);

		return ip_SOLO_LETRAS;
	}

	/**
	 * Compila la expresion regular dentro de un patron para telefono.
	 *
	 * @return la expresión regular dada compilada en un patrón
	 */
	private Pattern getPatronTelefono()
	{
		if(ip_TELEFONO == null)
			ip_TELEFONO = Pattern.compile(cs_TELEFONO);

		return ip_TELEFONO;
	}
}
