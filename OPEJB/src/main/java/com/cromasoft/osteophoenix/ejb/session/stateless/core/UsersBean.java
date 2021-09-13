package com.cromasoft.osteophoenix.ejb.session.stateless.core;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.business.core.UsersBusiness;

import com.cromasoft.cromaflow.model.aut.Login;

import com.cromasoft.osteophoenix.ejb.session.stateless.Logger;

import org.perf4j.StopWatch;


/**
 * Clase que contiene todos las propiedades CromaflowBean.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: ene 22, 2021
 */
@javax.ejb.Stateless(name = "RadicacionSolicitudProductos", mappedName = "radicacionSolicitudProductosMappedName")
@javax.ejb.TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class UsersBean implements UsersRemote
{
	/** Propiedad irb business. */
	private UsersBusiness iub_business;

	/**
	*  {@inheritdoc}.
	*
	* @param ate_parametros de ate parametros
	* @return el valor de usuario
	* @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	*/
	public Login login(String as_userId, String as_password)
	    throws B2BException
	{
		StopWatch lsw_watch;
		Login     ll_loginData;

		lsw_watch        = Logger.getNewStopWatch();
		ll_loginData     = getUsersBusiness().login(as_userId, as_password);

		Logger.log(lsw_watch, "UsersBean", "findUserById", null, null, null, null);

		return ll_loginData;
	}

	/**
	 * Retorna el valor de reference business.
	 *
	 * @return el valor de reference business
	 */
	private UsersBusiness getUsersBusiness()
	{
		if(iub_business == null)
			iub_business = new UsersBusiness();

		return iub_business;
	}
}
