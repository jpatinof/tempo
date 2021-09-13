package com.cromasoft.cromaflow.dao;

import com.b2bsg.common.exception.B2BException;


/**
 * Interface que contiene el comportamiento generico para cualquier tipo de objeto que necesite realizar consultas
 * por el ID de las tablas, insertar y/o actualizar en las mismas
 *
 * @author Julian Vaca
 * @param <T> de tipo generico
 */
public interface IBase<T>
{
	/**
	 * Retorna el valor del objeto de tipo consultado por ID
	 *
	 * @param at_param correspondiente al valor del tipo de objeto T
	 * @return devuelve el valor del objeto t
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	T findById(T at_param)
	    throws B2BException;

	/**
	 * Inserta o Actualiza en la base de datos el tipo que se recibe por parametro
	 *
	 * @param at_param Objecto a insertar o actualizar
	 * @param ab_query <b>verdadero</b> para insertar un nuevo registro en la base de datos, <b>falso</b> para actualizar 
	 * el registro en la base de datos.
	 * @throws B2BException Señala que se ha producido una excepción
	 */
	void insertOrUpdate(T at_param, boolean ab_query)
	    throws B2BException;
}
