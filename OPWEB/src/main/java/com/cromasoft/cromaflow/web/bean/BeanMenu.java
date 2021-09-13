package com.cromasoft.cromaflow.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import com.b2bsg.common.exception.B2BException;
import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;


/**
 * Clase para el manejo del menu en la aplicacion.
 *
 * @author Gabriel Arias
 */
@Named("beanMenu")
@SessionScoped
public class BeanMenu extends BaseBean implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 4074563807253988742L;

	/** Propiedad icdmi items del menu. */
	private Collection<DefaultMenuItem> icdmi_itemsDelMenu;

	/** Propiedad imm menu. */
	private MenuModel imm_menu;

	/**
	 *  {@inheritdoc}.
	 *
	 * @return el valor de application
	 */
	@Override
	public String getApplication()
	{
		return null;
	}

	/**
	 * Método para asignar un valor a la propiedad.
	 *
	 * @param acdmi_cdmi valor a asignar a la propiedad.
	 */
	public void setItemsDelMenu(Collection<DefaultMenuItem> acdmi_cdmi)
	{
		icdmi_itemsDelMenu = acdmi_cdmi;
	}

	/**
	 * Método para obtener el valor de la propiedad.
	 *
	 * @return el valor de la propiedad.
	 */
	public Collection<DefaultMenuItem> getItemsDelMenu()
	{
		return icdmi_itemsDelMenu;
	}

	/**
	 * Método para asignar un valor a la propiedad.
	 *
	 * @param amm_mm valor a asignar a la propiedad.
	 */
	public void setMenu(MenuModel amm_mm)
	{
		imm_menu = amm_mm;
	}

	/**
	 * Método para obtener el valor de la propiedad.
	 *
	 * @return el valor de la propiedad.
	 */
	public MenuModel getMenu()
	{
		return imm_menu;
	}

	/**
	 * Cambia el estilo de una opción seleccionada para identificarla como visitada.
	 */
	public void marcarOpcionVisitada()
	{
		String    ls_opcion;
		MenuModel lmm_menu;

		ls_opcion     = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter("opcionSeleccionada");
		lmm_menu      = getMenu();

		if(StringUtils.isValidString(ls_opcion) && (lmm_menu != null))
		{
			Collection<DefaultMenuItem> lcdmi_items;

			lcdmi_items = getItemsDelMenu();

			if(!CollectionUtils.isValidCollection(lcdmi_items))
			{
				lcdmi_items = obtenerItemsMenu(lmm_menu.getElements());

				if(CollectionUtils.isValidCollection(lcdmi_items))
					setItemsDelMenu(lcdmi_items);
			}

			if(CollectionUtils.isValidCollection(lcdmi_items))
			{
				//TODO Comentar lambda antes de formatear
				lcdmi_items.stream().filter(
						ldmi_item -> ldmi_item.getValue() != null && ldmi_item.getValue().toString().equals(ls_opcion))
						.forEach(ldmi_item -> ldmi_item.setStyleClass("estiloVisitado"));
			}
		}
	}

	/**
	 * Método que indica que accion se debe ejecutarr en cada una de las paginas del
	 * menú.
	 *
	 * @param as_pagina
	 *            pagina que el usuario en sesion accede
	 * @return devuelve el valor de String
	 * @throws B2BException
	 *             Señala que se ha producido una excepción
	 * @throws IOException
	 *             Señala que se ha producido una excepción de E / S.
	 */
	public String pages(String as_pagina)
	    throws B2BException, IOException
	{
		return pages(as_pagina, null, null, null);
	}

	/**
	 * Pages.
	 *
	 * @param as_pagina de as pagina
	 * @param as_opcion de as opcion
	 * @param as_idReporte de as id reporte
	 * @param as_idTipoPresentacion de as id tipo presentacion
	 * @return el valor de string
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 * @throws IOException Objeto de tipo IOException, se produce cuando se encuentra algun error de tipo I/O.
	 */
	public String pages(String as_pagina, String as_opcion, String as_idReporte, String as_idTipoPresentacion)
	    throws B2BException, IOException
	{
		marcarOpcionVisitada();

		switch(as_pagina)
		{
			default:
				break;
		}

		return as_pagina;
	}

	/* (non-Javadoc)
	 * @see com.cromasoft.cromaflow.web.bean.BaseBean#clean()
	 */
	@Override
	protected void clean()
	{
		setItemsDelMenu(null);
		setMenu(null);
	}

	/**
	 * Obtiene los item que componen el menú.
	 *
	 * @param acme_elementos Colección de elementos contenedores de los items del menú
	 * @return Colección de items resultante de la busqueda
	 */
	private Collection<DefaultMenuItem> obtenerItemsMenu(Collection<MenuElement> acme_elementos)
	{
		Collection<DefaultMenuItem> lcdmi_items;

		lcdmi_items = new LinkedList<DefaultMenuItem>();

		if(CollectionUtils.isValidCollection(acme_elementos))
		{
			for(MenuElement lme_elemento : acme_elementos)
			{
				if(lme_elemento != null)
				{
					Object lo_object;

					lo_object = (Object)lme_elemento;

					if(lo_object instanceof DefaultMenuItem)
					{
						DefaultMenuItem ldmi_item;

						ldmi_item = (DefaultMenuItem)lo_object;

						if(ldmi_item != null)
							lcdmi_items.add(ldmi_item);
					}
					else if(lo_object instanceof DefaultSubMenu)
					{
						DefaultSubMenu ldsm_item;

						ldsm_item = (DefaultSubMenu)lo_object;

						if(ldsm_item != null)
						{
							Collection<DefaultMenuItem> lcdmi_itemsTmp;

							lcdmi_itemsTmp = obtenerItemsMenu(ldsm_item.getElements());

							if(CollectionUtils.isValidCollection(lcdmi_itemsTmp))
								lcdmi_items.addAll(lcdmi_itemsTmp);
						}
					}
				}
			}
		}

		return lcdmi_items;
	}
}
