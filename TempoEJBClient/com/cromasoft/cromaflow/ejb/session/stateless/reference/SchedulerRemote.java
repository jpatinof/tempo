package com.cromasoft.cromaflow.ejb.session.stateless.reference;

import com.b2bsg.common.exception.B2BException;


/**
 * Interface que contiene las interacciones con parametrización de constantes para los Scheduler de Quartzs.
 *
 * @author Edgar Prieto
 * Fecha de Creacion: 19/08/2020
 */
public interface SchedulerRemote
{
	/**
	 * Obtener la propiedad caracter de una constante
	 * @param as_idConstante Codigo de la constante a buscar
	 * @return Propiedad caracter de la constante identificada con as_idConstante
	 * @throws B2BException Señala que se ha generado una excepción
	 */
	public String obtenerCaracterConstante(String as_idConstante)
	    throws B2BException;

	/**
	 * Obtener todos los ID_CONSTANTE que coincidan con un patrón y su el valor de la columna CARACTER concida sea
	 * igual a un valor
	 *
	 * @param as_IdLike Patrón sobre el cual se realizará búsqueda tipo like en en campo ID_CONSTANTE
	 * @param as_caracter Valor de coincidencia del campo CARACTER
	 * @return Listado de ID_CONSTANTE
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	public java.util.Collection<String> obtenerIdConstanesPorCaracterIdLikeCaracter(
	    String as_IdLike, String as_caracter
	)
	    throws B2BException;

	/**
	 * Update caracter.
	 *
	 * @param as_id de as id
	 * @param as_caracter de as caracter
	 * @param as_userId de as user id
	 * @throws B2BException de b 2 B exception
	 */
	public void updateCaracter(String as_id, String as_caracter, String as_userId)
	    throws B2BException;
}
