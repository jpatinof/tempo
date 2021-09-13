package com.cromasoft.cromaflow.common.utils.webApplication;

/**
 * Clase singleton para aumentar hilos programados.
 *
 * @author Julian Vaca
 */
public class SchedulerThreadCounter implements java.io.Serializable
{
	
	/** Constante serialVersionUID. */
	private static final long             serialVersionUID = -6614709229895096507L;
	
	/** Propiedad istc intance. */
	private static SchedulerThreadCounter istc_intance;
	
	/** Propiedad ii counter. */
	private int                           ii_counter;
	
	/**
	 * Constructor privado de la clase.
	 */
	private SchedulerThreadCounter()
	{
		ii_counter                                         = 100;
	}

	/**
	 * Obtiene la instancia de la misma.
	 *
	 * @return instancia del objeto
	 */
	public static SchedulerThreadCounter getInstance()
	{
		if(istc_intance == null)
			istc_intance = new SchedulerThreadCounter();

		return istc_intance;
	}

	/**
	 * Incrementar el contador.
	 *
	 * @param ai_i Argumento tipo int para incrementar el contador
	 * @return ii_counter contador incrementado
	 */
	public int incrementCounter(int ai_i)
	{
		ii_counter += ai_i;

		return ii_counter;
	}
}
