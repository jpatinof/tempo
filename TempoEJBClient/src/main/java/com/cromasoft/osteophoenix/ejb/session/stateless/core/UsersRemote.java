package com.cromasoft.osteophoenix.ejb.session.stateless.core;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.model.aut.Login;

import javax.ejb.Remote;


/**
 * Interface que contiene todos las propiedades CromaflowRemote.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: mar 31, 2021
 */
@Remote
public interface UsersRemote
{
	/**
	 * Login.
	 *
	 * @param as_userId de as user id
	 * @param as_password de as password
	 * @return el valor de login
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public Login login(String as_userId, String as_password)
	    throws B2BException;
}
