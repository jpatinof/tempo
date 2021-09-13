package com.b2bsg.common.web.axis;

import com.b2bsg.common.util.StringUtils;


/**
 * Clase que contiene todos las propiedades de RequestInfo y obtener la informacion de cada request
 *
 * @author Edgar Prieto
 */
public class RequestInfo implements java.io.Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Propiedad is local ip. */
	private String is_localIp;

	/** Propiedad is remote ip. */
	private String is_remoteIp;

	/**
	 * Instancia un nuevo objeto request info.
	 */
	public RequestInfo()
	{
		this(null, null);
	}

	/**
	 * Instancia un nuevo objeto request info.
	 *
	 * @param as_localIp correspondiente al valor del tipo de objeto String
	 * @param as_remoteIp correspondiente al valor del tipo de objeto String
	 */
	public RequestInfo(String as_localIp, String as_remoteIp)
	{
		setLocalIp(as_localIp);
		setRemoteIp(as_remoteIp);
	}

	/**
	 * Modifica el valor de local ip.
	 *
	 * @param as_s asigna el valor a la propiedad local ip
	 */
	public void setLocalIp(String as_s)
	{
		is_localIp = StringUtils.getString(as_s);
	}

	/**
	 * Retorna el valor de local ip.
	 *
	 * @return el valor de local ip
	 */
	public String getLocalIp()
	{
		return is_localIp;
	}

	/**
	 * Modifica el valor de remote ip.
	 *
	 * @param as_s asigna el valor a la propiedad remote ip
	 */
	public void setRemoteIp(String as_s)
	{
		is_remoteIp = StringUtils.getString(as_s);
	}

	/**
	 * Retorna el valor de remote ip.
	 *
	 * @return el valor de remote ip
	 */
	public String getRemoteIp()
	{
		return is_remoteIp;
	}
}
