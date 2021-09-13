package com.cromasoft.cromaflow.constants;


/**
 * Interface que contiene todos las propiedades MotivoTramiteCommon.
 *
 * @author  Julián David Vaca Rodriguez
 * Fecha de Creacion: 20/02/2020
 */
public interface MotivoTramiteCommon  
{	
	// ETAPA 203
	public final long ALISTAMIENTO_DE_PRODUCTOS_203				= 100L;
	public final long DEVOLUCION_203							= 110L;
	public final long GENERACION_ORDEN_FABRICACION_203_120		= 120L;
	public final long GENERACION_ORDEN_FABRICACION_203			= 130L;
	
	// ETAPA 204
	public final long ALISTAMIENTO_DEPRODUCTOS_204				= 100L;
	public final long EN_ESPERA_DE_FABRICACION_PRODUCTOS_204	= 120L;

	// ETAPA 205
	public final long REVISION_TOMOGRTAFIA_APROBADO				= 100L;
	public final long REVISION_TOMOGRTAFIA_DEVOLVER				= 110L;
	
	// ETAPA 210
	public final long PLAN_DE_TRATAMIENTO_APROBADO_210			= 100L;
	public final long PLAN_DE_TRATAMIENTO_DEVOLVER_210			= 110L;
	public final long PENDIENTE_DISPOSITIVOS_ADICIONALES		= 150L;
	
	// ETAPA 212
	public final long DISPOSITIVOS_ADICIONALES_GENERACION_OP	= 100L;
	
	// ETAPA 215
	public final long ACTUALIZACION_STOCK						= 100L;
	
	//ETAPA 225
	public final long VALIDACION_DISENO_COMPLETO	            = 100L;
	
	// ETAPA 230
	public final long ORDEN_ACEPTACION_DISENO_APROBADO			= 100L;
	public final long ORDEN_ACEPTACION_DISENO_DEVOLVER			= 100L;
	
	// ETAPA 500 
	public final long ENTREGA_FINALIZADA						= 100L;
	
}