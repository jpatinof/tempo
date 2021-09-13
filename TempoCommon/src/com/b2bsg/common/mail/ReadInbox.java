/*
 * Creado el 24/11/2005
 */
package com.b2bsg.common.mail;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase para leer la carpeta INBOX de un correo
 *
 * @author Fredy Posada
 * @version 1.0
 */
public class ReadInbox
{
	private Folder		   lf_inbox;
	private InitialContext li_initctx;
	private Session		   ls_session;
	private Store		   ls_store;
	private int[]		   li_messagesid;
	private int			   li_messagenew    = 0;
	private int			   li_messageunread = 0;
	private int			   li_totalmessages = 0;

	/**
	 * @param as_jndi
	 *        nombre del recurso JNDI (Recurso de Correo de WebSphere) a invocar.
	 * @throws NamingException
	 * @throws MessagingException
	 */
	public ReadInbox(String as_jndi)
	throws NamingException, MessagingException
	{
		li_initctx     = new InitialContext();
		ls_session     = (Session)li_initctx.lookup(as_jndi);
		inbox();
	}

	/**
	 * @param as_session
	 *        sesión de correo
	 * @throws MessagingException
	 */
	public ReadInbox(Session as_session)
	throws MessagingException
	{
		ls_session = as_session;
		inbox();
	}

	/**
	 * @param ai_messageid
	 *        código de mensaje
	 * @return BasicMessage Mensaje
	 * @throws MessagingException
	 */
	public BasicMessage getMessage(int ai_messageid)
	throws MessagingException
	{
		if(lf_inbox.isOpen())
			lf_inbox.close(true);

		lf_inbox.open(Folder.READ_WRITE);

		Message		 lm_message		  = lf_inbox.getMessage(ai_messageid);
		BasicMessage lbm_basicmessage = null;

		try
		{
			lbm_basicmessage = toBasicMessage(lm_message);
		}
		catch(Exception e)
		{
		}

		lf_inbox.close(true);

		return lbm_basicmessage;
	}

	/**
	 * @return <code>BasicMessage[]</code> arreglo de mensajes
	 * @throws MessagingException
	 */
	public BasicMessage[] getMessages()
	throws MessagingException
	{
		BasicMessage[] lbm_basicmessages = null;

		if(lf_inbox.isOpen())
			lf_inbox.close(true);

		lf_inbox.open(Folder.READ_WRITE);

		Message[] lm_messages = lf_inbox.getMessages();

		lbm_basicmessages = new BasicMessage[lm_messages.length];

		for(int i = 0; i < lm_messages.length; i++)
		{
			try
			{
				lbm_basicmessages[i] = toBasicMessage(lm_messages[i]);
			}
			catch(Exception e)
			{
			}
		}

		lf_inbox.close(true);

		return lbm_basicmessages;
	}

	/**
	 * @return int[] Código de Mensajes
	 * @throws MessagingException
	 */
	public int[] getMessagesID()
	throws MessagingException
	{
		if(lf_inbox.isOpen())
			lf_inbox.close(true);

		lf_inbox.open(Folder.READ_WRITE);
		li_messageunread     = lf_inbox.getUnreadMessageCount();
		li_messagenew		 = lf_inbox.getNewMessageCount();
		li_totalmessages     = lf_inbox.getMessageCount();

		Message[] lm_messages = lf_inbox.getMessages();

		li_messagesid = new int[lm_messages.length];

		for(int i = 0; i < lm_messages.length; i++)
			li_messagesid[i] = lm_messages[i].getMessageNumber();

		lf_inbox.close(true);

		return li_messagesid;
	}

	/**
	 * @return Mensajes nuevos
	 * @throws MessagingException
	 */
	public int getNewMessages()
	throws MessagingException
	{
		if(lf_inbox.isOpen())
			lf_inbox.close(true);

		lf_inbox.open(Folder.READ_WRITE);
		li_messagenew = lf_inbox.getNewMessageCount();
		lf_inbox.close(true);

		return li_messagenew;
	}

	/**
	 * @return Total de mensajes de la bandeja de entrada
	 * @throws MessagingException
	 */
	public int getTotalMessages()
	throws MessagingException
	{
		if(lf_inbox.isOpen())
			lf_inbox.close(true);

		lf_inbox.open(Folder.READ_WRITE);

		li_totalmessages = lf_inbox.getMessageCount();
		lf_inbox.close(true);

		return li_totalmessages;
	}

	/**
	 * @return Mensajes nuevos
	 * @throws MessagingException
	 */
	public int getUnreadMessages()
	throws MessagingException
	{
		lf_inbox.open(Folder.READ_WRITE);
		li_messageunread = lf_inbox.getUnreadMessageCount();
		lf_inbox.close(true);

		return li_messageunread;
	}

	private void inbox()
	throws MessagingException
	{
		ls_store = ls_session.getStore();
		ls_store.connect();
		lf_inbox = ls_store.getFolder("INBOX");
	}

	private BasicMessage toBasicMessage(Message am_message)
	throws MessagingException
	{
		String ls_to = "";
		String ls_cc = "";

		for(int i = 0; i < am_message.getRecipients(Message.RecipientType.TO).length; i++)
			ls_to += (am_message.getRecipients(Message.RecipientType.TO)[i].toString() + ", ");

		for(int i = 0; i < am_message.getRecipients(Message.RecipientType.CC).length; i++)
			ls_cc += (am_message.getRecipients(Message.RecipientType.CC)[i].toString() + ", ");

		return new BasicMessage(
			am_message.getFrom()[0].toString(),
			ls_to,
			ls_cc,
			am_message.getSubject(),
			null,
			am_message.getSentDate(),
			am_message.getReceivedDate(),
			am_message.getSize()
		);
	}
}
