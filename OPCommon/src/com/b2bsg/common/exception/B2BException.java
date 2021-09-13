package com.b2bsg.common.exception;

import com.b2bsg.common.messages.Messages;

import com.b2bsg.common.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Clase que contiene todos las propiedades de B2BException para el
 * manejo de excepciones controladas
 *
 * @author Edgar Prieto
 */
public class B2BException extends Exception
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Propiedad cm messages. */
	private static Messages cm_messages = new Messages();

	/** Propiedad ie root exception. */
	private Exception ie_rootException;

	/** Propiedad is message key. */
	private String is_messageKey;

	/** Propiedad ioa message args. */
	private Object[] ioa_messageArgs;

	/**
	 * Instancia un nuevo objeto B2BException.
	 *
	 * @param as_messageKey correspondiente al valor del tipo de objeto String
	 * @param aoa_messageArgs correspondiente al valor del tipo de objeto Object[]
	 */
	public B2BException(String as_messageKey, Object[] aoa_messageArgs)
	{
		this(as_messageKey, aoa_messageArgs, null);
	}

	/**
	 * Instancia un nuevo objeto B2BException.
	 *
	 * @param as_messageKey correspondiente al valor del tipo de objeto String
	 */
	public B2BException(String as_messageKey)
	{
		this(as_messageKey, null, null);
	}

	/**
	 * Instancia un nuevo objeto B2BException.
	 *
	 * @param as_messageKey correspondiente al valor del tipo de objeto String
	 * @param ae_rootException correspondiente al valor del tipo de objeto Exception
	 */
	public B2BException(String as_messageKey, Exception ae_rootException)
	{
		this(as_messageKey, null, ae_rootException);
	}

	/**
	 * Instancia un nuevo objeto B2BException.
	 *
	 * @param as_messageKey correspondiente al valor del tipo de objeto String
	 * @param aoa_messageArgs correspondiente al valor del tipo de objeto Object[]
	 * @param ae_rootException correspondiente al valor del tipo de objeto Exception
	 */
	private B2BException(String as_messageKey, Object[] aoa_messageArgs, Exception ae_rootException)
	{
		if(!setRootException(ae_rootException))
		{
			is_messageKey       = StringUtils.getString(as_messageKey);
			ioa_messageArgs     = aoa_messageArgs;
		}
	}

	/**
	 * Modifica el valor de bundle.
	 *
	 * @param as_bundle asigna el valor a la propiedad bundle
	 */
	public static void setBundle(String as_bundle)
	{
		if(StringUtils.isValidString(as_bundle))
			cm_messages.setBundleName(as_bundle);
	}

	/** {@inheritdoc} */
	public String getMessage()
	{
		boolean lb_messageArgs;
		boolean lb_rootException;
		String  ls_msg;

		lb_messageArgs       = ioa_messageArgs != null;
		lb_rootException     = ie_rootException != null;
		ls_msg               = null;

		if(lb_messageArgs)
		{
			int li_length;

			li_length = ioa_messageArgs.length;

			if(li_length > 0)
				ls_msg = cm_messages.getString(is_messageKey, ioa_messageArgs);
		}
		else if(lb_rootException)
		{
			String ls_rootExceptionMessage;

			ls_rootExceptionMessage = ie_rootException.getMessage();

			if(StringUtils.isValidString(ls_rootExceptionMessage))
			{
				Object[] loa_args = {ie_rootException.getMessage()};

				ls_msg = cm_messages.getString(is_messageKey, loa_args);
			}
		}
		else
			ls_msg = cm_messages.getString(is_messageKey);

		if(!StringUtils.isValidString(ls_msg))
		{
			StringBuilder lsb_msg;

			lsb_msg = new StringBuilder();

			if(StringUtils.isValidString(is_messageKey))
				lsb_msg.append(is_messageKey);

			if(lb_messageArgs)
			{
				int li_length;

				li_length = ioa_messageArgs.length;

				if(li_length > 0)
				{
					if(lsb_msg.length() > 0)
						lsb_msg.append("\n");

					lsb_msg.append("messageArgs\n");

					for(int li_i = 0; li_i < li_length; li_i++)
						lsb_msg.append("\t" + ioa_messageArgs[li_i] + "\n");
				}
			}

			if(lb_rootException)
			{
				boolean       lb_notPrintStackTrace;
				StringBuilder lsb_sb;

				lsb_sb                    = new StringBuilder();
				lb_notPrintStackTrace     = false;

				if(lsb_msg.length() > 0)
					lsb_msg.append("\n");

				lsb_sb.append(ie_rootException.getClass().getName());

				if(ie_rootException.getMessage() != null)
				{
					lsb_msg.append("\n\t");
					lsb_sb.append(ie_rootException.getMessage());

					lb_notPrintStackTrace = true;
				}

/*            No compila con 1.3
 *             if(ie_rootException.getCause() != null)
            {
                lsb_msg.append("\n\t");
                lsb_sb.append(ie_rootException.getCause());

                lb_notPrintStackTrace = true;
            }
*/
				if(!lb_notPrintStackTrace)
				{
					PrintWriter  lpw_pw;
					String       ls_trace;
					StringWriter lsw_sw;

					lsw_sw     = new StringWriter();
					lpw_pw     = new PrintWriter(lsw_sw);

					ie_rootException.printStackTrace(lpw_pw);
					lpw_pw.flush();

					ls_trace = lsw_sw.toString();

					lsb_sb.append(ls_trace);
				}

				lsb_msg.append(lsb_sb.toString());
			}

			ls_msg = lsb_msg.toString();
		}

		return ls_msg;
	}

	/**
	 * Retorna el valor de message key.
	 *
	 * @return Obtiene la propiedad is_messageKey.
	 */
	public String getMessageKey()
	{
		return is_messageKey;
	}

	/**
	 * Retorna el valor de message args.
	 *
	 * @return Obtiene la propiedad ioa_messageArgs.
	 */
	private Object[] getMessageArgs()
	{
		return ioa_messageArgs;
	}

	/**
	 * Sets the root exception.
	 *
	 * @param ae_rootException correspondiente al valor del tipo de objeto Exception
	 * @return verdadero, si se cumple la condicion, de lo contario retorna falso
	 */
	private boolean setRootException(Exception ae_rootException)
	{
		boolean lb_matchType;

		lb_matchType = false;

		if(ae_rootException != null)
		{
			lb_matchType = ae_rootException instanceof B2BException;

			if(lb_matchType)
			{
				B2BException lb2be_e;

				lb2be_e           = (B2BException)ae_rootException;
				is_messageKey     = StringUtils.getString(lb2be_e.getMessageKey());

				if(lb2be_e.getMessageArgs() != null)
					ioa_messageArgs = lb2be_e.getMessageArgs();

				setRootException(lb2be_e.getRootException());
			}
			else
				ie_rootException = ae_rootException;
		}

		return lb_matchType;
	}

	/**
	 * Retorna el valor de root exception.
	 *
	 * @return el valor de root exception
	 */
	private Exception getRootException()
	{
		return ie_rootException;
	}
}
