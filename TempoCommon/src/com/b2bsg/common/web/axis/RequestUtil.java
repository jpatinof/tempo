package com.b2bsg.common.web.axis;

import com.b2bsg.common.util.StringUtils;
import com.cromasoft.cromaflow.constants.IdentificadoresCommon;

import org.apache.axis.MessageContext;

import javax.servlet.http.HttpServletRequest;


/**
 * Clase que contiene todos las utilidades asociadas al request
 *
 * @author Edgar Prieto
 */
public class RequestUtil
{
	/**
	 * Retorna el valor de request info.
	 *
	 * @param amc_ctx correspondiente al valor del tipo de objeto MessageContext
	 * @return el valor de request info
	 */
	public static RequestInfo getRequestInfo(MessageContext amc_ctx)
	{
		RequestInfo lri_info;

		lri_info = new RequestInfo();

		if(amc_ctx != null)
		{
			Object lo_request;
			String ls_localIp;
			String ls_remoteIp;

			lo_request      = amc_ctx.getProperty(org.apache.axis.transport.http.HTTPConstants.MC_HTTP_SERVLETREQUEST);
			ls_localIp      = null;
			ls_remoteIp     = null;

			if((lo_request != null) && lo_request instanceof HttpServletRequest)
			{
				HttpServletRequest lhsr_request;
				String             ls_clientIp;

				lhsr_request     = (HttpServletRequest)lo_request;
				ls_localIp       = lhsr_request.getLocalAddr();
				ls_remoteIp      = lhsr_request.getRemoteAddr();
				ls_clientIp      = lhsr_request.getHeader("x-forwarded-for");

				if(ls_clientIp != null)
				{
					String[] lsa_forwarded;

					lsa_forwarded = StringUtils.getStringArray(ls_clientIp.trim(), IdentificadoresCommon.ESPACIO_VACIO);

					if((lsa_forwarded.length > 0) && (lsa_forwarded[0].trim().length() > 0))
						ls_remoteIp = lsa_forwarded[0];
				}
			}

			lri_info = new RequestInfo(ls_localIp, ls_remoteIp);
		}
		else
			lri_info = new RequestInfo();

		return lri_info;
	}
}
