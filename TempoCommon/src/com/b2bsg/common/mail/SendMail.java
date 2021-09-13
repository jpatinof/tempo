package com.b2bsg.common.mail;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.serviceLocator.ServiceLocator;

import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;

import com.cromasoft.cromaflow.constants.TipoContenidoCommon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.mail.util.ByteArrayDataSource;


/**
 * Clase para envío de correos usando un recurso de correo del servidor de aplicaciones
 * <code>JNDI mail/recurso</code>, el cual tiene la información requerida de conexión al servidor
 * <code>SMTP</code>. <b>Por defecto no se almacenan correos en el servidor SMTP </b>. Para uso,
 * se crea una instancia del objeto y se le asignan los valores de:
 * <ul>
 * <li>Remitente <code>from</code> <b>Requerido </b>
 * <li>Destinatario(s) <code>to</code> <b>Requerido </b>
 * <li>Asunto <code>subject</code> <b>Requerido </b>
 * <li>Mensaje o cuerpo del correo <code>body</code> <b>Requerido </b>
 * <li>Copia(s) oculta <code>BCC</code>
 * <li>Copia(s) <code>CC</code><br>
 * <li>Anexo <code>Archivo Anexo</code>
 * </ul>
 * Una vez se tenga los campos requeridos se puede invocar el método de envío
 * <code>sendMailEvent</code> ó <code>sendMailEvent(Formato)</code>. El formato usado por
 * defecto es HTML
 *
 * @author  Julián David Vaca Rodriguez
 * Fecha de Creacion: 28/03/2020
 */
public class SendMail
{
	/** Formato HTML <code>FORMAT_HTML</code>. */
	public static final String FORMAT_HTML = "text/html";

