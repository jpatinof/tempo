package com.cromasoft.cromaflow.scheduler;

import com.b2bsg.common.logging.LoggerHandler;

import com.b2bsg.common.util.BooleanUtils;
import com.b2bsg.common.util.CollectionUtils;
import com.b2bsg.common.util.StringUtils;

import com.cromasoft.cromaflow.common.constants.quartz.Quartz;

import com.cromasoft.cromaflow.constants.EstadoCommon;

import com.cromasoft.cromaflow.model.par.JobDetalle;

import com.cromasoft.osteophoenix.ejb.session.stateless.reference.SchedulerRemote;

import org.quartz.CronScheduleBuilder;
import static org.quartz.JobBuilder.newJob;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.TriggerKey;

import org.quartz.impl.StdSchedulerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * Agendador de tareas autom�ticas QUARTZ
 *
 * @author Edgar Prieto
 *
 */
public abstract class OPClusterScheduler
{
	/** Constante clh_LOGGER. */
	private static final LoggerHandler clh_LOGGER = (LoggerHandler)com.cromasoft.cromaflow.common.utils.LoggingHelper
			.getLogger(OPClusterScheduler.class);

	/** EJB Remoto de referencia para consulta de parametros */
	protected SchedulerRemote isr_referenciaRemota;

	/** Schedulers en modo Cluster */
	private Map<String, Scheduler> imss_schedulers;

	/** Nombre del componente sobre el cual se ejecutan los cluster de agendadores */
	private String is_componente;

	/** @return Nombre del componente sobre el cual se ejecutan los cluster de agendadores */
	public String getComponente()
	{
		return is_componente;
	}

	/**
	 * Obtiene el identificador de una tarea automatica
	 *
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	public JobKey getJobKey(String as_cluster, String as_nombreJob)
	{
		String ls_nombreGrupo;

		ls_nombreGrupo = getNombreGrupo(as_cluster);

		return getJobKeyInterno(getSufijoNombreJob(ls_nombreGrupo, as_nombreJob), ls_nombreGrupo);
	}

	/**
	 * Elimina una tarea autom�tica del cluster al que pertenece
	 *
	 * @param as_cluster Nombre del cluster donde se debe bucaar la tarea a eliminar
	 * @param as_nombreJob Nombre de la tarea a eliminar
	 */
	public void borrarJob(String as_cluster, String as_nombreJob)
	{
		Scheduler ls_scheduler;

		ls_scheduler = ((imss_schedulers != null) && StringUtils.isValidString(as_cluster))
			? imss_schedulers.get(as_cluster) : null;

		if((ls_scheduler != null) && StringUtils.isValidString(as_nombreJob))
		{
			try
			{
				org.quartz.JobKey ljk_job;

				ljk_job = getJobKey(as_cluster, as_nombreJob);

				if(ljk_job != null)
				{
					ls_scheduler.pauseJob(ljk_job);
					ls_scheduler.deleteJob(ljk_job);
				}
			}
			catch(Exception le_e)
			{
				clh_LOGGER.error("stopJob", le_e);
			}
		}
	}

	/**
	 * Detiene los todos los hilos de ejecuci�n del cluster
	 *
	 * @param as_cluster Cluster que se debe detener
	*/
	public void detenerCluster(String ls_cluster)
	{
		SchedulerRemote lsr_reference;

		lsr_reference = getReferenciaRemota();

		if((lsr_reference != null) && StringUtils.isValidString(ls_cluster))
		{
			try
			{
				boolean   lb_eliminarJobs;
				Scheduler ls_scheduler;

				{
					String        ls_nodoEliminarJobs;
					StringBuilder lsb_eliminarJobs;

					lsb_eliminarJobs = new StringBuilder(Quartz.PREFIJO_ELIMINAR_JOBS);

					lsb_eliminarJobs.append(ls_cluster);

					ls_nodoEliminarJobs     = lsr_reference.obtenerCaracterConstante(lsb_eliminarJobs.toString());
					lb_eliminarJobs         = (ls_nodoEliminarJobs == null) || ls_nodoEliminarJobs.equals("*")
							|| BooleanUtils.getBooleanValue(ls_nodoEliminarJobs) || validarNodo(ls_nodoEliminarJobs);
				}

				ls_scheduler = (imss_schedulers != null) ? imss_schedulers.get(ls_cluster) : null;

				if(ls_scheduler != null)
				{
					if(lb_eliminarJobs)
						ls_scheduler.clear();

					ls_scheduler.shutdown(true);
				}
			}
			catch(Exception le_e)
			{
				clh_LOGGER.error("detenerCluster", le_e);
			}
		}
	}

