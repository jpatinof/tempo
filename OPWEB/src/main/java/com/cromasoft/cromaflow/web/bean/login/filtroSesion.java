package com.cromasoft.cromaflow.web.bean.login;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.StringUtils;

import com.cromasoft.cromaflow.constants.IdentificadoresCommon;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Clase que contiene todos los filtros de inicio de sesion
 *
 * @author Julian Vaca
 */
@WebFilter("/pages/*")
public class filtroSesion implements Filter
{
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(filtroSesion.class);

	/** Propiedad filter config. */
	FilterConfig filterConfig;

	/** {@inheritdoc} */
	@Override
	public void destroy()
	{
		this.filterConfig                         = null;
	}

	/** {@inheritdoc} */
	@Override
	public void doFilter(ServletRequest asr_request, ServletResponse asr_response, FilterChain afc_chain)
	    throws ServletException, java.io.IOException
	{
		HttpServletRequest  req    = (HttpServletRequest)asr_request;
		HttpServletResponse res    = (HttpServletResponse)asr_response;
		HttpSession         sesion = req.getSession(true);

		String usuario = (String)sesion.getAttribute(IdentificadoresCommon.SESION_USUARIO);
		String timeout = (String)sesion.getAttribute(IdentificadoresCommon.SESION_TIME_OUT);

		if(StringUtils.isValidString(usuario))
			afc_chain.doFilter(asr_request, asr_response);
		else if(StringUtils.isValidString(timeout) && timeout.equals("true"))
		{
			res.sendRedirect(req.getContextPath() + "/loginExpired.jsf");
			sesion.invalidate();
		}
		else
			res.sendRedirect(req.getContextPath() + "/login.jsf");
	}

	/** {@inheritdoc} */
	@Override
	public void init(FilterConfig filterConfig)
	    throws ServletException
	{
		this.filterConfig = filterConfig;
	}
}
