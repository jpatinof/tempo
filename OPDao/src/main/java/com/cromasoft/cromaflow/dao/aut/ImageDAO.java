package com.cromasoft.cromaflow.dao.aut;

import com.b2bsg.common.exception.B2BException;

import com.cromasoft.cromaflow.dao.auditoria.AuditoriaDao;

import com.cromasoft.cromaflow.model.aut.Image;
import com.cromasoft.cromaflow.model.aut.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Clase que contiene todos las consultas relacionadas con la tabla
 * USUARIO_SCH.AUT_USUARIO.
 *
 * @author jpatino
 */
public abstract class ImageDAO extends AuditoriaDao
{
	/**
	 * Método que retorna la constante cs_FIND_BY_ID
	 *
	 * @return cs_FIND_BY_ID
	 */
	public abstract String csFindById();

	/**
	 * Retorna el valor Usuario.
	 *
	 * @param ai_id correspondiente al valor del tipo de objeto Usuario
	 * @return devuelve el valor del objeto usuario
	 * @throws B2BException Señala que se ha producido una excepción
	 * @see User
	 */
	public Image findById(int ai_id)
	    throws B2BException
	{
		Image ls_object;

		ls_object = null;

		if(ai_id > 0)
		{
			PreparedStatement lps_ps;
			ResultSet         lrs_rs;

			lps_ps     = null;
			lrs_rs     = null;

			try
			{
				lps_ps = getConnection().prepareStatement(csFindById());

				lps_ps.setInt(1, ai_id);

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
	private Image getObjectFromResultSet(ResultSet ars_rs)
	    throws SQLException, B2BException
	{
		Image lu_image;

		lu_image = new Image();

		lu_image.setIdImage(ars_rs.getInt("image_id"));
		lu_image.setData(ars_rs.getBytes("data"));

//		fillAuditoria(ars_rs, lu_usuario);
		return lu_image;
	}
}
