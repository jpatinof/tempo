package com.cromasoft.cromaflow.dao.aut;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.dao.auditoria.AuditoriaDao;

import com.cromasoft.cromaflow.model.aut.User;
import com.cromasoft.cromaflow.model.aut.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla USUARIO_SCH.AUT_USUARIO.
 *
 * @author jpatino
 */
public abstract class UserRoleDAO extends AuditoriaDao
{
	/**
	 * Método que retorna la constante cs_FIND_BY_ID
	 * @return cs_FIND_BY_ID
	 */
	public abstract String csFindByUserId();

	/**
	 * Retorna el valor Usuario.
	 *
	 * @param as_idUsuario correspondiente al valor del tipo de objeto Usuario
	 * @return devuelve el valor del objeto usuario
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see User
	 */
	public Collection<UserRole> findByUserId(String as_idUsuario)
	    throws B2BException
	{
		Collection<UserRole> ls_object;
		ls_object = null;

		if(as_idUsuario != null)
		{
			PreparedStatement lps_ps;
			ResultSet         lrs_rs;

			lps_ps     = null;
			lrs_rs     = null;

			try
			{
				lps_ps = getConnection().prepareStatement(csFindByUserId());

				lps_ps.setString(1, as_idUsuario);

				lrs_rs        = lps_ps.executeQuery();
				ls_object     = new ArrayList<UserRole>(1);

				while(lrs_rs.next())
					ls_object.add(getObjectFromResultSet(lrs_rs));
			}
			catch(SQLException lse_e)
			{
				logError(this, "findByUserId", lse_e);

				throw new B2BException(SQL_ERROR, lse_e);
			}
			finally
			{
				close(lrs_rs);
				close(lps_ps);
			}
		}

		return ls_object;
	}

	/**
	 * Retorna el valor de Usuario.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @return el valor de Usuario
	 * @throws SQLException Señala que se ha producido una excepción
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	private UserRole getObjectFromResultSet(ResultSet ars_rs)
	    throws SQLException, B2BException
	{
		UserRole lu_usuario;

		lu_usuario = new UserRole();

		lu_usuario.setUserRoleId(ars_rs.getInt("user_role_id"));
		lu_usuario.setUserId(ars_rs.getString("user_id"));
		lu_usuario.setRoleId(ars_rs.getInt("role_id"));
		lu_usuario.setActive(ars_rs.getString("active"));

//		fillAuditoria(ars_rs, lu_usuario);
		return lu_usuario;
	}
}
