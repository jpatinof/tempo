package com.cromasoft.cromaflow.web.bean.handlers;

import com.sun.faces.application.view.MultiViewHandler;

import java.util.Locale;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;


/**
 * Clase para el manejo del MultiViewHandler en la aplicacion.
 * @author dbeltran
 *
 */
public class CromaFlowViewHandler extends MultiViewHandler
{
	/**
	 * Método de calculo del Locala para la traducción de la aplicación
	 */
	public Locale calculateLocale(FacesContext afc_facesContext)
	{
		HttpSession lhttps_HttpSession = (HttpSession)afc_facesContext.getExternalContext().getSession(false);

		if(lhttps_HttpSession != null)
		{
			Locale ll_locale = (Locale)lhttps_HttpSession.getAttribute("locale");

			if(ll_locale != null)
				return ll_locale;
		}

		return super.calculateLocale(afc_facesContext);
	}
}
