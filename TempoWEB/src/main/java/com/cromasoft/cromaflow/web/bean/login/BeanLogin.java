package com.cromasoft.cromaflow.web.bean.login;

import com.b2bsg.common.exception.B2BException;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;

import com.cromasoft.cromaflow.common.constants.NavegacionCommon;

import com.cromasoft.cromaflow.constants.IdentificadoresCommon;

import com.cromasoft.cromaflow.model.aut.Login;
import com.cromasoft.cromaflow.model.aut.Role;
import com.cromasoft.cromaflow.model.aut.User;

import com.cromasoft.cromaflow.web.bean.BaseBean;

import com.cromasoft.cromaflow.web.util.FacesUtils;

import com.cromasoft.osteophoenix.ejb.session.stateless.core.UsersRemote;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;

import javax.enterprise.context.SessionScoped;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


// TODO: Auto-generated Javadoc
/**
 * Clase que contiene todos las propiedades de BeanLogin.
 *
 * @author Julian Vaca
 */
@Named("beanLogin")
@SessionScoped
public class BeanLogin extends BaseBean implements Serializable
{
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 5803333333001272430L;

	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(BeanLogin.class);

	/** Propiedad is nombre usuario. */
	private String is_nombreUsuario;

	/** Propiedad is password. */
	private String is_password;

	/** Propiedad is user. */
	private String is_user;

	/** Propiedad is user first name. */
	private String is_userFirstName;

	/** Propiedad is user gender. */
	private String is_userGender;

	/** Propiedad is user roles. */
	private String is_userRoles;

	/** Propiedad is version actual. */
	private String is_versionActual;

	/** Propiedad iu usuario sesion. */
	private User iu_usuarioSesion;

	/** Propiedad irr reference remote. */
	@EJB
	private UsersRemote iur_usersRemote;

	/** Propiedad iba user profile image. */
	private byte[] iba_userProfileImage;

	/** Propiedad ib administrator user. */
	private boolean ib_administratorUser;

	/** Propiedad ib allocator user. */
	private boolean ib_allocatorUser;

	/** Propiedad ib estado cerrar sesion. */
	private boolean ib_estadoCerrarSesion;

