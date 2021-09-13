package com.cromasoft.cromaflow.web.util;

import com.b2bsg.common.exception.B2BException;
import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.BooleanUtils;
import com.b2bsg.common.util.NumericUtils;
import com.b2bsg.common.util.StringUtils;
import com.cromasoft.cromaflow.common.constants.ErrorKeys;
import com.cromasoft.cromaflow.constants.ExtensionCommon;
import com.cromasoft.cromaflow.constants.IdentificadoresCommon;
import com.cromasoft.cromaflow.constants.TipoContenidoCommon;

import java.io.IOException;
import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Clase que contiene todos las funcionalidades para las utilidades manejadas por el framework
 *
 * @author garias
 */
public class FacesUtils implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = -1517228079052415575L;

	/** Propiedad ipb escribir mensajes. */
	private static boolean ipb_escribirMensajes;

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(FacesUtils.class);

	/**
	 * Retorna el valor de boolean faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de boolean faces parameter
	 */
	public static Boolean getBooleanFacesParameter(String as_parameterName)
	{
		return BooleanUtils.getBoolean(getStringFacesParameter(as_parameterName));
	}

	/**
	 * Retorna el valor de boolean value faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de boolean value faces parameter
	 */
	public static boolean getBooleanValueFacesParameter(String as_parameterName)
	{
		return BooleanUtils.getBooleanValue(getBooleanFacesParameter(as_parameterName));
	}

	/**
	 * Modifica el valor de escribir mensajes.
	 *
	 * @param apb_escribirMensajes asigna el valor a la propiedad escribir mensajes
	 */
	public static void setEscribirMensajes(boolean apb_escribirMensajes)
	{
		ipb_escribirMensajes = apb_escribirMensajes;
	}

	/**
	 * Retorna el valor de escribir mensajes.
	 *
	 * @return el valor de escribir mensajes
	 */
	public static boolean getEscribirMensajes()
	{
		return ipb_escribirMensajes;
	}

	/**
	 * Retorna el valor de external context.
	 *
	 * @return el valor de external context
	 */
	public static ExternalContext getExternalContext()
	{
		FacesContext lfc_context;

		lfc_context = getFacesContext();

		return (lfc_context == null) ? null : lfc_context.getExternalContext();
	}

	/**
	 * Retorna el valor de faces context.
	 *
	 * @return el valor de faces context
	 */
	public static FacesContext getFacesContext()
	{
		return FacesContext.getCurrentInstance();
	}
	/**
	 * Método para obtener la instancia de un bean.
	 * @param acu_bean <code>Class ?</code> del bean que se quiere obtener.
	 * @param as_beanNameCommon <code>String</code> que contiene el nombre del bean.
	 * @return <code>Object</code> que contiene la instancia del bean obtenido.
	 * @throws B2BException
	 */
	public static Object obtenerInstanciaBean(Class<?> acu_bean, String as_beanNameCommon)
	    throws B2BException
	{
		Object lb_bean;

		lb_bean = null;

		if((acu_bean != null) && StringUtils.isValidString(as_beanNameCommon))
		{
			FacesContext lfc_context;

			lfc_context     = FacesUtils.getFacesContext();
			lb_bean         = (Object)lfc_context.getApplication()
					                                 .evaluateExpressionGet(lfc_context, as_beanNameCommon, acu_bean);

			if((lb_bean == null) || !acu_bean.isInstance(lb_bean))
				throw new B2BException(ErrorKeys.ERROR_INTERNO_SISTEMA);
		}

		return lb_bean;
	}

	/**
	 * Retorna el valor de faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de faces parameter
	 */
	public static Object getFacesParameter(String as_parameterName)
	{
		ExternalContext lec_context;
		Object          lo_parameter;

		lec_context     = getExternalContext();

		lo_parameter = (lec_context != null)
			? (StringUtils.isValidString(as_parameterName) ? lec_context.getRequestParameterMap().get(as_parameterName)
			                                               : null) : null;

		return lo_parameter;
	}

	/**
	 * Retorna el valor de int faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de int faces parameter
	 */
	public static int getIntFacesParameter(String as_parameterName)
	{
		return NumericUtils.getInt(getIntegerFacesParameter(as_parameterName));
	}

	/**
	 * Retorna el valor de integer faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de integer faces parameter
	 */
	public static Integer getIntegerFacesParameter(String as_parameterName)
	{
		return NumericUtils.getInteger(getStringFacesParameter(as_parameterName), -1);
	}

	/**
	 * Return the locale of the request.
	 *
	 * @return the locale
	 */
	public static Locale getLocale()
	{
		ExternalContext lec_context;
		Locale          ll_locale;

		lec_context     = getExternalContext();
		ll_locale       = (lec_context != null) ? lec_context.getRequestLocale() : null;

		if(ll_locale == null)
			ll_locale = new Locale("en", "us");

		return ll_locale;
	}

	/**
	 * Retorna el valor de long faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de long faces parameter
	 */
	public static long getLongFacesParameter(String as_parameterName)
	{
		return NumericUtils.getLong(getLongWrapperFacesParameter(as_parameterName));
	}

	/**
	 * Retorna el valor de long wrapper faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de long wrapper faces parameter
	 */
	public static Long getLongWrapperFacesParameter(String as_parameterName)
	{
		return NumericUtils.getLongWrapper(getStringFacesParameter(as_parameterName), -1L);
	}

	/**
	 * Retorna el valor de parameter.
	 *
	 * @param as_parameter correspondiente al valor del tipo de objeto String
	 * @return el valor de parameter
	 */
	public static String getParameter(String as_parameter)
	{
		HttpServletRequest lhsr_request;

		lhsr_request = getRequest();

		return (lhsr_request != null) ? lhsr_request.getParameter(as_parameter) : null;
	}

	/**
	 * Retorna el valor de request.
	 *
	 * @return el valor de request
	 */
	public static HttpServletRequest getRequest()
	{
		Object lo_request;

		{
			ExternalContext lec_context;

			lec_context     = getExternalContext();
			lo_request      = (lec_context == null) ? null : lec_context.getRequest();
		}

		return ((lo_request != null) && lo_request instanceof HttpServletRequest) ? (HttpServletRequest)lo_request : null;
	}

	/**
	 * Retorna el valor de response.
	 *
	 * @return el valor de response
	 */
	public static HttpServletResponse getResponse()
	{
		Object lo_response;

		{
			ExternalContext lec_context;

			lec_context     = getExternalContext();
			lo_response     = (lec_context == null) ? null : lec_context.getResponse();
		}

		return ((lo_response != null) && lo_response instanceof HttpServletResponse) ? (HttpServletResponse)lo_response
		                                                                             : null;
	}

	/**
	 * Retorna el valor de session.
	 *
	 * @return el valor de session
	 */
	public static HttpSession getSession()
	{
		HttpServletRequest lhsr_request;

		lhsr_request = getRequest();

		return (lhsr_request != null) ? lhsr_request.getSession() : null;
	}

	/**
	 * Retorna Objeto o variable de valor session.
	 *
	 * @param ab_crearSesion de ab crear sesion
	 * @return el valor de session
	 */
	public static HttpSession getSession(boolean ab_crearSesion)
	{
		HttpServletRequest lhsr_request;
		
		lhsr_request = getRequest();
		
		return (lhsr_request != null) ? lhsr_request.getSession(ab_crearSesion) : null;
	}

	/**
	 * Retorna el valor de session attribute.
	 *
	 * @param as_name correspondiente al valor del tipo de objeto String
	 * @return el valor de session attribute
	 */
	public static Object getSessionAttribute(String as_name)
	{
		HttpSession lhs_session;

		lhs_session = getSession();

		return (lhs_session != null) ? lhs_session.getAttribute(as_name) : null;
	}

	/**
	 * Retorna el valor de session attribute as string.
	 *
	 * @param as_name correspondiente al valor del tipo de objeto String
	 * @return el valor de session attribute as string
	 */
	public static String getSessionAttributeAsString(String as_name)
	{
		Object lo_attribute;

		lo_attribute = getSessionAttribute(as_name);

		return (lo_attribute != null) ? lo_attribute.toString() : null;
	}

	/**
	 * Retorna el valor de session map.
	 *
	 * @return el valor de session map
	 */
	public static Map<String, Object> getSessionMap()
	{
		ExternalContext lec_context;

		lec_context = getExternalContext();

		return (lec_context == null) ? null : lec_context.getSessionMap();
	}

	/**
	 * Retorna el valor de string faces parameter.
	 *
	 * @param as_parameterName correspondiente al valor del tipo de objeto String
	 * @return el valor de string faces parameter
	 */
	public static String getStringFacesParameter(String as_parameterName)
	{
		Object lo_parameter;

		lo_parameter = getFacesParameter(as_parameterName);

		return (lo_parameter == null) ? null : lo_parameter.toString();
	}

	/**
	 * Retorna el valor de user.
	 *
	 * @return el valor de user
	 */
	public static String getUser()
	{
		ExternalContext lec_context;

		lec_context = getExternalContext();

		return (lec_context == null) ? null : lec_context.getRemoteUser();
	}

	/**
	 * Valida la propiedad user in role.
	 *
	 * @param as_roleName correspondiente al valor del tipo de objeto String
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en user in role
	 */
	public static boolean isUserInRole(String as_roleName)
	{
		ExternalContext lec_context;

		lec_context = getExternalContext();

		return (lec_context != null) ? lec_context.isUserInRole(as_roleName) : false;
	}

	/**
	 * Download excel file 2003.
	 *
	 * @param as_fileName correspondiente al valor del tipo de objeto String
	 * @param aba_file correspondiente al valor del tipo de objeto byte[]
	 */
	public static void downloadExcelFile2003(String as_fileName, byte[] aba_file)
	{
		downloadFile(as_fileName, aba_file, TipoContenidoCommon.EXCEL_FILE_2003);
	}

	/**
	 * Download excel file 2007.
	 *
	 * @param as_fileName correspondiente al valor del tipo de objeto String
	 * @param aba_file correspondiente al valor del tipo de objeto byte[]
	 */
	public static void downloadExcelFile2007(String as_fileName, byte[] aba_file)
	{
		downloadFile(as_fileName, aba_file, TipoContenidoCommon.EXCEL_FILE_2007);
	}

	/**
	 * Download file.
	 *
	 * @param as_fileExtension correspondiente al valor del tipo de objeto String
	 * @param aba_file correspondiente al valor del tipo de objeto byte[]
	 */
	public static void downloadFile(String as_fileExtension, byte[] aba_file)
	{
		String       ls_contentType;
		StringBuilder lsb_filename;

		ls_contentType     = null;
		lsb_filename       = new StringBuilder();

		lsb_filename.append("archivo");

		if(StringUtils.isValidString(as_fileExtension))
		{
			String ls_fileExtension;

			ls_fileExtension = as_fileExtension.toLowerCase();

			if(ls_fileExtension.equalsIgnoreCase(ExtensionCommon.BMP))
				ls_contentType = TipoContenidoCommon.BMP;
			else if(
			    ls_fileExtension.equalsIgnoreCase(ExtensionCommon.DOC)
				    || ls_fileExtension.equalsIgnoreCase(ExtensionCommon.XDOC)
			)
				ls_contentType = TipoContenidoCommon.DOC_XDOC;
			else if(ls_fileExtension.equalsIgnoreCase(ExtensionCommon.GIF))
				ls_contentType = TipoContenidoCommon.GIF;
			else if(
			    ls_fileExtension.equalsIgnoreCase(ExtensionCommon.HTM)
				    || ls_fileExtension.equalsIgnoreCase(ExtensionCommon.HTML)
			)
				ls_contentType = TipoContenidoCommon.HTM_HTML;
			else if(
			    ls_fileExtension.equalsIgnoreCase(ExtensionCommon.JPG)
				    || ls_fileExtension.equalsIgnoreCase(ExtensionCommon.JPEG)
			)
				ls_contentType = TipoContenidoCommon.JPG_JPEG;
			else if(ls_fileExtension.equalsIgnoreCase(ExtensionCommon.PDF))
				ls_contentType = TipoContenidoCommon.PDF;
			else if(ls_fileExtension.equalsIgnoreCase(ExtensionCommon.PNG))
				ls_contentType = TipoContenidoCommon.PNG;
			else if(
			    ls_fileExtension.equalsIgnoreCase(ExtensionCommon.XLS)
				    || ls_fileExtension.equalsIgnoreCase(ExtensionCommon.XXLS)
			)
				ls_contentType = TipoContenidoCommon.XLS_XLSX_XXLS;
			else if(
			    ls_fileExtension.equalsIgnoreCase(ExtensionCommon.TIF)
				    || (ls_fileExtension.equalsIgnoreCase(ExtensionCommon.TIFF))
			)
				ls_contentType = TipoContenidoCommon.TIF_TIFF;
			else if(
			    ls_fileExtension.equalsIgnoreCase(ExtensionCommon.TXT)
				    || (ls_fileExtension.equalsIgnoreCase(ExtensionCommon.TIFF))
			)
				ls_contentType = TipoContenidoCommon.TXT;
			else
			{
				StringBuilder lsb_contentType;

				lsb_contentType = new StringBuilder();

				lsb_contentType.append("application/");
				lsb_contentType.append(ls_fileExtension);

				ls_contentType = lsb_contentType.toString();
			}

			lsb_filename.append(IdentificadoresCommon.PUNTO);
			lsb_filename.append(ls_fileExtension);
		}

		downloadFile(lsb_filename.toString(), aba_file, ls_contentType);
	}

	/**
	 * Download file.
	 *
	 * @param as_fileName correspondiente al valor del tipo de objeto String
	 * @param aba_file correspondiente al valor del tipo de objeto byte[]
	 * @param as_contentType correspondiente al valor del tipo de objeto String
	 */
	public static void downloadFile(String as_fileName, byte[] aba_file, String as_contentType)
	{
		if(StringUtils.isValidString(as_fileName) && (aba_file != null))
		{
			try
			{
				HttpServletResponse lhsr_response;

				lhsr_response = getResponse();

				if(lhsr_response != null)
				{
					ServletOutputStream lsos_output;

					lsos_output = lhsr_response.getOutputStream();

					lhsr_response.setContentLength(aba_file.length);

					if(StringUtils.isValidString(as_contentType))
						lhsr_response.setContentType(as_contentType);

					lhsr_response.setDateHeader("Expires", 0);
					lhsr_response.setHeader("cache-control", "private, must-revalidate");
					lhsr_response.setHeader("Content-Disposition", "inline; filename=" + as_fileName);
					lhsr_response.setHeader("Content-Transfer-Encoding", "Binary");
					lhsr_response.setHeader("Pragma", "no-cache");
					lhsr_response.setHeader("Pragma", "private");

					lsos_output.write(aba_file);
					lsos_output.flush();
					lsos_output.close();

					lhsr_response.flushBuffer();

					FacesContext.getCurrentInstance().responseComplete();
				}
			}
			catch(IOException lioe_e)
			{
				clh_LOGGER.error("downloadFile", lioe_e);
			}
		}
	}

	/**
	 * Download PDF file.
	 *
	 * @param as_fileName correspondiente al valor del tipo de objeto String
	 * @param aba_file correspondiente al valor del tipo de objeto byte[]
	 */
	public static void downloadPDFFile(String as_fileName, byte[] aba_file)
	{
		downloadFile(as_fileName, aba_file, TipoContenidoCommon.PDF);
	}

	/**
	 * Download ZIP file.
	 *
	 * @param as_fileName correspondiente al valor del tipo de objeto String
	 * @param aba_file correspondiente al valor del tipo de objeto byte[]
	 */
	public static void downloadZIPFile(String as_fileName, byte[] aba_file)
	{
		downloadFile(as_fileName, aba_file, TipoContenidoCommon.ZIP);
	}
}
