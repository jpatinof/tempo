package com.cromasoft.cromaflow.web.util;

import com.cromasoft.cromaflow.model.core.Auditoria;

import org.primefaces.model.StreamedContent;


/**
 * Clase que contiene todos las propiedades Document.
 *
 * @author  Jorge Esteban Patiño Fonseca
 * Fecha de Creacion: 24/06/2021
 */
public class Document extends Auditoria
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = -52809323922391488L;

	/** Propiedad isc guide. */
	private StreamedContent isc_guide;

	/** Propiedad isc template. */
	private StreamedContent isc_template;

	/** Propiedad is text guide. */
	private String is_textGuide;

	/** Propiedad is text template. */
	private String is_textTemplate;

	/**
	 * Retorna Objeto o variable de valor text template.
	 *
	 * @return el valor de text template
	 */
	public String getTextTemplate()
	{
		return is_textTemplate;
	}

	/**
	 * Modifica el valor de TextTemplate.
	 *
	 * @param as_s de is text template
	 */
	public void setTextTemplate(String as_s)
	{
		is_textTemplate = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor text guide.
	 *
	 * @return el valor de text guide
	 */
	public String getTextGuide()
	{
		return is_textGuide;
	}

	/**
	 * Modifica el valor de TextGuide.
	 *
	 * @param as_s de is text guide
	 */
	public void setTextGuide(String as_s)
	{
		is_textGuide = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor template.
	 *
	 * @return el valor de template
	 */
	public StreamedContent getTemplate()
	{
		return isc_template;
	}

	/**
	 * Modifica el valor de Template.
	 *
	 * @param asc_sc de isc template
	 */
	public void setTemplate(StreamedContent asc_sc)
	{
		isc_template = asc_sc;
	}

	/**
	 * Retorna Objeto o variable de valor guide.
	 *
	 * @return el valor de guide
	 */
	public StreamedContent getGuide()
	{
		return isc_guide;
	}

	/**
	 * Modifica el valor de Guide.
	 *
	 * @param asc_sc de isc guide
	 */
	public void setGuide(StreamedContent asc_sc)
	{
		isc_guide = asc_sc;
	}
}
