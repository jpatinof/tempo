package com.cromasoft.cromaflow.dao.aut;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.dao.auditoria.AuditoriaDao;

import com.cromasoft.cromaflow.model.aut.Role;
import com.cromasoft.cromaflow.model.aut.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla USUARIO_SCH.AUT_USUARIO.
 *
 * @author jpatino
 */
public abstract class RoleDAO extends AuditoriaDao
{
	/**
	 * M�todo que retorna la constante cs_FIND_BY_ID
	 * @return cs_FIND_BY_ID
	 */
	public abstract String csFindById();

	/**
	 * Retorna el valor Usuario.
	 *
	 * @param as_roleId correspondiente al valor del tipo de objeto Usuario
	 * @return devuelve el valor del objeto usuario
	 * @throws B2BException Se�ala que se ha producido una excepci�n
	 * @see User
	 */
	public Role findById(int as_roleId)
	    throws B2BException
	{
		Role lcr_roles;

		lcr_roles = null;

		{
			PreparedStatement lps_ps;
			ResultSet         lrs_rs;

			lps_ps     = null;
			lrs_rs     = null;

			try
			{
				lps_ps = getConnection().prepareStatement(csFindById());

				lps_ps.setInt(1, as_roleId);

				lrs_rs        = lps_ps.executeQuery();
				lcr_roles     = new Role();

				if(lrs_rs.next())
					lcr_roles = getObjectFromResultSet(lrs_rs);
			}
			catch(SQLException lse_e)
			{
				logError(this, "findById", lse_e);

				throw new B2BException(SQL_ERROR, lse_e);
			}
			finally
			{
				close(lrs_rs);
				close(lps_ps);
			}
		}

		return lcr_roles;
	}

	/**
	 * Retorna el valor de Usuario.
	 *
	 * @param ars_rs correspondiente al valor del tipo de objeto ResultSet
	 * @return el valor de Usuario
	 * @throws SQLException Se�ala que se ha producido una excepci�n
	 * @throws B2BException Se�ala que se ha producido una excepci�n
	 */
	private Role getObjectFromResultSet(ResultSet ars_rs)
	    throws SQLException, B2BException
	{
		Role lr_role;

		lr_role = new Role();

		lr_role.setRoleId(ars_rs.getInt("role_id"));
		lr_role.setDescription(ars_rs.getString("description"));
		lr_role.setActive(ars_rs.getString("active"));

//		fillAuditoria(ars_rs, lu_usuario);
		return lr_role;
	}
}
