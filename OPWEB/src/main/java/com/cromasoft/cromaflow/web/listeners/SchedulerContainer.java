package com.cromasoft.cromaflow.web.listeners;


/**
 * Clase que contiene todos las propiedades de SchedulerContainer en donde se tiene todas las tareas programadas del sistema
 *
 * @author ccalderon
 */
public class SchedulerContainer
{
	/** Propiedad isc container. */
	private static SchedulerContainer isc_container;

	/** Propiedad ib iniciado. */
	private boolean ib_iniciado;

	/**
	 * Instancia un nuevo objeto scheduler container.
	 */
	private SchedulerContainer()
	{
	}

	/**
	 * Obtiene una instancia simpple' SchedulerContainer.
	 *
	 * @return una instancia unica de SchedulerContainer
	 */
	public static SchedulerContainer getInstance()
	{
		if(isc_container == null)
			isc_container = new SchedulerContainer();

		return isc_container;
	}

	/**
	 * Valida la propiedad iniciado.
	 *
	 * @return verdadero si se cumple la condicion, de lo contario retorna falso en iniciado
	 */
	public boolean isIniciado()
	{
		return ib_iniciado;
	}

	/**
	 * Inicializa  schedulers.
	 *
	 * @param ai_puerto correspondiente al valor del tipo de objeto int
	 */
	public void initSchedulers(int ai_puerto)
	{
		setIniciado(true);
	}

	/**
	 * Detiene schedulers.
	 */
	public void stopSchedulers()
	{
		setIniciado(false);
	}

	/**
	 * Modifica el valor de iniciado.
	 *
	 * @param ab_b asigna el valor a la propiedad iniciado
	 */
	private void setIniciado(boolean ab_b)
	{
		ib_iniciado = ab_b;
	}
}
