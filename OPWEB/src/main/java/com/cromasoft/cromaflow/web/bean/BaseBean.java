package com.cromasoft.cromaflow.web.bean;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.messages.Messages;

import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.NumericUtils;
import com.b2bsg.common.util.StringUtils;

import com.cromasoft.cromaflow.common.constants.ErrorKeys;

import com.cromasoft.cromaflow.constants.IdentificadoresCommon;
import com.cromasoft.cromaflow.constants.TipoArchivoCommon;
import com.cromasoft.cromaflow.constants.TipoContenidoCommon;

import com.cromasoft.cromaflow.web.util.FacesUtils;

import org.primefaces.PrimeFaces;

import org.primefaces.PrimeFaces.Ajax;

import org.primefaces.component.calendar.Calendar;

import org.primefaces.component.inputnumber.InputNumber;

import org.primefaces.component.inputtext.InputText;

import org.primefaces.component.inputtextarea.InputTextarea;

import org.primefaces.component.outputlabel.OutputLabel;

import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import org.primefaces.component.selectoneradio.SelectOneRadio;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;


/**
 * Clase que contiene todos las propiedades y acciones de BaseBean.
 *
 * @author garias
 */
public abstract class BaseBean implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 6786415929687070202L;

	/** Constante CONFIG_MESSAGES. */
	public static final String cs_CONFIG_MESSAGES = "com.cromasoft.cromaflow.web.bean.resources.Messages";

	/** Constante CONFIG_ERRORS. */
	public static final String cs_CONFIG_ERRORS = "com.cromasoft.cromaflow.web.bean.resources.Errors";

	/** Constante CONFIG_ERRORS_EN. */
	public static final String CONFIG_ERRORS_EN = "com.cromasoft.cromaflow.web.bean.resources.Errors_en";

	/** Constante CONFIG_MESSAGES_EN. */
	public static final String CONFIG_MESSAGES_EN = "com.cromasoft.cromaflow.web.bean.resources.Messages_en";

	/** Constante CONFIG_MESSAGES_OPTIONS. */
	public static final String CONFIG_MESSAGES_OPTIONS = "com.cromasoft.cromaflow.web.bean.resources.Options";

	/** Constante CONFIG_MESSAGES_OPTIONS_EN. */
	public static final String CONFIG_MESSAGES_OPTIONS_EN = "com.cromasoft.cromaflow.web.bean.resources.Options_en";

	/** Propiedad im messages. */
	private static Messages im_messages;

	/** Propiedad im messages. */
	private static Messages im_messagesOpciones;

	/** Propiedad idioma español. */
	private boolean ib_idiomaEspanol;

	/**
	 * Instancia un nuevo objeto base bean.
	 */
	public BaseBean()
	{
		B2BException.setBundle(cs_CONFIG_ERRORS);
	}

	/**
	 * Retorna el valor de messages.
	 *
	 * @return el valor de messages
	 */
	public static Messages getMessagesOpciones()
	{
		if(im_messagesOpciones == null)
			im_messagesOpciones = new Messages(CONFIG_MESSAGES_OPTIONS);

		return im_messagesOpciones;
	}

	/**
	 * Validate styles.
	 *
	 * @param as_idComponente
	 *            correspondiente al valor del tipo de objeto String
	 * @param afc_pantallaActual
	 *            correspondiente al valor del tipo de objeto FacesContext
	 * @param as_datoParaValidar
	 *            correspondiente al valor del tipo de objeto String
	 * @param ab_focus
	 *            correspondiente al valor del tipo de objeto boolean
	 * @return verdadero, si se cumple la condicion, de lo contario retorna falso
	 */
	public static boolean validateStyles(
	    String as_idComponente, FacesContext afc_pantallaActual, String as_datoParaValidar, boolean ab_focus
	)
	{
		UIComponent luic_uic;
		boolean     lb_return;

		lb_return     = false;
		luic_uic      = afc_pantallaActual.getViewRoot().findComponent(as_idComponente);

		if(luic_uic != null)
		{
			String ls_style;

			if(luic_uic instanceof InputText)
			{
				InputText lit_it;

				lit_it = (InputText)luic_uic;

				if(lit_it != null)
				{
					ls_style     = lit_it.getStyle();

					ls_style      = changeStyleComponent(
						    as_idComponente, as_datoParaValidar, ls_style, ab_focus, false
						);
					lb_return     = ls_style.contains("border-color:null");

					lit_it.setStyle(ls_style);
				}
			}
			else if(luic_uic instanceof InputNumber)
			{
				InputNumber lin_in;

				lin_in = (InputNumber)luic_uic;

				if(lin_in != null)
				{
					ls_style     = lin_in.getInputStyle();

					ls_style      = changeStyleComponent(
						    as_idComponente, as_datoParaValidar, ls_style, ab_focus, false
						);
					lb_return     = ls_style.contains("border-color:null");

					lin_in.setInputStyle(ls_style);
				}
			}
			else if(luic_uic instanceof InputTextarea)
			{
				InputTextarea lin_in;

				lin_in = (InputTextarea)luic_uic;

				if(lin_in != null)
				{
					ls_style     = lin_in.getStyle();

					ls_style      = changeStyleComponent(
						    as_idComponente, as_datoParaValidar, ls_style, ab_focus, false
						);
					lb_return     = ls_style.contains("border-color:null");

					lin_in.setStyle(ls_style);
				}
			}
			else if(luic_uic instanceof Calendar)
			{
				Calendar lin_in;

				lin_in = (Calendar)luic_uic;

				if(lin_in != null)
				{
					ls_style     = lin_in.getInputStyle();

					ls_style      = changeStyleComponent(
						    as_idComponente, as_datoParaValidar, ls_style, ab_focus, false
						);
					lb_return     = ls_style.contains("border-color:null");

					lin_in.setInputStyle(ls_style);
				}
			}
			else if(luic_uic instanceof OutputLabel)
			{
				OutputLabel lin_in;

				lin_in = (OutputLabel)luic_uic;

				if(lin_in != null)
				{
					ls_style     = lin_in.getStyle();

					ls_style      = changeStyleComponent(as_idComponente, as_datoParaValidar, ls_style, ab_focus, true);
					lb_return     = ls_style.contains("color:black;");

					lin_in.setStyle(ls_style);
				}
			}
			else if(luic_uic instanceof SelectBooleanCheckbox)
			{
				SelectBooleanCheckbox lin_in;

				lin_in = (SelectBooleanCheckbox)luic_uic;

				if(lin_in != null)
				{
					ls_style     = lin_in.getStyle();

					ls_style      = changeStyleComponent(
						    as_idComponente, as_datoParaValidar, ls_style, ab_focus, false
						);
					lb_return     = ls_style.contains("border-color:null");

					lin_in.setStyle(ls_style);
				}
			}
			else if(luic_uic instanceof SelectOneMenu)
			{
				SelectOneMenu lin_in;

				lin_in = (SelectOneMenu)luic_uic;

				if(lin_in != null)
				{
					ls_style     = lin_in.getStyle();

					ls_style      = changeStyleComponent(
						    as_idComponente, as_datoParaValidar, ls_style, ab_focus, false
						);
					lb_return     = ls_style.contains("border-color:null");

					lin_in.setStyle(ls_style);
				}
			}
			else if(luic_uic instanceof SelectOneRadio)
			{
				SelectOneRadio lsor_sor;

				lsor_sor = (SelectOneRadio)luic_uic;

				if(lsor_sor != null)
				{
					ls_style     = lsor_sor.getStyle();

					ls_style      = changeStyleComponent(as_idComponente, as_datoParaValidar, ls_style, ab_focus, true);
					lb_return     = ls_style.contains("color:black;");

					lsor_sor.setStyle(ls_style);
				}
			}
		}

		return lb_return;
	}

	/**
	 * Validate styles.
	 *
	 * @param as_idComponente
	 *            correspondiente al valor del tipo de objeto String
	 * @param afc_pantallaActual
	 *            correspondiente al valor del tipo de objeto FacesContext
	 * @param ad_fecha
	 *            correspondiente al valor del tipo de objeto Date
	 * @param ab_focus
	 *            correspondiente al valor del tipo de objeto boolean
	 * @return verdadero, si se cumple la condicion, de lo contario retorna falso
	 */
	public static boolean validateStyles(
	    String as_idComponente, FacesContext afc_pantallaActual, Date ad_fecha, boolean ab_focus
	)
	{
		return validateStyles(
		    as_idComponente, afc_pantallaActual,
		    (ad_fecha == null) ? IdentificadoresCommon.DATO_INVALIDO : IdentificadoresCommon.DATO_VALIDO, ab_focus
		);
	}

	/**
	 * Modifica el valor de IdiomaEspanol.
	 *
	 * @param ib_idiomaEspanol de ib idioma espanol
	 */
	public void setIdiomaEspanol(boolean ib_idiomaEspanol)
	{
		this.ib_idiomaEspanol = ib_idiomaEspanol;
	}

	/**
	 * Valida la propiedad idioma espanol.
	 *
	 * @return Retorna el valor de la propiedad ib_idiomaEspañol
	 */
	public boolean isIdiomaEspanol()
	{
		return ib_idiomaEspanol;
	}

	//TODO desquemar
	/**
	 * Retorna Objeto o variable de valor image type to show.
	 *
	 * @param as_fileType de as file type
	 * @return el valor de image type to show
	 */
	public String getImageTypeToShow(String as_fileType)
	{
		String ls_fileTypePath;

		ls_fileTypePath = IdentificadoresCommon.DATO_INVALIDO;

		if(StringUtils.isValidString(as_fileType))
		{
			switch(as_fileType.toUpperCase())
			{
				case "RTF":
					ls_fileTypePath = "../images/word.png";

					break;

				case "PDF":
					ls_fileTypePath = "../images/pdf.png";

					break;

				case "JPG":
					ls_fileTypePath = "../images/png.png";

					break;

				case "DOCX":
					ls_fileTypePath = "../images/word.png";

					break;

				case "PNG":
					ls_fileTypePath = "../images/png.png";

					break;

				case "XLSX":
					ls_fileTypePath = "../images/excel_ico.png";

					break;

				default:
					ls_fileTypePath = IdentificadoresCommon.DATO_INVALIDO;
			}
		}

		return ls_fileTypePath;
	}

	/**
	 * Retorna Objeto o variable de valor type archive.
	 *
	 * @param as_fileType de as file type
	 * @return el valor de type archive
	 */
	public String getTypeArchive(String as_fileType)
	{
		String ls_fileTypePath;

		ls_fileTypePath = IdentificadoresCommon.DATO_INVALIDO;

		if(StringUtils.isValidString(as_fileType))
		{
			as_fileType = StringUtils.getStringLowerCase(as_fileType);

			switch(as_fileType.toUpperCase())
			{
				case TipoArchivoCommon.RTF_TXT:
					ls_fileTypePath = TipoContenidoCommon.RTF;

					break;

				case TipoArchivoCommon.PDF:
					ls_fileTypePath = TipoContenidoCommon.PDF;

					break;

				case TipoArchivoCommon.JPG:
					ls_fileTypePath = TipoContenidoCommon.JPG_JPEG;

					break;

				case TipoArchivoCommon.DOCX:
					ls_fileTypePath = TipoContenidoCommon.DOC_XDOC;

					break;

				case TipoArchivoCommon.PNG:
					ls_fileTypePath = TipoContenidoCommon.PNG;

					break;

				case TipoArchivoCommon.XLSX:
					ls_fileTypePath = TipoContenidoCommon.XLS_XLSX_XXLS;

					break;

				default:
					ls_fileTypePath = IdentificadoresCommon.DATO_INVALIDO;
			}
		}

		return ls_fileTypePath;
	}

	/**
	
	/**
	 * Retorna Objeto o variable de valor string.
	 *
	 * @param ad_d de ad d
	 * @param as_format de as format
	 * @return el valor de string
	 */
	public String getString(Date ad_d, String as_format)
	{
		return StringUtils.getString(ad_d, as_format);
	}

	/**
	 * Valida la propiedad valid collection.
	 *
	 * @param acu_cu de acu cu
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en valid collection
	 */
	public boolean isValidCollection(Collection<?> acu_cu)
	{
		return CollectionUtils.isValidCollection(acu_cu);
	}

	/**
	 * Valida la propiedad valid long.
	 *
	 * @param al_l de al l
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en valid long
	 */
	public boolean isValidLong(Long al_l)
	{
		return NumericUtils.isValidLong(al_l);
	}

	/**
	 * Valida la propiedad valid string.
	 *
	 * @param as_s de as s
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en valid string
	 */
	public boolean isValidString(String as_s)
	{
		return StringUtils.isValidString(as_s);
	}

	/**
	 * Metodo encargado de abrir dialogos de tipo modal.
	 *
	 * @param as_widget
	 *            Argumento de tipo <code>String</code> que contiene el id del
	 *            widget con el que se abre el dialogo.
	 * @param as_idForm
	 *            Argumento de tipo <code>String</code> que contiene el id del
	 *            formulario del dialogo a actualizar.
	 */
	public void abrirDialogo(String as_widget, String as_idForm)
	{
		if(StringUtils.isValidString(as_widget))
		{
			PrimeFaces.current().executeScript("PF('" + as_widget + "').show();");
			actualizarComponente(as_idForm);
		}
	}

	/**
	 * Método para el cambio de idioma en bachué.
	 *
	 * @throws IOException Objeto de tipo IOException, se produce cuando se encuentra algun error de tipo I/O.
	 */
	public void cambiarIdiomaWorkflow()
	    throws IOException
	{
		FacesContext lfc_facesContext;
		lfc_facesContext = FacesContext.getCurrentInstance();
		setIdiomaEspanol(!isIdiomaEspanol());

		if(isIdiomaEspanol())
		{
			FacesUtils.getSession().setAttribute("locale", new Locale("ES"));

			if(im_messages != null)
				im_messages.setBundleName(cs_CONFIG_MESSAGES);
			else
				im_messages = new Messages(cs_CONFIG_MESSAGES);

			if(im_messagesOpciones != null)
				im_messagesOpciones.setBundleName(CONFIG_MESSAGES_OPTIONS);
			else
				im_messagesOpciones = new Messages(CONFIG_MESSAGES_OPTIONS);

			B2BException.setBundle(cs_CONFIG_ERRORS);
		}
		else
		{
			if(im_messages != null)
				im_messages.setBundleName(CONFIG_MESSAGES_EN);
			else
				im_messages = new Messages(CONFIG_MESSAGES_EN);

			B2BException.setBundle(CONFIG_ERRORS_EN);

			if(im_messagesOpciones != null)
				im_messagesOpciones.setBundleName(CONFIG_MESSAGES_OPTIONS_EN);
			else
				im_messagesOpciones = new Messages(CONFIG_MESSAGES_OPTIONS_EN);

			FacesUtils.getSession().setAttribute("locale", Locale.ENGLISH);
		}

		lfc_facesContext.getExternalContext().redirect("principal.jsf");
	}

	/**
	 * Metodo encargado de cerrar dialogos de tipo modal.
	 *
	 * @param as_widget
	 *            Argumento de tipo <code>String</code> que contiene el id del
	 *            widget con el que se abre el dialogo.
	 * @param as_form
	 *            correspondiente al valor del tipo de objeto String
	 */
	public void cerrarDialogo(String as_widget, String as_form)
	{
		if(StringUtils.isValidString(as_widget))
		{
			PrimeFaces lpf_faces;

			lpf_faces = PrimeFaces.current();

			lpf_faces.executeScript("PF('" + as_widget + "').hide();");
			lpf_faces.ajax().update(as_form);
		}
	}

	/**
	 * Contar.
	 *
	 * @param as_campo de as campo
	 * @return el valor de string
	 */
	public String contar(String as_campo)
	{
		char[] arrayChar;
		int    contador;
		String result;

		contador = 0;

		if(as_campo != null)
		{
			arrayChar     = as_campo.toCharArray();
			contador      = arrayChar.length;
		}

		result = Integer.toString(contador);

		return result;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param as_texto
	 *            correspondiente al valor del tipo de objeto String
	 * @return devuelve el valor de String
	 * @throws B2BException
	 *             Señala que se ha producido una excepción
	 */
	public String convertirTextoParaRtf(String as_texto)
	    throws B2BException
	{
		if(StringUtils.isValidString(as_texto))
		{
			if(as_texto.contains("<img "))
				throw new B2BException(ErrorKeys.ERROR_IMAGENES_EN_TEXTO);

			if(as_texto.contains("<p>"))
				as_texto = as_texto.replace("<p>", "{\\pard\\qj ");

			if(as_texto.contains("</p>"))
				as_texto = as_texto.replace("</p>", "\\par}");

			{
				String ls_espacio;

				ls_espacio = ";&nbsp;";

				if(as_texto.contains(ls_espacio))
					as_texto = as_texto.replace(ls_espacio, " ");
			}

			{
				int    li_inicioTag;
				int    li_finalTag;
				String ls_tagSinCerrar;

				while(as_texto.contains("<") && as_texto.contains(">"))
				{
					li_inicioTag        = as_texto.indexOf("<");
					li_finalTag         = as_texto.substring(li_inicioTag).indexOf(">");
					li_finalTag         = li_finalTag + li_inicioTag + 1;
					ls_tagSinCerrar     = as_texto.substring(li_inicioTag, li_finalTag);

					if(StringUtils.isValidString(ls_tagSinCerrar))
						as_texto = as_texto.replace(ls_tagSinCerrar, "");
				}
			}
		}

		return as_texto;
	}

	/**
	 * Generate exception invalid field.
	 *
	 * @param as_fieldName de as field name
	 * @return el valor de b 2 B exception
	 */
	public B2BException generateExceptionInvalidField(String as_fieldName)
	{
		B2BException lb2be_exception;

		lb2be_exception = null;

		if(StringUtils.isValidString(as_fieldName))
		{
			Object[] loa_args;

			loa_args        = new String[1];
			loa_args[0]     = as_fieldName;

			lb2be_exception = new B2BException(ErrorKeys.EL_CAMPO_ES_INVALIDO, loa_args);
		}

		return lb2be_exception;
	}

	/**
	 * Método para obtener la instancia de un bean.
	 *
	 * @param acu_bean            <code>Class ?</code> del bean que se quiere obtener.
	 * @param as_beanNameCommon            <code>String</code> que contiene el nombre del bean.
	 * @return <code>Object</code> que contiene la instancia del bean obtenido.
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public Object obtenerInstanciaBean(Class<?> acu_bean, String as_beanNameCommon)
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
	 * Método de validación del idioma de bachué.
	 *
	 * @throws IOException Objeto de tipo IOException, se produce cuando se encuentra algun error de tipo I/O.
	 */
	public void validarIdioma()
	    throws IOException
	{
		if(isIdiomaEspanol())
		{
			FacesUtils.getSession().setAttribute("locale", new Locale("ES"));

			if(im_messages != null)
				im_messages.setBundleName(cs_CONFIG_MESSAGES);
			else
				im_messages = new Messages(cs_CONFIG_MESSAGES);

			if(im_messagesOpciones != null)
				im_messagesOpciones.setBundleName(CONFIG_MESSAGES_OPTIONS);
			else
				im_messagesOpciones = new Messages(CONFIG_MESSAGES_OPTIONS);

			B2BException.setBundle(cs_CONFIG_ERRORS);
		}
		else
		{
			FacesUtils.getSession().setAttribute("locale", Locale.ENGLISH);

			if(im_messages != null)
				im_messages.setBundleName(CONFIG_MESSAGES_EN);
			else
				im_messages = new Messages(CONFIG_MESSAGES_EN);

			if(im_messagesOpciones != null)
				im_messagesOpciones.setBundleName(CONFIG_MESSAGES_OPTIONS_EN);
			else
				im_messagesOpciones = new Messages(CONFIG_MESSAGES_OPTIONS_EN);

			B2BException.setBundle(CONFIG_ERRORS_EN);
		}
	}

	/**
	 * Retorna el valor de application.
	 *
	 * @return el valor de application
	 */
	protected abstract String getApplication();

	/**
	 * Retorna el valor de local ip address.
	 *
	 * @return el valor de local ip address
	 */
	protected String getLocalIpAddress()
	{
		HttpServletRequest lhsr_request;

		lhsr_request = FacesUtils.getRequest();

		return (lhsr_request != null) ? lhsr_request.getLocalAddr() : null;
	}

	/**
	 * Clean properties of bean.
	 */
	protected abstract void clean();

	/**
	 * Retorna el valor de messages.
	 *
	 * @return el valor de messages
	 */
	protected static Messages getMessages()
	{
		if(im_messages == null)
			im_messages = new Messages(cs_CONFIG_MESSAGES);

		return im_messages;
	}

	/**
	 * Retorna el valor de messages.
	 *
	 * @return el valor de messages
	 */
	protected static Messages getMessagesWorkflow()
	{
		if(im_messages == null)
			im_messages = new Messages(cs_CONFIG_MESSAGES);

		return im_messages;
	}

	/**
	 * Retorna el valor de user id.
	 *
	 * @return el valor de user id
	 */
	protected String getUserId()
	{
		return FacesUtils.getSessionAttributeAsString(IdentificadoresCommon.SESION_USUARIO);
	}

	/**
	 * Adds the message.
	 *
	 * @param ab2be_exception correspondiente al valor del tipo de objeto B2BException
	 */
	protected static void addMessage(B2BException ab2be_exception)
	{
		if(ab2be_exception != null)
			addMessage("W", ab2be_exception.getMessage());
	}

	/**
	 * Adds the message.
	 *
	 * @param as_mensaje correspondiente al valor del tipo de objeto String
	 */
	protected static void addMessage(String as_mensaje)
	{
		if(StringUtils.isValidString(as_mensaje))
			addMessage("I", getMessages().getString(as_mensaje));
	}

	/**
	 * Adds the message.
	 *
	 * @param as_mensaje correspondiente al valor del tipo de objeto String
	 * @param aoa_oa correspondiente al valor del tipo de objeto Object[]
	 */
	protected static void addMessage(String as_mensaje, Object[] aoa_oa)
	{
		if(StringUtils.isValidString(as_mensaje))
		{
			if(aoa_oa != null)
				addMessage("I", getMessages().getString(as_mensaje, aoa_oa));
			else
				addMessage(as_mensaje);
		}
	}

	/**
	 * Adds the message.
	 *
	 * @param as_tipo correspondiente al valor del tipo de objeto String
	 * @param as_mensaje correspondiente al valor del tipo de objeto String
	 */
	protected static void addMessage(String as_tipo, String as_mensaje)
	{
		FacesContext lfc_context;
		lfc_context = FacesContext.getCurrentInstance();

		if(StringUtils.isValidString(as_tipo) && StringUtils.isValidString(as_mensaje))
		{
			FacesMessage.Severity lfs_severity;
			lfs_severity = null;

			if(as_tipo.equalsIgnoreCase("E"))
			{
				as_tipo          = "Error: ";
				lfs_severity     = FacesMessage.SEVERITY_ERROR;
			}
			else if(as_tipo.equalsIgnoreCase("I"))
			{
				as_tipo          = "Información: ";
				lfs_severity     = FacesMessage.SEVERITY_INFO;
			}
			else if(as_tipo.equalsIgnoreCase("F"))
			{
				as_tipo          = "Error Fatal: ";
				lfs_severity     = FacesMessage.SEVERITY_FATAL;
			}
			else if(as_tipo.equalsIgnoreCase("W"))
			{
				as_tipo          = "Advertencia: ";
				lfs_severity     = FacesMessage.SEVERITY_WARN;
			}
			else
				lfc_context.addMessage(null, new FacesMessage(as_tipo, as_mensaje));

			if(lfs_severity != null)
				lfc_context.addMessage(null, new FacesMessage(lfs_severity, as_tipo, as_mensaje));
		}
	}

	/**
	 * Adds the message info.
	 *
	 * @param as_tipo correspondiente al valor del tipo de objeto String
	 * @param as_mensaje correspondiente al valor del tipo de objeto String
	 */
	protected static void addMessageInfo(String as_tipo, String as_mensaje)
	{
		if(StringUtils.isValidString(as_tipo) && StringUtils.isValidString(as_mensaje))
			addMessage(as_tipo, getMessages().getString(as_mensaje));
	}

	/**
	 * Adds the message.
	 *
	 * @param as_mensaje correspondiente al valor del tipo de objeto String
	 */
	protected static void addMessageWorkflow(String as_mensaje)
	{
		if(StringUtils.isValidString(as_mensaje))
			addMessage("I", getMessages().getString(as_mensaje));
	}

	/**
	 * Retorna el valor de remote ip address.
	 *
	 * @return el valor de remote ip address
	 */
	protected String getRemoteIpAddress()
	{
		HttpServletRequest lhsr_request;

		lhsr_request = FacesUtils.getRequest();

		return (lhsr_request != null) ? lhsr_request.getRemoteAddr() : null;
	}

	/**
	 * Actualizar componente.
	 *
	 * @param as_idComponente correspondiente al valor del tipo de objeto String
	 */
	protected void actualizarComponente(String as_idComponente)
	{
		if(StringUtils.isValidString(as_idComponente))
		{
			Ajax la_ajax;

			la_ajax = PrimeFaces.current().ajax();

			if(la_ajax != null)
				la_ajax.update(as_idComponente);
		}
	}

	/**
	 * Validar fecha desde fecha hasta.
	 *
	 * @param ad_desde de ad desde
	 * @param ad_hasta de ad hasta
	 * @param as_idComponenteDesde de as id componente desde
	 * @param as_idComponenteHasta de as id componente hasta
	 * @param afc_context de afc context
	 * @param ab_focus de ab focus
	 * @param ab_desdeMayor Bandera que indica si se debe validar que la fecha desde sea mayor a la actual o al revés.
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	protected void validarFechaDesdeFechaHasta(
	    Date ad_desde, Date ad_hasta, String as_idComponenteDesde, String as_idComponenteHasta, FacesContext afc_context,
	    boolean ab_focus, boolean ab_desdeMayor
	)
	    throws B2BException
	{
		if(ad_desde != null)
		{
			if(StringUtils.isValidString(as_idComponenteDesde) && StringUtils.isValidString(as_idComponenteHasta))
			{
				Date ld_fechaActual;

				ld_fechaActual = new Date();

				if(ab_desdeMayor)
				{
					if(ad_desde.after(ld_fechaActual))
					{
						ab_focus = validateStyles(
							    as_idComponenteDesde, afc_context, IdentificadoresCommon.DATO_INVALIDO, ab_focus
							);
						throw new B2BException(ErrorKeys.FECHA_DESDE_SUPERIOR_ACTUAL);
					}
				}
				else
				{
					if(ad_desde.before(ld_fechaActual))
					{
						ab_focus = validateStyles(
							    as_idComponenteDesde, afc_context, IdentificadoresCommon.DATO_INVALIDO, ab_focus
							);
						throw new B2BException(ErrorKeys.FECHA_DESDE_DEBE_SER_SUPERIOR);
					}
				}

				if((ad_hasta != null) && ad_desde.after(ad_hasta))
				{
					ab_focus     = validateStyles(
						    as_idComponenteDesde, afc_context, IdentificadoresCommon.DATO_INVALIDO, ab_focus
						);
					ab_focus     = validateStyles(
						    as_idComponenteHasta, afc_context, IdentificadoresCommon.DATO_INVALIDO, ab_focus
						);
					throw new B2BException(ErrorKeys.FECHA_DESDE_SUPERIOR);
				}
			}
		}
		else
		{
			ab_focus = validateStyles(as_idComponenteDesde, afc_context, IdentificadoresCommon.DATO_INVALIDO, ab_focus);
			throw new B2BException(ErrorKeys.ERROR_SIN_FECHA_DESDE);
		}
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @param as_idComponente
	 *            correspondiente al valor del tipo de objeto String
	 * @param as_datoParaValidar
	 *            correspondiente al valor del tipo de objeto String
	 * @param ls_style
	 *            correspondiente al valor del tipo de objeto String
	 * @param ab_focus
	 *            correspondiente al valor del tipo de objeto boolean
	 * @param ab_outputLabel
	 *            correspondiente al valor del tipo de objeto boolean
	 * @return devuelve el valor de String
	 */
	private static String changeStyleComponent(
	    String as_idComponente, String as_datoParaValidar, String ls_style, boolean ab_focus, boolean ab_outputLabel
	)
	{
		if(!StringUtils.isValidString(as_datoParaValidar))
		{
			if(StringUtils.isValidString(ls_style))
			{
				if(ls_style.contains(ab_outputLabel ? "color:" : "border-color:"))
					ls_style = ls_style.replace(ab_outputLabel ? ":black;" : ":null;", ":red;");
				else
					ls_style = ls_style + ((!ls_style.contains(";")) ? ";" : "")
						+ (ab_outputLabel ? "color:red;" : "border-color:red;");
			}
			else
				ls_style = ab_outputLabel ? "color:red;" : "border-color:red;";

			if(ab_focus)
			{
				if(StringUtils.isValidString(as_idComponente) && as_idComponente.startsWith(":"))
				{
					StringBuilder lsb_sb;

					lsb_sb = new StringBuilder(as_idComponente);

					lsb_sb.deleteCharAt(0);

					as_idComponente = lsb_sb.toString();
				}

				//	RequestContext.getCurrentInstance().execute("PrimeFaces.focus('" + as_idComponente + "');");
			}
		}
		else
		{
			if(StringUtils.isValidString(ls_style))
			{
				if(ls_style.contains(ab_outputLabel ? "color:" : "border-color:"))
					ls_style = ls_style.replace(":red;", ab_outputLabel ? ":black;" : ":null;");
				else
					ls_style = ls_style + ((!ls_style.contains(";")) ? ";" : "")
						+ (ab_outputLabel ? "color:black;" : "border-color:null;");
			}
			else
				ls_style = ab_outputLabel ? "color:black;" : "border-color:null;";
		}

		return ls_style;
	}
}