	/** Formato plano(TXT) <code>FORMAT_TEXT</code>. */
	public static final String FORMAT_TEXT = "text/plain";

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)LoggerHandler.getLogger(SendMail.class);

	/** Constante cb_DEFAULT_STORE. */
	private static final boolean cb_DEFAULT_STORE = false;

	/** Constante cs_USER_PROPERTY. */
	private static final String cs_USER_PROPERTY = "mail.smtp.user";

	/** Constante cs_PASSWORD_PROPERTY. */
	private static final String cs_PASSWORD_PROPERTY = "mail.smtp.password";

	/** Propiedad ic bcc. */
	private Collection ic_bcc;

	/** Propiedad ic cc. */
	private Collection ic_cc;

	/** Propiedad ic to. */
	private Collection ic_to;

	/** Propiedad isa filename. */
	private Map<String, byte[]> imsba_file;

	/** Propiedad is session. */
	private Session is_session;

	/** Propiedad is body. */
	private String is_body;

	/** Propiedad is format. */
	private String is_format;

	/** Propiedad is from. */
	private String is_from;

	/** Propiedad is subject. */
	private String is_subject;

	/** Propiedad ib html code. */
	private boolean ib_htmlCode;

	/** Propiedad ib store. */
	private boolean ib_store;

	/**
	 * Constructor para instanciar la clase, recibiendo una sesión de correo.
	 *
	 * @param as_session
	 *        <code>javax.mail.Session</code> sessión a usar para el envío de mensajes.
	 * @param ab_store
	 *        para indicar que se almacenará el mensaje.
	 */
	public SendMail(Session as_session, boolean ab_store)
	{
		setSession(as_session);
		setStore(ab_store);
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param ap_mail de ap mail
	 * @param ab_store de ab store
	 */
	public SendMail(Properties ap_mail, boolean ab_store)
	{
		this(createSession(ap_mail), ab_store);
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param as_session de as session
	 */
	public SendMail(Session as_session)
	{
		this(as_session, cb_DEFAULT_STORE);
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param ap_mail de ap mail
	 */
	public SendMail(Properties ap_mail)
	{
		this(createSession(ap_mail));
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param ab_store de ab store
	 */
	public SendMail(boolean ab_store)
	{
		this(createConfigSession(), ab_store);
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 */
	public SendMail()
	{
		this(cb_DEFAULT_STORE);
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param as_jndi de as jndi
	 * @param ab_fromConfig de ab from config
	 * @param ab_env de ab env
	 * @param ab_store de ab store
	 */
	public SendMail(String as_jndi, boolean ab_fromConfig, boolean ab_env, boolean ab_store)
	{
		this(createSession(as_jndi, ab_fromConfig, ab_env), ab_store);
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param as_jndi de as jndi
	 * @param ab_fromConfig de ab from config
	 * @param ab_env de ab env
	 */
	public SendMail(String as_jndi, boolean ab_fromConfig, boolean ab_env)
	{
		this(createSession(as_jndi, ab_fromConfig, ab_env));
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param as_jndi de as jndi
	 * @param ab_env de ab env
	 */
	public SendMail(String as_jndi, boolean ab_env)
	{
		this(createConfigSession(as_jndi, ab_env));
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param as_jndi de as jndi
	 */
	public SendMail(String as_jndi)
	{
		this(createConfigSession(as_jndi));
	}

	/**
	 * Instancia un nuevo objeto send mail.
	 *
	 * @param ab_env de ab env
	 * @param ab_store de ab store
	 */
	public SendMail(boolean ab_env, boolean ab_store)
	{
		this(createConfigSession(ab_env), ab_store);
	}

	/**
	 * Creates the config session.
	 *
	 * @param as_jndi de as jndi
	 * @param ab_env de ab env
	 * @return el valor de session
	 */
	public static Session createConfigSession(String as_jndi, boolean ab_env)
	{
		return createSession(as_jndi, true, ab_env);
	}

	/**
	 * Creates the config session.
	 *
	 * @param ab_env de ab env
	 * @return el valor de session
	 */
	public static Session createConfigSession(boolean ab_env)
	{
		return createConfigSession(null, ab_env);
	}

	/**
	 * Creates the config session.
	 *
	 * @param as_jndi de as jndi
	 * @return el valor de session
	 */
	public static Session createConfigSession(String as_jndi)
	{
		return createConfigSession(as_jndi, ServiceLocator.DEFAUL_ENV);
	}

	/**
	 * Creates the config session.
	 *
	 * @return el valor de session
	 */
	public static Session createConfigSession()
	{
		return createConfigSession(ServiceLocator.DEFAUL_ENV);
	}

	/**
	 * Creates the session.
	 *
	 * @param ap_mail de ap mail
	 * @return el valor de session
	 */
	public static Session createSession(Properties ap_mail)
	{
		Session ls_session;
		String  ls_password;
		String  ls_user;

		if(ap_mail != null)
		{
			ls_password     = ap_mail.getProperty(cs_PASSWORD_PROPERTY);
			ls_user         = ap_mail.getProperty(cs_USER_PROPERTY);
		}
		else
		{
			ls_password     = null;
			ls_user         = null;
		}

		if(StringUtils.isValidString(ls_password) && StringUtils.isValidString(ls_user))
		{
			final String ls_passwordTmp = ls_password;
			final String ls_userTmp     = ls_user;

			ls_session                  = Session.getDefaultInstance(
				    ap_mail,
				    new javax.mail.Authenticator()
					{
						protected PasswordAuthentication getPasswordAuthentication()
						{
							return new PasswordAuthentication(ls_userTmp, ls_passwordTmp);
						}
					}
				);
		}
		else
			ls_session = Session.getDefaultInstance(ap_mail);

		return ls_session;
	}

	/**
	 * Modifica el valor de Attach.
	 *
	 * @param amsba_file de amsba file
	 */
	public void setAttach(Map<String, byte[]> amsba_file)
	{
		imsba_file = amsba_file;
	}

	/**
	 * Modifica el valor de Bcc.
	 *
	 * @param asa_sa de asa sa
	 */
	public void setBcc(String[] asa_sa)
	{
		if(asa_sa != null)
			for(int li_i = 0; li_i < asa_sa.length; li_i++)
				setBcc(StringUtils.getString(asa_sa[li_i]));
	}

	/**
	 * Modifica el valor de Bcc.
	 *
	 * @param ac_c de ac c
	 */
	public void setBcc(Collection ac_c)
	{
		ic_bcc = ac_c;
	}

	/**
	 * Modifica el valor de Bcc.
	 *
	 * @param as_s de as s
	 */
	public void setBcc(String as_s)
	{
		ic_bcc = init(ic_bcc);

		if(StringUtils.isValidString(as_s))
			ic_bcc.add(StringUtils.getString(as_s));
	}

	/**
	 * Retorna Objeto o variable de valor bcc.
	 *
	 * @return el valor de bcc
	 */
	public Collection getBcc()
	{
		return ic_bcc;
	}

	/**
	 * Modifica el valor de Body.
	 *
	 * @param as_body        El cuerpo del mensaje a establecer.
	 */
	public void setBody(String as_body)
	{
		is_body = StringUtils.getString(as_body);
	}

	/**
	 * Retorna Objeto o variable de valor body.
	 *
	 * @return el valor de body
	 */
	public String getBody()
	{
		return is_body;
	}

	/**
	 * Modifica el valor de Cc.
	 *
	 * @param asa_sa de asa sa
	 */
	public void setCc(String[] asa_sa)
	{
		if(asa_sa != null)
			for(int li_i = 0; li_i < asa_sa.length; li_i++)
				setCc(StringUtils.getString(asa_sa[li_i]));
	}

	/**
	 * Modifica el valor de Cc.
	 *
	 * @param ac_c de ac c
	 */
	public void setCc(Collection ac_c)
	{
		ic_cc = ac_c;
	}

	/**
	 * Modifica el valor de Cc.
	 *
	 * @param as_s de as s
	 */
	public void setCc(String as_s)
	{
		ic_cc = init(ic_cc);

		if(StringUtils.isValidString(as_s))
			ic_cc.add(StringUtils.getString(as_s));
	}

	/**
	 * Retorna Objeto o variable de valor cc.
	 *
	 * @return el valor de cc
	 */
	public Collection getCc()
	{
		return ic_cc;
	}

	/**
	 * Modifica el valor de Format.
	 *
	 * @param as_s de as s
	 */
	public void setFormat(String as_s)
	{
		is_format = StringUtils.getString(as_s);
	}

	/**
	 * Retorna Objeto o variable de valor format.
	 *
	 * @return el valor de format
	 */
	public String getFormat()
	{
		return is_format;
	}

	/**
	 * Modifica el valor de From.
	 *
	 * @param as_from        El remitente a establecer.
	 */
	public void setFrom(String as_from)
	{
		if(StringUtils.isValidString(as_from))
			is_from = StringUtils.getString(as_from);
	}

	/**
	 * Retorna Objeto o variable de valor from.
	 *
	 * @return el valor de from
	 */
	public String getFrom()
	{
		return is_from;
	}

	/**
	 * Modifica el valor de htmlCode
	 *
	 * @param Asigna el valor de htmlCode a htmlCode
	 */
	public void setHtmlCode(boolean ab_b)
	{
		ib_htmlCode = ab_b;
	}

	/**
	 * Retorna objeto o variable de valor htmlCode
	 *
	 * @return el valor de htmlCode
	 */
	public boolean isHtmlCode()
	{
		return ib_htmlCode;
	}

	/**
	 * Modifica el valor de Session.
	 *
	 * @param as_s de as s
	 */
	public void setSession(Session as_s)
	{
		is_session = as_s;

		if(is_session != null)
			is_session.setDebug(clh_LOGGER.isDebugEnabled());
	}

	/**
	 * Retorna Objeto o variable de valor session.
	 *
	 * @return <code>javax.mail.Session</code> Sesión de correo
	 */
	public Session getSession()
	{
		return is_session;
	}

	/**
	 * Modifica el valor de Store.
	 *
	 * @param ab_b de ab b
	 */
	public void setStore(boolean ab_b)
	{
		ib_store = ab_b;
	}

	/**
	 * Valida la propiedad store.
	 *
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en store
	 */
	public boolean isStore()
	{
		return ib_store;
	}

	/**
	 * Modifica el valor de Subject.
	 *
	 * @param as_subject        El asunto a establecer.
	 */
	public void setSubject(String as_subject)
	{
		is_subject = StringUtils.getString(as_subject);
	}

	/**
	 * Retorna Objeto o variable de valor subject.
	 *
	 * @return el valor de subject
	 */
	public String getSubject()
	{
		return is_subject;
	}

	/**
	 * Modifica el valor de To.
	 *
	 * @param asa_sa de asa sa
	 */
	public void setTo(String[] asa_sa)
	{
		if(asa_sa != null)
			for(int li_i = 0; li_i < asa_sa.length; li_i++)
				setTo(StringUtils.getString(asa_sa[li_i]));
	}

	/**
	 * Modifica el valor de To.
	 *
	 * @param ac_c de ac c
	 */
	public void setTo(Collection ac_c)
	{
		ic_to = ac_c;
	}

	/**
	 * Modifica el valor de To.
	 *
	 * @param as_s de as s
	 */
	public void setTo(String as_s)
	{
		ic_to = init(ic_to);

		if(StringUtils.isValidString(as_s))
			ic_to.add(StringUtils.getString(as_s));
	}

	/**
	 * Retorna Objeto o variable de valor to.
	 *
	 * @return el valor de to
	 */
	public Collection getTo()
	{
		return ic_to;
	}

	/**
	 * Creates the session.
	 *
	 * @param as_jndi de as jndi
	 * @param ab_fromConfig de ab from config
	 * @param ab_env de ab env
	 * @return el valor de session
	 */
	public static Session createSession(String as_jndi, boolean ab_fromConfig, boolean ab_env)
	{
		ServiceLocator lsl_locator;

		lsl_locator = ServiceLocator.getServiceLocator();

		return ab_fromConfig
		? (StringUtils.isValidString(as_jndi) ? lsl_locator.getConfigMailSession(as_jndi, ab_env)
		                                      : lsl_locator.getConfigMailSession(ab_env))
		: lsl_locator.getMailSession(as_jndi, ab_env);
	}

	/**
	 * Creates the session.
	 *
	 * @param as_jndi de as jndi
	 * @param ab_env de ab env
	 * @return el valor de session
	 */
	public static Session createSession(String as_jndi, boolean ab_env)
	{
		return createSession(as_jndi, false, ab_env);
	}

	/**
	 * Creates the session.
	 *
	 * @param as_jndi de as jndi
	 * @return el valor de session
	 */
	public static Session createSession(String as_jndi)
	{
		return createSession(as_jndi, ServiceLocator.DEFAUL_ENV);
	}

	/**
	 * Send mail event.
	 *
	 * @param ac_to de ac to
	 * @param as_body de as body
	 * @param as_format de as format
	 * @param as_from de as from
	 * @param as_subject de as subject
	 * @throws AddressException cuando se produce algun error en el proceso
	 * @throws MessagingException cuando se produce algun error en el proceso
	 */
	public String sendMailEvent(Collection ac_to, String as_body, String as_format, String as_from, String as_subject)
	    throws AddressException, MessagingException
	{
		setBody(as_body);
		setFormat(as_format);
		setFrom(as_from);
		setSubject(as_subject);

		if(CollectionUtils.isValidCollection(ac_to))
			setTo(ac_to);

		return sendMailEvent();
	}

	/**
	 * Send mail event.
	 *
	 * @param ac_to de ac to
	 * @param as_body de as body
	 * @param as_from de as from
	 * @param as_subject de as subject
	 * @throws AddressException cuando se produce algun error en el proceso
	 * @throws MessagingException cuando se produce algun error en el proceso
	 */
	public String sendMailEvent(Collection ac_to, String as_body, String as_from, String as_subject)
	    throws AddressException, MessagingException
	{
		return sendMailEvent(ac_to, as_body, null, as_from, as_subject);
	}

	/**
	 * Método para el envío de correo.
	 *
	 * @throws AddressException cuando se produce algun error en el proceso
	 * @throws MessagingException cuando se produce algun error en el proceso
	 */
	public String sendMailEvent()
	    throws AddressException, MessagingException
	{
		boolean     lb_debug;
		MimeMessage lmm_msg;
		Session     ls_session;
		String      ls_return;

		lb_debug       = clh_LOGGER.isDebugEnabled();
		ls_session     = getSession();
		lmm_msg        = new MimeMessage(ls_session);
		ls_return      = null;

		if(lb_debug)
		{
			if(ls_session != null)
			{
				Properties lp_p;

				lp_p = ls_session.getProperties();

				if(lp_p != null)
				{
					Enumeration le_keys;

					le_keys = lp_p.keys();

					if(le_keys != null)
					{
						while(le_keys.hasMoreElements())
						{
							String ls_key;

							ls_key = CollectionUtils.getString(le_keys.nextElement());

							if(StringUtils.isValidString(ls_key))
								clh_LOGGER.debug(
								    "sendMailEvent",
								    "Mail session property [" + ls_key + "] :: " + lp_p.getProperty(ls_key)
								);
						}
					}
				}
			}
			else
				clh_LOGGER.debug("sendMailEvent", "Mail session is invalid");
		}

		{
			Collection lc_tmp;

			lc_tmp = getBcc();

			if(CollectionUtils.isValidCollection(lc_tmp))
			{
				if(lb_debug)
					clh_LOGGER.debug("sendMailEvent", "BCC :: ");

				lmm_msg.setRecipients(Message.RecipientType.BCC, toAddress(lc_tmp, lb_debug));
			}
		}

		{
			Collection lc_tmp;

			lc_tmp = getCc();

			if(CollectionUtils.isValidCollection(lc_tmp))
			{
				if(lb_debug)
					clh_LOGGER.debug("sendMailEvent", "CC :: ");

				lmm_msg.setRecipients(Message.RecipientType.CC, toAddress(lc_tmp, lb_debug));
			}
		}

		{
			String ls_tmp;
			String ls_format;

			ls_format     = getFormat();
			ls_tmp        = StringUtils.isValidString(ls_format) ? ls_format : FORMAT_HTML;

			lmm_msg.setHeader("Content-Type", ls_tmp);

			if(lb_debug)
				clh_LOGGER.debug("sendMailEvent", "Content-Type :: " + ls_tmp);
		}

		{
			String ls_tmp;

			ls_tmp = getFrom();

			if(StringUtils.isValidString(ls_tmp))
				lmm_msg.setFrom(new InternetAddress(ls_tmp));

			if(lb_debug)
				clh_LOGGER.debug("sendMailEvent", "From :: " + ls_tmp);
		}

		{
			String ls_tmp;

			ls_tmp = getSubject();

			lmm_msg.setSubject(ls_tmp, "ISO-8859-1");

			if(lb_debug)
				clh_LOGGER.debug("sendMailEvent", "Subject :: " + ls_tmp);
		}

		{
			Collection lc_tmp;

			lc_tmp = getTo();

			if(CollectionUtils.isValidCollection(lc_tmp))
			{
				if(lb_debug)
					clh_LOGGER.debug("sendMailEvent", "To :: ");

				lmm_msg.setRecipients(Message.RecipientType.TO, toAddress(lc_tmp, lb_debug));
			}
		}

		{
			Multipart    lmmp_mimemultipart;
			MimeBodyPart lmbp_mimemultipartText;

			lmmp_mimemultipart         = new MimeMultipart();
			lmbp_mimemultipartText     = new MimeBodyPart();

			{
				String ls_tmp;

				ls_tmp = getBody();

				if(isHtmlCode())
					lmbp_mimemultipartText.setContent(ls_tmp, "text/html; charset=utf-8");
				else
					lmbp_mimemultipartText.setText(ls_tmp, "ISO-8859-1");

				if(lb_debug)
					clh_LOGGER.debug("sendMailEvent", "Body :: " + ls_tmp);
			}

			if(CollectionUtils.isValidCollection(imsba_file))
			{
				for(Map.Entry<String, byte[]> ls_entry : imsba_file.entrySet())
				{
					if(ls_entry != null)
					{
						MimeBodyPart        lmbp_mimemultipartFile;
						ByteArrayDataSource lfds_file;
						byte[]              lf_file;

						lf_file                    = ls_entry.getValue();
						lmbp_mimemultipartFile     = new MimeBodyPart();
						lfds_file                  = new ByteArrayDataSource(lf_file, TipoContenidoCommon.PDF);
						lfds_file.setName(ls_entry.getKey());

						if(lb_debug)
							clh_LOGGER.debug("sendMailEvent", "Attached File :: " + lf_file);

						lmbp_mimemultipartFile.setDataHandler(new DataHandler(lfds_file));
						lmbp_mimemultipartFile.setFileName(lfds_file.getName());
						lmmp_mimemultipart.addBodyPart(lmbp_mimemultipartFile);
					}
				}
			}

			lmmp_mimemultipart.addBodyPart(lmbp_mimemultipartText);
			lmm_msg.setContent(lmmp_mimemultipart);
		}

		lmm_msg.setSentDate(new Date());

		if(isStore())
			lmm_msg.saveChanges();

		javax.mail.Transport.send(lmm_msg);

		{
			StringBuilder lsb_sb;
			String[]      lsa_ids;

			lsb_sb      = new StringBuilder();
			lsa_ids     = lmm_msg.getHeader("Message-ID");

			if(lsa_ids != null)
			{
				for(int li_i = 0, li_size = lsa_ids.length; li_i < li_size; li_i++)
					lsb_sb.append(lsa_ids[li_i]);
			}

			if(lsb_sb != null)
				ls_return = lsb_sb.toString();
		}

		return ls_return;
	}

	/**
	 * Inits the.
	 *
	 * @param ac_c de ac c
	 * @return el valor de collection
	 */
	private Collection init(Collection ac_c)
	{
		return (ac_c != null) ? ac_c : new ArrayList();
	}

	/**
	 * To address.
	 *
	 * @param ac_addresses de ac addresses
	 * @param ab_debug de ab debug
	 * @return el valor de address[]
	 */
	private Address[] toAddress(Collection ac_addresses, boolean ab_debug)
	{
		Address[]                       laa_address;
		java.util.List<InternetAddress> lc_validAddresses;

		laa_address           = null;
		lc_validAddresses     = new ArrayList<InternetAddress>();

		if(CollectionUtils.isValidCollection(ac_addresses))
		{
			Iterator li_addresses;

			li_addresses = ac_addresses.iterator();

			while(li_addresses.hasNext())
			{
				String ls_address;

				ls_address = CollectionUtils.getString(li_addresses);

				if(StringUtils.isValidString(ls_address))
				{
					try
					{
						lc_validAddresses.add(new InternetAddress(ls_address));

						if(ab_debug)
							clh_LOGGER.debug("toAddress", "Addresses :: " + ls_address);
					}
					catch(Exception le_e)
					{
						clh_LOGGER.error("toAddress", le_e.getMessage());
					}
				}
			}
		}

		if(CollectionUtils.isValidCollection(lc_validAddresses))
		{
			laa_address = new Address[lc_validAddresses.size()];

			for(int li_i = 0; li_i < laa_address.length; li_i++)
				laa_address[li_i] = lc_validAddresses.get(li_i);
		}

		return laa_address;
	}
}
