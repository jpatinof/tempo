package com.cromasoft.cromaflow.common.constants.quartz;


/**
 * Interface para contantes de gesition de tareas automáticas.
 *
 * @author Edgar Prieto
 */
public interface Quartz
{
	/** Clusters de alta disponibilidad de Quartz validos en el componente */
	public final String CLUSTERS = "QUARTZ_HA_CLUSTERS";

	/** Id de constante para el datasource con la configuración de acceso a la base de datos de Quartz */
	public final String DATASOURCE = "QUARTZ_HA_DATASOURCE";

	/** Prefijo de nombre de cluster */
	public final String PREFIJO_CLUSTER = "CLUSTER_";

	/** Prefijo de contante para indicar si se deben eliminar las tareas automaticas al detener cualuier nodo del cluster */
	public final String PREFIJO_ELIMINAR_JOBS = "QUARTZ_HA_NODO_ELIMINAR_JOBS_";

	/** Prefijo para todas las contantes de tareas automaticas */
	public final String PREFIJO_JOB = "JOB_";

	/** Prefijo de contante para los nodos asociados a un cluster de alta disponibilidad de Quartz */
	public final String PREFIJO_NODOS = "QUARTZ_HA_NODOS_";

	/** Prefijo de contante para el nodo que debe iniciar la ejecutión de tareas automaticas en el cluster */
	public final String PREFIJO_NODO_INICIO = "QUARTZ_HA_NODO_INICIO_CLUSTER_";

	/** Prefijo todas las contantes de triggers de tareas automaticas */
	public final String PREFIJO_TRIGGER = "TRIGGER_";

	/** Sufijo de contantes para identificar el estado de bloqueo de una tarea automática */
	public final String SUFIJO_BLOQUEO = "_BLOQUEO";

	/** Sufijo de contantes para identificar el nodo sobre el cual debe correr una tarea automática */
	public final String SUFIJO_CLUSTER = "_CLUSTER";

	/** Sufijo de contantes para identificar el intervalo de ejecución de una tarea automática */
	public final String SUFIJO_INTERVALO = "_INTERVALO";
}