	/** Detiene los Scheduler corriendo en el nodo */
	public void detenerScheduler()
	{
		if(CollectionUtils.isValidCollection(imss_schedulers))
		{
			for(String ls_cluster : imss_schedulers.keySet())
			{
				if(StringUtils.isValidString(ls_cluster))
					detenerCluster(ls_cluster);
			}
		}
	}

	/**
	 * Inicializa los Scheduleres seg�n la configuraci�n
	 *
	 * @param as_componente Componente al que pertenecen los clusters
	 */
	public void iniciarScheduler(String as_componente)
	{
		SchedulerRemote lsr_reference;

		lsr_reference = getReferenciaRemota();

		if((lsr_reference != null) && StringUtils.isValidString(as_componente))
		{
			String ls_componente;

			ls_componente = as_componente.toUpperCase();

			setComponente(ls_componente);

			try
			{
				String   ls_clusters;
				String[] lsa_clusters;

				ls_clusters      = lsr_reference.obtenerCaracterConstante(Quartz.CLUSTERS);
				lsa_clusters     = (ls_clusters != null) ? ls_clusters.split(",") : null;

				if(lsa_clusters != null)
				{
					for(int li_cluster = 0, li_clusters = lsa_clusters.length; li_cluster < li_clusters;
						    li_cluster++
					)
					{
						String ls_cluster;

						ls_cluster = lsa_clusters[li_cluster];

						if(StringUtils.isValidString(ls_cluster))
							iniciarCluster(ls_cluster);
					}
				}
			}
			catch(Exception le_e)
			{
				clh_LOGGER.error("initScheduler", le_e);
			}
		}
	}

