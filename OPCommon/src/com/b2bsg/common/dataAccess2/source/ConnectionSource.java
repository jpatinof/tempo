package com.b2bsg.common.dataAccess2.source;


/**
 * Interface que contiene todos las propiedades ConnectionSource para establecer las
 * conexiones con lod motores de datos.
 *
 * @author Edgar Prieto
 */
public interface ConnectionSource
{
	/** Propiedad default bundle. */
	public final String DEFAULT_BUNDLE = "com.b2bsg.common.dataAccess2.source.conf.connection";

	/**
	 * Valida la propiedad include connection id in thread name.
	 *
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en include connection id in thread name
	 */
	public boolean isIncludeConnectionIdInThreadName();

	/**
	 * Modifica el valor de password.
	 *
	 * @param as_password asigna el valor a la propiedad password
	 */
	public void setPassword(String as_password);

	/**
	 * Retorna el valor de password.
	 *
	 * @return el valor de password
	 */
	public String getPassword();

	/**
	 * Modifica el valor de user.
	 *
	 * @param as_user asigna el valor a la propiedad user
	 */
	public void setUser(String as_user);

	/**
	 * Retorna el valor de user.
	 *
	 * @return el valor de user
	 */
	public String getUser();

	/**
	 * Retorna el valor de vendor.
	 *
	 * @return el valor de vendor
	 */
	public String getVendor();

	/**
	 * Retorna el valor del objeto de java.sql.Connection
	 *
	 * @return devuelve el valor de java.sql.Connection
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see java.sql.Connection
	 */
	public java.sql.Connection create()
	    throws com.b2bsg.common.exception.B2BException;
}