	/** Propiedad ib executing user. */
	private boolean ib_executingUser;

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
	 * Modifica el valor de Password.
	 *
	 * @param as_s de as s
	 */
	public void setPassword(String as_s)
	{
		is_password = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor password.
	 *
	 * @return el valor de password
	 */
	public String getPassword()
	{
		return is_password;
	}

	/**
	 * Modifica el valor de estado cerrar sesion.
	 *
	 * @param ab_b asigna el valor a la propiedad estado cerrar sesion
	 */
	public void setEstadoCerrarSesion(boolean ab_b)
	{
		ib_estadoCerrarSesion = ab_b;
	}

	/**
	 * Valida la propiedad estado cerrar sesion.
	 *
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en estado cerrar sesion
	 */
	public boolean isEstadoCerrarSesion()
	{
		return ib_estadoCerrarSesion;
	}

	/**
	 * Modifica el valor de nombre usuario.
	 *
	 * @param au_u asigna el valor a la propiedad nombre usuario
	 */
	public void setNombreUsuario(User au_u)
	{
		String ls_s1;

		ls_s1 = null;

		if(au_u != null)
		{
			String ls_pNombre;
			String ls_sNombre;
			String ls_pApellido;
			String ls_sApellido;

			ls_pNombre       = au_u.getFirstName();
			ls_sNombre       = au_u.getSecondName();
			ls_pApellido     = au_u.getFirstLastName();
			ls_sApellido     = au_u.getSecondLastName();

			ls_s1 = StringUtils.isValidString(ls_pNombre) ? ls_pNombre : "";
			ls_s1 += (StringUtils.isValidString(ls_s1) ? " " : "");
			ls_s1 += (StringUtils.isValidString(ls_sNombre) ? ls_sNombre : "");
			ls_s1 += (StringUtils.isValidString(ls_s1) ? " " : "");
			ls_s1 += (StringUtils.isValidString(ls_pApellido) ? ls_pApellido : "");
			ls_s1 += (StringUtils.isValidString(ls_s1) ? " " : "");
			ls_s1 += (StringUtils.isValidString(ls_sApellido) ? ls_sApellido : "");
			ls_s1 += (StringUtils.isValidString(ls_s1) ? " " : "");
		}

		is_nombreUsuario = ls_s1;
	}

	/**
	 * Retorna el valor de nombre usuario.
	 *
	 * @return el valor de nombre usuario
	 */
	public String getNombreUsuario()
	{
		return is_nombreUsuario;
	}

	/**
	 * Retorna el valor de time out.
	 *
	 * @return el valor de time out
	 */
	public int getTimeOut()
	{
		int li_timeOut = FacesUtils.getSession(false).getMaxInactiveInterval() * 1000;

		return li_timeOut;
	}

	/**
	 * Retorna el valor de time out medio.
	 *
	 * @return el valor de time out medio
	 */
	public int getTimeOutMedio()
	{
		int li_timeOut = FacesUtils.getSession(false).getMaxInactiveInterval() / 2;

		return li_timeOut;
	}

	/**
	 * Modifica el valor de usuario.
	 *
	 * @param as_s asigna el valor a la propiedad usuario
	 */
	public void setUser(String as_s)
	{
		is_user = as_s;
	}

	/**
	 * Retorna el valor de usuario.
	 *
	 * @return el valor de usuario
	 */
	public String getUser()
	{
		return is_user;
	}

	/**
	 * Modifica el valor de usuario sesion.
	 *
	 * @param au_u asigna el valor a la propiedad usuario sesion
	 */
	public void setUsuarioSesion(User au_u)
	{
		if(au_u != null)
		{
			String ls_idUsuario;

			ls_idUsuario = au_u.getUserId();

			if(StringUtils.isValidString(ls_idUsuario))
			{
				HttpSession        lhs_httpSession;
				HttpServletRequest https_request;

				lhs_httpSession     = FacesUtils.getSession(false);
				https_request       = FacesUtils.getRequest();
				ls_idUsuario        = https_request.getRemoteUser();

				lhs_httpSession.setAttribute(IdentificadoresCommon.SESION_USUARIO, ls_idUsuario);
				iu_usuarioSesion = au_u;
			}
		}
	}

	/**
	 * Retorna el valor de usuario sesion.
	 *
	 * @return el valor de usuario sesion
	 * @throws IOException Señala que se ha producido una excepción de E / S.
	 */
	public User getUsuarioSesion()
	    throws IOException
	{
		if(iu_usuarioSesion == null)
		{
			iu_usuarioSesion = new User();
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
		}

		return iu_usuarioSesion;
	}

	/**
	 * Modifica el valor de version actual.
	 *
	 * @param as_s asigna el valor a la propiedad version actual
	 */
	public void setVersionActual(String as_s)
	{
		is_versionActual = as_s;
	}

	/**
	 * Retorna el valor de version actual.
	 *
	 * @return el valor de version actual
	 */
	public String getVersionActual()
	{
		if(!StringUtils.isValidString(is_versionActual))
		{
			String ls_version;
			int    li_inicio;
			int    li_fin;

			li_inicio      = 0;
			li_fin         = 0;
			ls_version     = BeanLogin.class.getProtectionDomain().getCodeSource().getLocation().toString();

			if(StringUtils.isValidString(ls_version))
			{
				li_inicio = ls_version.indexOf("CFEAR_");

				if(li_inicio > 0)
					ls_version = ls_version.substring(li_inicio);

				li_fin = ls_version.indexOf("/");

				if(li_fin > 0)
					ls_version = ls_version.substring(0, li_fin);

				if(StringUtils.isValidString(ls_version))
					setVersionActual(ls_version);
			}
		}

		return is_versionActual;
	}

	/**
	 * Activar sesion.
	 */
	public void activarSesion()
	{
		new Date();
	}

	/**
	 * Método de cambio de idioma validando los properties de la aplicación.
	 *
	 * @throws IOException Objeto de tipo IOException, se produce cuando se encuentra algun error de tipo I/O.
	 * @throws B2BException Objeto de tipo B2BException, se produce cuando se encuentra algun error controlado.
	 */
	public void cambiarIdioma()
	    throws IOException, B2BException
	{
		cambiarIdiomaWorkflow();
	}

	/**
	 * Retorna el valor del objeto de String con la vista.
	 *
	 * @return devuelve el valor de String
	 */
	public String cerrarSesion()
	{
		HttpServletRequest lhsr_request;
		String             ls_return;

		lhsr_request     = FacesUtils.getRequest();
		ls_return        = null;

		try
		{
			if(lhsr_request != null)
			{
				HttpSession lhs_httpSession;

				lhs_httpSession = lhsr_request.getSession(false);

				lhs_httpSession.invalidate();
				lhsr_request.logout();

				clean();

				//TODO indagar solucion en glassfish
//				weblogic.servlet.security.ServletAuthentication.logout(lhsr_request);
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
			}
		}
		catch(Exception ex)
		{
			Logger.getLogger(BeanLogin.class.getName()).log(Level.SEVERE, null, ex);

			ls_return = null;
		}

		return ls_return;
	}

	/**
	 * Retorna el valor del objeto de String.
	 *
	 * @return devuelve el valor de String
	 * @throws IOException Señala que se ha producido una excepción de E / S.
	 */
	public String cerrarSesionExpirada()
	    throws IOException
	{
		HttpServletRequest lhsr_request;
		String             ls_return;

		lhsr_request     = FacesUtils.getRequest();
		ls_return        = null;

		try
		{
			if(lhsr_request != null)
			{
				HttpSession lhs_httpSession;

				lhs_httpSession = lhsr_request.getSession(false);

				lhs_httpSession.invalidate();
				lhsr_request.logout();

				setUser(null);
				setUsuarioSesion(null);
				setNombreUsuario(null);
				setEstadoCerrarSesion(false);

				//TODO indagar solucion en glassfish
//				weblogic.servlet.security.ServletAuthentication.logout(lhsr_request);
				FacesContext.getCurrentInstance().getExternalContext().redirect("loginExpired.jsf");
			}
		}
		catch(Exception ex)
		{
			Logger.getLogger(BeanLogin.class.getName()).log(Level.SEVERE, null, ex);

			ls_return = null;
		}

		return ls_return;
	}

	/**
	 * Retorna el valor del objeto de String con la vista del login.
	 *
	 * @return devuelve el valor de String
	 */
	public String login()
	{
		String ls_s;

		ls_s = null;

		setIdiomaEspanol(!FacesContext.getCurrentInstance().getViewRoot().getLocale().equals(Locale.ENGLISH));

		try
		{
			String ls_idUsuario;

			ls_idUsuario = getUser();

			if(StringUtils.isValidString(ls_idUsuario))
			{
				Login ll_loginData;

				ll_loginData = iur_usersRemote.login(ls_idUsuario, getPassword());

				if(ll_loginData != null)
				{
					User lu_usuario;

					lu_usuario = ll_loginData.getUser();

					if(lu_usuario != null)
					{
						HttpSession https_sesion = FacesUtils.getSession();

						setNombreUsuario(lu_usuario);
						setUsuarioSesion(lu_usuario);
						setEstadoCerrarSesion(true);
						setUserFirstName(lu_usuario.getFirstName());
						setUserGender(lu_usuario.getGender());
						setUserProfileImage(ll_loginData.getUserProfileImage());
						buildUserRoles(ll_loginData.getUserRoles());

						https_sesion.setAttribute(IdentificadoresCommon.SESION_USUARIO, ls_idUsuario);
						https_sesion.setAttribute(IdentificadoresCommon.SESION_NOMBRE_USUARIO, getNombreUsuario());
						https_sesion.setAttribute(IdentificadoresCommon.SESION_VERSION_ACTUAL, getVersionActual());

						ls_s = NavegacionCommon.PRINCIPAL_PAGE;
					}
				}
			}
			else
				FacesContext.getCurrentInstance()
					            .addMessage(
					    null,
					    new FacesMessage(
					        FacesMessage.SEVERITY_ERROR, "ERROR: ", "Por favor ingrese usuario y contraseña"
					    )
					);
		}

		catch(B2BException lb2be_e)
		{
			addMessage(lb2be_e);

			clh_LOGGER.error("login", lb2be_e);
		}

		return ls_s;
	}

	/**
	 * Builds the user roles string.
	 *
	 * @param acr_userRoles de acr user roles
	 */
	private void buildUserRoles(Collection<Role> acr_userRoles)
	{
		if(CollectionUtils.isValidCollection(acr_userRoles))
		{
			if(acr_userRoles.size() > 1)
			{
				for(Role ar_iterator : acr_userRoles)
				{
					if(ar_iterator != null)
						setUserRolesBooleans(ar_iterator, isAllocatorUser(), isExecutingUser(), isAdministratorUser());
				}
			}
			else
			{
				Iterator<Role> lir_iterator;

				lir_iterator = acr_userRoles.iterator();

				if((lir_iterator != null) && lir_iterator.hasNext())
				{
					Role lr_role;

					lr_role = lir_iterator.next();

					if(lr_role != null)
						setUserRolesBooleans(lr_role, isAllocatorUser(), isExecutingUser(), isAdministratorUser());
				}
			}
		}
	}

	/**
	 * Sets the user roles booleans.
	 *
	 * @param ar_userRole de ar user role
	 * @param ab_allocatorUser de ab allocator user
	 * @param ab_executingUser de ab executing user
	 */
	private void setUserRolesBooleans(
	    Role ar_userRole, boolean ab_allocatorUser, boolean ab_executingUser, boolean ab_administratorUser
	)
	{
		int li_roleId;

		li_roleId = ar_userRole.getRoleId();

		setAllocatorUser(ab_allocatorUser ? ab_allocatorUser : (li_roleId == 1));
		setExecutingUser(ab_executingUser ? ab_executingUser : (li_roleId == 2));
		setAdministratorUser(ab_administratorUser ? ab_administratorUser : (li_roleId == 3));
	}

	/* (non-Javadoc)
	 * @see com.cromasoft.cromaflow.web.bean.BaseBean#clean()
	 */
	@Override
	protected void clean()
	{
		setPassword(null);
		setEstadoCerrarSesion(false);
		setIdiomaEspanol(true);
		setNombreUsuario(null);
		setUser(null);
		setUsuarioSesion(null);
		setVersionActual(null);
		setUserGender(null);
		setUserProfileImage(null);
		setUserFirstName(null);
		setAllocatorUser(false);
		setExecutingUser(false);
		setUserRoles(null);
	}

	/**
	 * Retorna Objeto o variable de valor user first name.
	 *
	 * @return el valor de user first name
	 */
	public String getUserFirstName()
	{
		return is_userFirstName;
	}

	/**
	 * Modifica el valor de UserFirstName.
	 *
	 * @param as_s de is user first name
	 */
	public void setUserFirstName(String as_s)
	{
		is_userFirstName = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor user gender.
	 *
	 * @return el valor de user gender
	 */
	public String getUserGender()
	{
		return is_userGender;
	}

	/**
	 * Modifica el valor de UserGender.
	 *
	 * @param as_s de is user gender
	 */
	public void setUserGender(String as_s)
	{
		is_userGender = as_s;
	}

	/**
	 * Retorna Objeto o variable de valor user profile image.
	 *
	 * @return el valor de user profile image
	 */
	public byte[] getUserProfileImage()
	{
		return iba_userProfileImage;
	}

	/**
	 * Modifica el valor de UserProfileImage.
	 *
	 * @param aba_ba de iba user profile image
	 */
	public void setUserProfileImage(byte[] aba_ba)
	{
		iba_userProfileImage = aba_ba;
	}

	/**
	 * Retorna Objeto o variable de valor checks if is user roles.
	 *
	 * @return el valor de checks if is user roles
	 */
	public String getUserRoles()
	{
		return is_userRoles;
	}

	/**
	 * Modifica el valor de Is_userRoles.
	 *
	 * @param as_s de is user roles
	 */
	public void setUserRoles(String as_s)
	{
		is_userRoles = as_s;
	}

	/**
	 * Valida la propiedad allocator user.
	 *
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en allocator user
	 */
	public boolean isAllocatorUser()
	{
		return ib_allocatorUser;
	}

	/**
	 * Modifica el valor de AllocatorUser.
	 *
	 * @param ab_b de ib allocator user
	 */
	public void setAllocatorUser(boolean ab_b)
	{
		ib_allocatorUser = ab_b;
	}

	/**
	 * Valida la propiedad executing user.
	 *
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en executing user
	 */
	public boolean isExecutingUser()
	{
		return ib_executingUser;
	}

	/**
	 * Modifica el valor de ExecutingUser.
	 *
	 * @param ab_b de ib executing user
	 */
	public void setExecutingUser(boolean ab_b)
	{
		ib_executingUser = ab_b;
	}

	/**
	 * Valida la propiedad administrator user.
	 *
	 * @return retorna <code>true</code> si se cumple la condicion, de lo contario retorna <code>false</code> en administrator user
	 */
	public boolean isAdministratorUser()
	{
		return ib_administratorUser;
	}

	/**
	 * Modifica el valor de AdministratorUser.
	 *
	 * @param ib_administratorUser de ib administrator user
	 */
	public void setAdministratorUser(boolean ib_administratorUser)
	{
		this.ib_administratorUser = ib_administratorUser;
	}
}