	/** @retorn EJB Remoto de referencia para consulta de parametros */
	protected abstract SchedulerRemote getReferenciaRemota();

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	  * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, String as_cluster, String as_nodo,
	    Class ac_job, String as_nombreJob, boolean ab_agendarJob, String as_intervalo
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, null, as_intervalo, null, as_cluster, as_nodo, ac_job, as_nombreJob,
		    ab_agendarJob
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, String as_cluster, String as_nodo,
	    Class ac_job, String as_nombreJob, String as_intervalo
	)
	{
		iniciarJob(amsjd_jobs, amst_triggers, as_cluster, as_nodo, ac_job, as_nombreJob, false, as_intervalo);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    String as_intervalo, String as_cluster, String as_nodo, Class ac_job, String as_nombreJob, boolean ab_agendarJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, amss_jobData, as_intervalo, null, as_cluster, as_nodo, ac_job, as_nombreJob,
		    ab_agendarJob
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    String as_intervalo, String as_cluster, String as_nodo, Class ac_job, String as_nombreJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, amss_jobData, as_intervalo, as_cluster, as_nodo, ac_job, as_nombreJob, false
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Date ad_inicio, String as_cluster,
	    String as_nodo, Class ac_job, String as_nombreJob, boolean ab_agendarJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, null, null, ad_inicio, as_cluster, as_nodo, ac_job, as_nombreJob, ab_agendarJob
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Date ad_inicio, String as_cluster,
	    String as_nodo, Class ac_job, String as_nombreJob
	)
	{
		iniciarJob(amsjd_jobs, amst_triggers, ad_inicio, as_cluster, as_nodo, ac_job, as_nombreJob, false);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, String as_intervalo, Date ad_inicio,
	    String as_cluster, String as_nodo, Class ac_job, String as_nombreJob, boolean ab_agendarJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, null, as_intervalo, ad_inicio, as_cluster, as_nodo, ac_job, as_nombreJob,
		    ab_agendarJob
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, String as_intervalo, Date ad_inicio,
	    String as_cluster, String as_nodo, Class ac_job, String as_nombreJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, as_intervalo, ad_inicio, as_cluster, as_nodo, ac_job, as_nombreJob, false
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, String as_intervalo, String as_cluster,
	    String as_nodo, Class ac_job, String as_nombreJob, boolean ab_agendarJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, null, as_intervalo, null, as_cluster, as_nodo, ac_job, as_nombreJob,
		    ab_agendarJob
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, String as_intervalo, String as_cluster,
	    String as_nodo, Class ac_job, String as_nombreJob
	)
	{
		iniciarJob(amsjd_jobs, amst_triggers, as_intervalo, as_cluster, as_nodo, ac_job, as_nombreJob, false);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    String as_cluster, String as_nodo, Class ac_job, String as_nombreJob, boolean ab_agendarJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, amss_jobData, null, null, as_cluster, as_nodo, ac_job, as_nombreJob,
		    ab_agendarJob
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    String as_intervalo, Date ad_inicio, String as_cluster, String as_nodo, Class ac_job, String as_nombreJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, amss_jobData, as_intervalo, ad_inicio, as_cluster, as_nodo, ac_job, as_nombreJob,
		    false
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    String as_cluster, String as_nodo, Class ac_job, String as_nombreJob
	)
	{
		iniciarJob(amsjd_jobs, amst_triggers, amss_jobData, as_cluster, as_nodo, ac_job, as_nombreJob, false);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    Date ad_inicio, String as_cluster, String as_nodo, Class ac_job, String as_nombreJob, boolean ab_agendarJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, amss_jobData, null, ad_inicio, as_cluster, as_nodo, ac_job, as_nombreJob,
		    ab_agendarJob
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    Date ad_inicio, String as_cluster, String as_nodo, Class ac_job, String as_nombreJob
	)
	{
		iniciarJob(
		    amsjd_jobs, amst_triggers, amss_jobData, ad_inicio, as_cluster, as_nodo, ac_job, as_nombreJob, false
		);
	}

	/**
	 * Inicializaci�n de Job / Trigger para ejecuci�n de tarea automatica seg�n configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param amss_jobData Parametros que debe ser enviados a cada Job
	 * @param as_intervalo Intervalo de ejecuci�n del Job. Si no es suministrado el valor se toma de la parametrizaci�n
	 * @param ad_inicio Fecha de inicio de ejecuci�n del Job
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param ac_job Clase que implementa la tarea autom�tica a ejecutar
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 * @param ab_agendarJob Indica si la tarea automatica se debe iniciar
	 */
	protected void iniciarJob(
	    Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, Map<String, String> amss_jobData,
	    String as_intervalo, Date ad_inicio, String as_cluster, String as_nodo, Class ac_job, String as_nombreJob,
	    boolean ab_agendarJob
	)
	{
		SchedulerRemote lsr_reference;

		lsr_reference = getReferenciaRemota();

		if(lsr_reference != null)
		{
			JobKey     ljk_jobId;
			TriggerKey ltk_triggerId;

			ljk_jobId         = null;
			ltk_triggerId     = null;

			{
				String ls_nombreGrupo;
				String ls_sufijoNombreJob;

				ls_nombreGrupo         = getNombreGrupo(as_cluster);
				ls_sufijoNombreJob     = getSufijoNombreJob(ls_nombreGrupo, as_nombreJob);

				if(StringUtils.isValidString(ls_sufijoNombreJob))
				{
					ljk_jobId = getJobKeyInterno(ls_sufijoNombreJob, ls_nombreGrupo);

					{
						StringBuilder lsb_triggerId;

						lsb_triggerId = new StringBuilder(Quartz.PREFIJO_TRIGGER);

						lsb_triggerId.append(ls_sufijoNombreJob);

						ltk_triggerId = new TriggerKey(lsb_triggerId.toString(), ls_nombreGrupo);
					}
				}
			}

			try
			{
				String ls_intervalo;

				ls_intervalo = StringUtils.getString(as_intervalo);

				if(!StringUtils.isValidString(ls_intervalo))
				{
					String     ls_nombreJobDb;
					JobDetalle ljd_job;

					ls_nombreJobDb     = as_nombreJob.replaceAll(Quartz.PREFIJO_JOB, new String())
							                             .replaceAll(Quartz.SUFIJO_CLUSTER, new String());
					ljd_job            = lsr_reference.obtenerJobPorNombre(ls_nombreJobDb);

					if(ljd_job != null)
						ls_intervalo = ljd_job.getIntervlado();
				}

				if(StringUtils.isValidString(ls_intervalo))
				{
					if(ljk_jobId != null)
					{
						org.quartz.JobBuilder ljb_job;

						ljb_job = newJob(ac_job).withIdentity(ljk_jobId).usingJobData(OPJob.DATA_IP_REMOTA, as_nodo);

						if(CollectionUtils.isValidCollection(amss_jobData))
						{
							Set<String> lss_jobDataKeys;

							lss_jobDataKeys = amss_jobData.keySet();

							for(String ls_jobDataKey : lss_jobDataKeys)
							{
								if(StringUtils.isValidString(ls_jobDataKey))
								{
									String ls_jobDataValue;

									ls_jobDataValue = amss_jobData.get(ls_jobDataKey);

									if(StringUtils.isValidString(ls_jobDataValue))
										ljb_job = ljb_job.usingJobData(ls_jobDataKey, ls_jobDataValue);
								}
							}
						}

						amsjd_jobs.put(as_nombreJob, ljb_job.build());
					}

					if(ltk_triggerId != null)
					{
						org.quartz.TriggerBuilder ltb_trigger;

						ltb_trigger = newTrigger().withIdentity(ltk_triggerId)
								              .withSchedule(CronScheduleBuilder.cronSchedule(ls_intervalo));

						if(ad_inicio != null)
							ltb_trigger = ltb_trigger.startAt(ad_inicio);

						amst_triggers.put(as_nombreJob, ltb_trigger.build());
					}

					if(ab_agendarJob)
						agendarJobs(amsjd_jobs, amst_triggers, as_cluster);
				}
			}
			catch(Exception le_e)
			{
				clh_LOGGER.error("initJob", le_e);
			}
		}
	}

	/**
	 * Inicializaci�n de jobs que deben ejecutarse en un nodo
	 *
	 * @param acjd_jobs Listado de IDs de JOBs que deben inicializarse
	 * @param amsjd_jobs Contenedor de Jobs donde se alojar� el Job inicializado
	 * @param amst_triggers Contenedor de Triggers donde se alojar� el Trigger inicializado
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 * @param
	 * @throws ClassNotFoundException
	 */
	protected abstract void iniciarJobs(
	    Collection<JobDetalle> acjd_jobs, Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers,
	    String as_cluster, String as_nodo
	)
	    throws ClassNotFoundException;

	/**
	 * Ajusta el nombre del componente sobre el cual se ejecutan los cluster de agendadores
	 * @param as_s Nombre del componente sobre el cual se ejecutan los cluster de agendadores
	 */
	private void setComponente(String as_s)
	{
		is_componente = StringUtils.getString(as_s);
	}

	/**
	 * Obtiene el identificador de una tarea automatica
	 *
	 * @param as_sufijoNombreJob Sufijo del nombre de una tarea automatica
	 * @param as_nombreGrupo Nombre del grupo en el cual se registrar� una tarea automatica
	 */
	private JobKey getJobKeyInterno(String as_sufijoNombreJob, String as_nombreGrupo)
	{
		JobKey ljk_key;

		if(StringUtils.isValidString(as_sufijoNombreJob) && StringUtils.isValidString(as_nombreGrupo))
		{
			StringBuilder lsb_jobId;

			lsb_jobId = new StringBuilder(Quartz.PREFIJO_JOB);

			lsb_jobId.append(as_sufijoNombreJob);

			ljk_key = new JobKey(lsb_jobId.toString(), as_nombreGrupo);
		}
		else
			ljk_key = null;

		return ljk_key;
	}

	/**
	 * Obtiene el nombre del grupo en el cual se registrar� una tarea automatica
	 *
	 * @param as_cluster Cluster que se debe inicializar
	 */
	private String getNombreGrupo(String as_cluster)
	{
		String ls_componente;
		String ls_nombreGrupo;

		ls_componente = getComponente();

		if(StringUtils.isValidString(ls_componente))
		{
			StringBuilder lsb_nombreGrupo;

			lsb_nombreGrupo = new StringBuilder(ls_componente);

			lsb_nombreGrupo.append("_");
			lsb_nombreGrupo.append(as_cluster);

			ls_nombreGrupo = lsb_nombreGrupo.toString();
		}
		else
			ls_nombreGrupo = null;

		return ls_nombreGrupo;
	}

	/**
	 * Obtiene el sufijo del nombre de una tarea automatica
	 *
	 * @param as_nombreGrupo Nombre del grupo en el cual se registrar� una tarea automatica
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	private String getSufijoNombreJob(String as_nombreGrupo, String as_nombreJob)
	{
		String ls_sufijoNombreJob;

		if(StringUtils.isValidString(as_nombreGrupo) && StringUtils.isValidString(as_nombreJob))
		{
			StringBuilder lsb_sufijoNombreJob;

			lsb_sufijoNombreJob = new StringBuilder(as_nombreGrupo);

			lsb_sufijoNombreJob.append("_");
			lsb_sufijoNombreJob.append(as_nombreJob);

			ls_sufijoNombreJob = lsb_sufijoNombreJob.toString();
		}
		else
			ls_sufijoNombreJob = null;

		return ls_sufijoNombreJob;
	}

	/**
	 * Inicializa los schedulers en modo cluster seg�n la configuraci�n
	 *
	 * @param amsjd_jobs Contenedor de Jobs donde a iniciar
	 * @param amst_triggers Contenedor de Triggers donde a iniciar
	 * @param as_cluster Cluster donde se agendaran las tareas automaticas
	 */
	private void agendarJobs(Map<String, JobDetail> amsjd_jobs, Map<String, Trigger> amst_triggers, String as_cluster)
	    throws Exception
	{
		if(
		    CollectionUtils.isValidCollection(amsjd_jobs) && CollectionUtils.isValidCollection(amst_triggers)
			    && StringUtils.isValidString(as_cluster)
		)
		{
			Scheduler ls_scheduler;

			ls_scheduler = obtenerScheduler(as_cluster, amsjd_jobs.size());

			if(ls_scheduler != null)
			{
				Set<String> lss_jobIds;

				lss_jobIds = amsjd_jobs.keySet();

				if(lss_jobIds != null)
				{
					for(String ls_jobId : lss_jobIds)
					{
						if(StringUtils.isValidString(ls_jobId))
						{
							JobDetail ljd_job;
							Trigger   lt_trigger;

							ljd_job        = amsjd_jobs.get(ls_jobId);
							lt_trigger     = amst_triggers.get(ls_jobId);

							if(
							    (ljd_job != null) && (lt_trigger != null)
								    && !ls_scheduler.checkExists(ljd_job.getKey())
								    && !ls_scheduler.checkExists(lt_trigger.getKey())
							)
							{
								ls_scheduler.scheduleJob(ljd_job, lt_trigger);

								desbloquearInicioJob(ls_jobId);
							}
						}
					}
				}

				if(!ls_scheduler.isStarted())
					ls_scheduler.start();
			}
		}
	}

	/**
	 * Actualiza la variable de bloqueo del Job para permitir su ejecuci�n
	 *
	 * @param as_nombreJob Identificador de la tara automatica a ejecutar
	 */
	private void desbloquearInicioJob(String as_nombreJob)
	{
		SchedulerRemote lsr_reference;

		lsr_reference = getReferenciaRemota();

		if((lsr_reference != null) && StringUtils.isValidString(as_nombreJob))
		{
			try
			{
				JobDetalle ljd_job;

				ljd_job = new JobDetalle();

				ljd_job.setIdUsuarioModificacion(as_nombreJob);

				as_nombreJob     = as_nombreJob.replace(Quartz.PREFIJO_JOB, "");
				as_nombreJob     = as_nombreJob.replace(Quartz.SUFIJO_CLUSTER, "");

				ljd_job.setNombre(as_nombreJob);
				ljd_job.setBloqueo(EstadoCommon.N);

				lsr_reference.bloquearDesbloquearJob(ljd_job);
			}
			catch(Exception le_e)
			{
				clh_LOGGER.error("desbloquearInicioJob", le_e);
			}
		}
	}

	/**
	 * Inicializa los schedulers en modo cluster seg�n la configuraci�n
	 *
	 * @param as_cluster Cluster que se debe inicializar
	 */
	private void iniciarCluster(String as_cluster)
	    throws Exception
	{
		SchedulerRemote lsr_reference;
		lsr_reference = getReferenciaRemota();

		if((lsr_reference != null) && StringUtils.isValidString(as_cluster))
		{
			String   ls_nodos;
			String[] lsa_nodos;

			{
				StringBuilder lsb_idConstante;

				lsb_idConstante = new StringBuilder(Quartz.PREFIJO_NODOS);

				lsb_idConstante.append(as_cluster);

				ls_nodos = lsr_reference.obtenerCaracterConstante(lsb_idConstante.toString());
			}

			lsa_nodos = (ls_nodos != null) ? ls_nodos.split(",") : null;

			if(lsa_nodos != null)
			{
				for(int li_nodo = 0, li_nodos = lsa_nodos.length; li_nodo < li_nodos; li_nodo++)
				{
					String ls_nodo;

					ls_nodo = lsa_nodos[li_nodo];

					if(StringUtils.isValidString(ls_nodo))
						iniciarNodo(as_cluster, ls_nodo);
				}
			}
			else
				iniciarNodo(as_cluster, null);
		}
	}

	/**
	 * Inicializa los schedulers en modo cluster seg�n la configuraci�n
	 *
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 */
	private void iniciarNodo(String as_cluster, String as_nodo)
	    throws Exception
	{
		SchedulerRemote lsr_reference;

		lsr_reference = getReferenciaRemota();

		if(
		    (lsr_reference != null) && StringUtils.isValidString(as_cluster)
			    && (!StringUtils.isValidString(as_nodo) || as_nodo.equals("*") || validarNodo(as_nodo))
		)
		{
			Collection<JobDetalle> lcjd_jobs;
			Map<String, JobDetail> lmsjd_jobs;
			Map<String, Trigger>   lmst_triggers;
			String                 ls_nodoInicioJobs;

			lcjd_jobs = lsr_reference.obtenerJobsPorCluster(as_cluster);

			{
				StringBuilder lsb_nodoInicioJobs;

				lsb_nodoInicioJobs = new StringBuilder(Quartz.PREFIJO_NODO_INICIO);

				lsb_nodoInicioJobs.append(as_cluster);

				ls_nodoInicioJobs = lsr_reference.obtenerCaracterConstante(lsb_nodoInicioJobs.toString());
			}

			lmsjd_jobs        = new HashMap<String, JobDetail>();
			lmst_triggers     = new HashMap<String, Trigger>();

			iniciarJobs(lcjd_jobs, lmsjd_jobs, lmst_triggers, as_cluster, as_nodo);

			if((ls_nodoInicioJobs == null) || ls_nodoInicioJobs.equals("*") || ls_nodoInicioJobs.equals(as_nodo))
				agendarJobs(lmsjd_jobs, lmst_triggers, as_cluster);
		}
	}

	/**
	 * Crea un objeto Scheduler de Quartz para soporte de Cluster en WebLogic
	 *
	 * @param as_cluster Cluster que se debe inicializar
	 * @param ai_cantidadJobs Cantidad de Jobs que se ejecutar�n dentro del clustes
	 */
	private Scheduler obtenerScheduler(String as_cluster, int ai_cantidadJobs)
	    throws Exception
	{
		Scheduler       ls_scheduler;
		SchedulerRemote lsr_reference;

		ls_scheduler      = null;
		lsr_reference     = getReferenciaRemota();

		if((lsr_reference != null) && StringUtils.isValidString(as_cluster))
		{
			String ls_nombreCluster;

			{
				StringBuilder lsb_nombreCluster;

				lsb_nombreCluster = new StringBuilder(Quartz.PREFIJO_CLUSTER);

				lsb_nombreCluster.append(getComponente());
				lsb_nombreCluster.append("_");
				lsb_nombreCluster.append(as_cluster);

				ls_nombreCluster     = lsb_nombreCluster.toString();
				ls_scheduler         = null;
			}

			if(imss_schedulers == null)
				imss_schedulers = new HashMap<String, Scheduler>();
			else
				ls_scheduler = imss_schedulers.get(as_cluster);

			if(ls_scheduler == null)
			{
				String     ls_threadCount;
				Properties lp_p;

				ls_threadCount     = Integer.toString((ai_cantidadJobs > 5) ? ((ai_cantidadJobs * 2) + 1) : 5);
				lp_p               = new Properties();

				lp_p.put(
				    "org.quartz.dataSource.opQuartzDS.jndiURL",
				    lsr_reference.obtenerCaracterConstante(Quartz.DATASOURCE)
				);

				lp_p.put("org.quartz.scheduler.instanceName", ls_nombreCluster);
				lp_p.put("org.quartz.threadPool.threadCount", ls_threadCount);

				lp_p.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
				lp_p.put("org.quartz.jobStore.dataSource", "opQuartzDS");
				lp_p.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
				lp_p.put("org.quartz.jobStore.isClustered", "true");
				lp_p.put("org.quartz.jobStore.misfireThreshold", "60000");
				lp_p.put("org.quartz.jobStore.tablePrefix", "qrtz_");
				lp_p.put("org.quartz.jobStore.useProperties", "false");
				lp_p.put("org.quartz.scheduler.instanceId", "AUTO");
				lp_p.put("org.quartz.scheduler.skipUpdateCheck", "true");
				lp_p.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
				lp_p.put("org.quartz.threadPool.threadPriority", "5");

				ls_scheduler = new StdSchedulerFactory(lp_p).getScheduler();

				imss_schedulers.put(as_cluster, ls_scheduler);
			}
		}

		return ls_scheduler;
	}

	/**
	 * Valida si el nodo actual coincide con el nodo parametrizado en el cluster
	 *
	 * @param as_cluster Cluster que se debe inicializar
	 * @param as_nodo Nodo que se est� inicializando dentro del cluster
	 */
	private boolean validarNodo(String as_nodo)
	    throws Exception
	{
		boolean lb_nodoValido;

		lb_nodoValido = false;

		if(StringUtils.isValidString(as_nodo))
		{
			Enumeration<NetworkInterface> leni_networkInterfaces;

			leni_networkInterfaces = NetworkInterface.getNetworkInterfaces();

			while(!lb_nodoValido && leni_networkInterfaces.hasMoreElements())
			{
				Enumeration<InetAddress> leia_addresses;
				NetworkInterface         lni_networkInterface;

				lni_networkInterface     = leni_networkInterfaces.nextElement();
				leia_addresses           = lni_networkInterface.getInetAddresses();

				while(!lb_nodoValido && leia_addresses.hasMoreElements())
				{
					InetAddress lia_ip;

					lia_ip = leia_addresses.nextElement();

					if(lia_ip != null)
						lb_nodoValido = as_nodo.equals(lia_ip.getHostAddress());
				}
			}
		}

		return lb_nodoValido;
	}
}
