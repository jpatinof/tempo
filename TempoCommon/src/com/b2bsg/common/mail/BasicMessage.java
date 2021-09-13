package com.b2bsg.common.mail;

import java.util.Date;

/**
 * Objeto base de correo.
 *
 * @author Fredy Posada
 * @version 1.0
 */
public class BasicMessage implements java.io.Serializable, Comparable
{
	private Date			  id_receivedDate  = new Date();
	private Date			  id_sendDate	   = new Date();
	private String			  is_body;
	private String			  is_cc;
	private String			  is_from;
	private String			  is_subject;
	private String			  is_to;
	private String[]		  isa_filenames;
	private int				  ii_size		   = 0;
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor base de correo
	 */
	public BasicMessage()
	{
		super();
	}

	/**
	 * Contructor para crear objeto base de correo con campo inicializados
	 *
	 * @param as_from
	 *            Remitente
	 * @param as_to
	 *            Destinatario
	 * @param as_cc
	 *            Destinatario Opcional
	 * @param as_subject
	 *            Asunto
	 * @param as_body
	 *            Contenido del mensaje
	 * @param ad_senddate
	 *            fecha de envío
	 * @param ad_receivedDate
	 *            Fecha de recepción
	 * @param ai_size
	 *            Tamaño del correo
	 */
	public BasicMessage(
		String as_from,
		String as_to,
		String as_cc,
		String as_subject,
		String as_body,
		Date   ad_senddate,
		Date   ad_receivedDate,
		int    ai_size
	)
	{
		is_from			    = as_from;
		is_to			    = as_to;
		is_cc			    = as_cc;
		is_subject		    = as_subject;
		is_body			    = as_body;
		id_sendDate		    = ad_senddate;
		id_receivedDate     = ad_receivedDate;
		ii_size			    = ai_size;
	}

	/**
	 * @param as_s
	 *            Contenido del coreeo.
	 */
	public void setBody(String as_s)
	{
		is_body = as_s;
	}

	/**
	 * @return Contenido del correo
	 */
	public String getBody()
	{
		return is_body;
	}

	/**
	 * @param as_s
	 *            Dirección de Correo (Copia)
	 */
	public void setCc(String as_s)
	{
		is_cc = as_s;
	}

	/**
	 * @return Dirección de correo (Copia)
	 */
	public String getCc()
	{
		return is_cc;
	}

	/**
	 *
	 * @return Archivos a adjuntar
	 */
	public String[] getFilenames()
	{
		return isa_filenames;
	}

	/**
	 * @param as_s
	 *            Dirección de remitente
	 */
	public void setFrom(String as_s)
	{
		is_from = as_s;
	}

	/**
	 * @return Dirección de remitente
	 */
	public String getFrom()
	{
		return is_from;
	}

	/**
	 * @return Fecha de recepción
	 */
	public Date getReceivedDate()
	{
		return id_receivedDate;
	}

	/**
	 * @param ad_d
	 *            Fecha de envío
	 */
	public void setSendDate(Date ad_d)
	{
		id_sendDate = ad_d;
	}

	/**
	 * @return Fecha de envío
	 */
	public Date getSendDate()
	{
		return id_sendDate;
	}

	/**
	 * @return Tamaño del mensaje
	 */
	public int getSize()
	{
		return ii_size;
	}

	/**
	 * @param as_subject
	 *            Asunto de correo
	 */
	public void setSubject(String as_subject)
	{
		is_subject = as_subject;
	}

	/**
	 * @return Asunto de correo
	 */
	public String getSubject()
	{
		return is_subject;
	}

	/**
	 *
	 * @param asa_sa Archivos a adjuntar
	 */
	public void setTilenames(String[] asa_sa)
	{
		isa_filenames = asa_sa;
	}

	/**
	 * @param as_s
	 *            Destinatario de Correo
	 */
	public void setTo(String as_s)
	{
		is_to = as_s;
	}

	/**
	 * @return Destinatario de Correo
	 */
	public String getTo()
	{
		return is_to;
	}

	/**
	 * Método de comparación
	 *
	 * @param ao_o
	 *            Objeto a comparar
	 *
	 * @return peso de objeto
	 */
	public int compareTo(Object ao_o)
	{
		int li_return = 0;

		if(ao_o instanceof BasicMessage)
		{
			BasicMessage lbm_other = (BasicMessage)ao_o;

			li_return = id_sendDate.compareTo(lbm_other.getSendDate());
		}

		return li_return;
	}
}
