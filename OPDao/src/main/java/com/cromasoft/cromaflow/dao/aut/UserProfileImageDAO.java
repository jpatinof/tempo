package com.cromasoft.cromaflow.dao.aut;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.dao.auditoria.AuditoriaDao;

import com.cromasoft.cromaflow.model.aut.User;
import com.cromasoft.cromaflow.model.aut.UserProfileImage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla
 * USUARIO_SCH.AUT_USUARIO.
 *
 * @author jpatino
 */
public abstract class UserProfileImageDAO extends AuditoriaDao
{
	/**
	 * Método que retorna la constante cs_FIND_BY_ID
	 *
	 * @return cs_FIND_BY_ID
	 */
	public abstract String csFindByUserId();

	/**
	 * Retorna el valor Usuario.
	 *
	 * @param as_userId correspondiente al valor del tipo de objeto Usuario
	 * @return devuelve el valor del objeto usuario
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see User
	 */
	public UserProfileImage findByUserId(String as_userId)
	    throws B2BException
	{
		UserProfileImage ls_object;

		ls_object = null;

		if(as_userId != null)
		{
			PreparedStatement lps_ps;
			ResultSet         lrs_rs;

			lps_ps     = null;
			lrs_rs     = null;

			try
			{
				lps_ps = getConnection().prepareStatement(csFindByUserId());

				lps_ps.setString(1, as_userId);

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
	private UserProfileImage getObjectFromResultSet(ResultSet ars_rs)
	    throws SQLException, B2BException
	{
		UserProfileImage lu_userProfileImage;

		lu_userProfileImage = new UserProfileImage();

		lu_userProfileImage.setUserId(ars_rs.getString("user_id"));
		lu_userProfileImage.setIdImage(ars_rs.getInt("image_id"));

//		fillAuditoria(ars_rs, lu_usuario);
		return lu_userProfileImage;
	}
}
