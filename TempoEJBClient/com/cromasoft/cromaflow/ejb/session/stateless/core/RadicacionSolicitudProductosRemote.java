package com.cromasoft.cromaflow.ejb.session.stateless.core;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.model.aut.Opcion;
import com.cromasoft.cromaflow.model.aut.Usuario;

import com.cromasoft.cromaflow.model.core.RadicacionSolicitudProductos;

import java.util.Collection;

import javax.ejb.Remote;


/**
 * Interface que contiene todos las propiedades CromaflowRemote.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: mar 31, 2021
 */
@Remote
public interface RadicacionSolicitudProductosRemote
{
	public Collection<Opcion> cargarOpcionesMenu(
	    String as_userId, String as_idComponente, String as_localIp, String as_remoteIp
	)
	    throws B2BException;

	public Usuario findUserById(Usuario ate_parametros)
	    throws B2BException;

	public void save(RadicacionSolicitudProductos arsp_rsp)
	    throws B2BException;
}
