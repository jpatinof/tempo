/*
 * Creado el 24/11/2005
 */
package com.b2bsg.common.mail;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase para Obtener un listado de Folder de un usuario
 *
 * @author Fredy Posada
 * @version 1.0
 */
public class ListingFolders
{
	private Folder		   lf_defaultFolder;
	private InitialContext li_initctx;
	private Session		   ls_session;
	private Store		   ls_store;
	private Folder[]	   lf_folders;

	/**
	 * @param as_jndi
	 * @throws NamingException
	 * @throws MessagingException
	 */
	public ListingFolders(String as_jndi)
	throws NamingException, MessagingException
	{
		li_initctx     = new InitialContext();
		ls_session     = (Session)li_initctx.lookup(as_jndi);
		connectStore();
	}

	/**
	 * @param as_session
	 * @throws MessagingException
	 */
	public ListingFolders(Session as_session)
	throws MessagingException
	{
		ls_session = as_session;
		connectStore();
	}

	/**
	 * Método para llamar los directorios de correo de un usuario. No incluye recursividad
	 *
	 * @return <code>javax.mail.Folder[]</code>
	 */
	public Folder[] getFolder()
	{
		return lf_folders;
	}

	private void connectStore()
	throws MessagingException
	{
		ls_store = ls_session.getStore();
		ls_store.connect();
		lf_defaultFolder     = ls_store.getDefaultFolder();
		lf_folders			 = lf_defaultFolder.list();
		ls_store.close();
	}
}
