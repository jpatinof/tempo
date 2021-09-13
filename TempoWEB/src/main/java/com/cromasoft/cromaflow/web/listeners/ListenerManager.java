package com.cromasoft.cromaflow.web.listeners;

import java.util.Locale;

import javax.servlet.ServletContextEvent;

import com.b2bsg.common.logging.LoggerHandler;


/**
 * Clase que contiene todos las funcionalidades de ListenerManager.
 *
 * @author Gabriel Arias
 */
public class ListenerManager implements javax.servlet.ServletContextListener
{
	/** Propiedad cs SCHEDULER CONTAINER. */
	private static String cs_SCHEDULER_CONTAINER = "SCHEDULER_CONTAINER";

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(ListenerManager.class);

	/**
	 * Instancia un nuevo objeto listener manager.
	 */
	public ListenerManager()
	{
		super();
	}

	/** {@inheritdoc} */
	public void contextInitialized(ServletContextEvent asce_event)
	{
		System.setProperty("java.awt.headless", "true");

//		try
//		{
//			Constantes lc_constante;
//
//			lc_constante = new ReferenceDelegate().getRemote()
//					                                  .findConstantesById(ConstanteCommon.CONFIGURACION_IDIOMA_PAIS);
//
//			if(lc_constante != null)
//			{
//				String ls_caracter;
//				ls_caracter = lc_constante.getCaracter();
//
//				if(com.b2bsg.common.util.StringUtils.isValidString(ls_caracter))
//				{
//					String   ls_idioma;
//					String   ls_pais;
//					String[] ls_temp;
//
//					ls_temp       = ls_caracter.split("-");
//					ls_idioma     = ls_temp[0];
//					ls_pais       = ls_temp[1];
//
//					Locale.setDefault(new Locale(ls_idioma, ls_pais));
//				}
//			}
//			else
				Locale.setDefault(new Locale("es", "CO"));
//		}
//		catch(B2BException b2b2e_e)
//		{
//			clh_LOGGER.error("contextInitialized", b2b2e_e);
//		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
	}
}
