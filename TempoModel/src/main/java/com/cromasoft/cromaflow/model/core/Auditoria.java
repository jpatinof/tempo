package com.cromasoft.cromaflow.model.core;

import java.io.Serializable;

import java.util.Date;



/**
 * Clase modelo que contiene todos los atributos usados en la auditoria.
 * @author jvaca
 *
 */
public class Auditoria implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = -4200146636467976879L;

	/** Propiedad id fecha creacion. */
	private Date id_fechaCreacion;

	/** Propiedad id fecha modificacion. */
	private Date id_fechaModificacion;

	/** Propiedad is id usuario creacion. */
	private String is_idUsuarioCreacion;

	/** Propiedad is id usuario modificacion. */
	private String is_idUsuarioModificacion;

	/** Propiedad is ip creacion. */
	private String is_ipCreacion;

	/** Propiedad is ip modificacion. */
	private String is_ipModificacion;

	/**
	 * Modifica el valor de fecha creacion.
	 *
	 * @param ad_d asigna el valor a la propiedad fecha creacion
	 */
	public void setFechaCreacion(Date ad_d)
	{
		id_fechaCreacion = ad_d;
	}

	/**
	 * Retorna el valor de fecha creacion.
	 *
	 * @return el valor de fecha creacion
	 */
	public Date getFechaCreacion()
	{
		return id_fechaCreacion;
	}

	/**
	 * Modifica el valor de fecha modificacion.
	 *
	 * @param ad_d asigna el valor a la propiedad fecha modificacion
	 */
	public void setFechaModificacion(Date ad_d)
	{
		id_fechaModificacion = ad_d;
	}

	/**
	 * Retorna el valor de fecha modificacion.
	 *
	 * @return el valor de fecha modificacion
	 */
	public Date getFechaModificacion()
	{
		return id_fechaModificacion;
	}

	/**
	 * Modifica el valor de id usuario creacion.
	 *
	 * @param as_s asigna el valor a la propiedad id usuario creacion
	 */
	public void setIdUsuarioCreacion(String as_s)
	{
		is_idUsuarioCreacion = as_s;
	}

	/**
	 * Retorna el valor de id usuario creacion.
	 *
	 * @return el valor de id usuario creacion
	 */
	public String getIdUsuarioCreacion()
	{
		return is_idUsuarioCreacion;
	}

	/**
	 * Modifica el valor de id usuario modificacion.
	 *
	 * @param as_s asigna el valor a la propiedad id usuario modificacion
	 */
	public void setIdUsuarioModificacion(String as_s)
	{
		is_idUsuarioModificacion = as_s;
	}

	/**
	 * Retorna el valor de id usuario modificacion.
	 *
	 * @return el valor de id usuario modificacion
	 */
	public String getIdUsuarioModificacion()
	{
		return is_idUsuarioModificacion;
	}

	/**
	 * Modifica el valor de ip creacion.
	 *
	 * @param as_s asigna el valor a la propiedad ip creacion
	 */
	public void setIpCreacion(String as_s)
	{
		is_ipCreacion = as_s;
	}

	/**
	 * Retorna el valor de ip creacion.
	 *
	 * @return el valor de ip creacion
	 */
	public String getIpCreacion()
	{
		return is_ipCreacion;
	}

	/**
	 * Modifica el valor de ip modificacion.
	 *
	 * @param as_s asigna el valor a la propiedad ip modificacion
	 */
	public void setIpModificacion(String as_s)
	{
		is_ipModificacion = as_s;
	}

	/**
	 * Retorna el valor de ip modificacion.
	 *
	 * @return el valor de ip modificacion
	 */
	public String getIpModificacion()
	{
		return is_ipModificacion;
	}
}
