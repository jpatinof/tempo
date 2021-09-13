package com.cromasoft.cromaflow.dao.auditoria;

import com.cromasoft.cromaflow.model.core.Auditoria;


/**
 * Clase DAO que extrae todos los atributos usados en la auditoria apartir de un resulset enviado
 *
 * @author Julian Vaca
 *
 */
public class AuditoriaDao extends com.b2bsg.common.dataAccess2.BaseDAO
{
	/**
	 * Constructor por defecto de clase
	 */
	public AuditoriaDao()
	{
	}

	/**
	 * Método encargado de extraer todos los atributos usados en la auditoria apartir de un resulset enviado
	 *
	 * @param ars_rs Objeto de tipo ResultSet que contiene atributos de auditoria a extraer
	 * @param aa_auditoria Objeto de tipo Auditoria al cual se le asignan atributos de auditoria extraidos
	 * @throws java.sql.SQLException
	 */
	public void fillAuditoria(java.sql.ResultSet ars_rs, Auditoria aa_auditoria)
	    throws java.sql.SQLException
	{
		if(aa_auditoria != null)
		{
			aa_auditoria.setIdUsuarioCreacion(ars_rs.getString("ID_USUARIO_CREACION"));
			aa_auditoria.setFechaCreacion(ars_rs.getTimestamp("FECHA_CREACION"));
			aa_auditoria.setIpCreacion(ars_rs.getString("IP_CREACION"));
			aa_auditoria.setIdUsuarioModificacion(ars_rs.getString("ID_USUARIO_MODIFICACION"));
			aa_auditoria.setFechaModificacion(ars_rs.getTimestamp("FECHA_MODIFICACION"));
			aa_auditoria.setIpModificacion(ars_rs.getString("IP_MODIFICACION"));
		}
	}

	/**
	 * Método encargado de extraer todos los atributos usados en la auditoria de creacion apartir de un resulset enviado
	 *
	 * @param ars_rs Objeto de tipo ResultSet que contiene atributos de auditoria de creacion a extraer
	 * @param aa_auditoria Objeto de tipo Auditoria al cual se le asignan atributos de auditoria de creacion extraidos
	 * @throws java.sql.SQLException
	 */
	public void fillAuditoriaCreacion(java.sql.ResultSet ars_rs, Auditoria aa_auditoria)
	    throws java.sql.SQLException
	{
		if(aa_auditoria != null)
		{
			aa_auditoria.setIdUsuarioCreacion(ars_rs.getString("ID_USUARIO_CREACION"));
			aa_auditoria.setFechaCreacion(ars_rs.getTimestamp("FECHA_CREACION"));
			aa_auditoria.setIpCreacion(ars_rs.getString("IP_CREACION"));
		}
	}
}
