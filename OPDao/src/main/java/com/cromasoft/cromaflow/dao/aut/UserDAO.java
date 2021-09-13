package com.cromasoft.cromaflow.dao.aut;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.dao.auditoria.AuditoriaDao;

import com.cromasoft.cromaflow.model.aut.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla USUARIO_SCH.AUT_USUARIO.
 *
 * @author jpatino
 */
public abstract class UserDAO extends AuditoriaDao
{
	/**
	 * Método que retorna la constante cs_FIND_BY_ID
	 * @return cs_FIND_BY_ID
	 */
	public abstract String csFindById();

	/**
	 * Retorna el valor Usuario.
	 *
	 * @param as_idUsuario correspondiente al valor del tipo de objeto Usuario
	 * @return devuelve el valor del objeto usuario
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see User
	 */
	public User findById(String as_idUsuario)
	    throws B2BException
	{
		User ls_object;
		ls_object = null;

		if(as_idUsuario != null)
		{
			PreparedStatement lps_ps;
			ResultSet         lrs_rs;

			lps_ps     = null;
			lrs_rs     = null;

			try
			{
				lps_ps = getConnection().prepareStatement(csFindById());

				lps_ps.setString(1, as_idUsuario);

				lrs_rs = lps_ps.executeQuery();

				if(lrs_rs.next())
					ls_object = getObjectFromResultSet(lrs_rs);
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
	private User getObjectFromResultSet(ResultSet ars_rs)
	    throws SQLException, B2BException
	{
		User lu_usuario;

		lu_usuario = new User();

		lu_usuario.setUserId(ars_rs.getString("user_id"));
		lu_usuario.setDocumentType(ars_rs.getString("document_type"));
		lu_usuario.setIdNumber(ars_rs.getInt("id_number"));
		lu_usuario.setFirstName(ars_rs.getString("first_name"));
		lu_usuario.setSecondName(ars_rs.getString("second_name"));
		lu_usuario.setFirstLastName(ars_rs.getString("first_lastname"));
		lu_usuario.setSecondLastName(ars_rs.getString("second_lastname"));
		lu_usuario.setActive(ars_rs.getString("active"));
		lu_usuario.setPassword(ars_rs.getString("password"));
		lu_usuario.setGender(ars_rs.getString("gender"));

//		fillAuditoria(ars_rs, lu_usuario);
		return lu_usuario;
	}
}
