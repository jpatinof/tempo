package com.b2bsg.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
* Clase de utilidades de encripción de datos
*
* @author Edgar Prieto
* @version Sep 05, 2004
*/
public class Digest
{
	  
  	/**
  	 * Digest.
  	 *
  	 * @param as_text correspondiente al valor de texto al cual se aplicará el algoritmo de <i>Hash</i>
  	 * @param as_alg correspondiente al valor de Algoritmo <i>Hash</i> a utilizar
  	 * @return el valor de string
  	 */
  	public static String digest(String as_text, String as_alg)
	  {
	    return digest(as_text.getBytes(), as_alg);
	  }
	
	/**
	* Obtener la función <i>Hash</i> de un contenido de bytes utilizando un algoritmo arbitrario
	* @param    aba_content    Contenido de bytes sobre el cual se aplicará el algoritmo de <i>Hash</i>
	* @param    as_alg        Algoritmo <i>Hash</i> a utilizar
	* @return    Función <i>Hash</i> del texto en formato hexadecimal. <code>null</code> en caso de
	*            error
	*/
	public static String digest(byte[] aba_content, String as_alg)
	{
		String ls_digest;

		try
		{
			byte[]        lba_digest;
			MessageDigest lmd_md;

			/* Obtener una instancia del algoritmo */
			lmd_md = getMessageDigest(as_alg);

			/* Ajustar el contenido */
			lmd_md.update(aba_content);

			/* Obtener la funcion hash del contenido */
			lba_digest     = lmd_md.digest();
			ls_digest      = new String();

			/* Convertir el resultado de la funcion hash a un formato hexadecimal */
			ls_digest      = toHexa(lba_digest);
		}
		catch(NoSuchAlgorithmException lnsae_e)
		{
			/* No se pudo obtener una instancia del algoritmo. Retornar null */
			ls_digest = null;
		}

		return ls_digest;
	}

	/**
	* Obtener una instacia de <code>MessageDigest</code> dado un algoritimo
	* @param as_alg Algortimo del cual se desea obtener una instancia
	* @return Instacia de <code>MessageDigest</code> para al algoritomo indicado por <i>as_alg</i>
	* @throws NoSuchAlgorithmException Cuando el algoritmo no existe o no puede ser intanciado
	*/
	private static MessageDigest getMessageDigest(String as_alg)
	    throws NoSuchAlgorithmException
	{
		MessageDigest lmd_md;

		/* Obtener una instancia del algoritmo */
		lmd_md = MessageDigest.getInstance(as_alg);

		/* Ajustar las propiedades del algoritmo */
		lmd_md.reset();

		return lmd_md;
	}

	/**
	* Convertir el contenido de una arreglo de bytes a representacion hexadecimal en un objeto
	* <code>String</code>
	* @param aba_digest Arreglo de bytes
	* @return Representación hexadecimal en formato <code>String</code> del arreglo <i>aba_digest</i>
	*/
	private static String toHexa(byte[] aba_digest)
	{
		String ls_digest;

		ls_digest = new String();

		for(int li_i = 0; li_i < aba_digest.length; li_i++)
		{
			ls_digest += Integer.toString(((aba_digest[li_i] & 0xF0) >> 4), 16);
			ls_digest += Integer.toString((aba_digest[li_i] & 0x0F), 16);
		}

		return ls_digest;
	}
}
