package com.cromasoft.osteophoenix.ejb.session.stateless;

import org.perf4j.StopWatch;

import java.util.Collection;


/**
 * Clase que contiene todos las propiedades Logger.
 *
 * @author Julian Vaca
 */
public class Logger
{
	/**
	 * Retorna el valor de new stop watch.
	 *
	 * @return el valor de new stop watch
	 */
	public static StopWatch getNewStopWatch()
	{
		return new org.perf4j.log4j.Log4JStopWatch();
	}

	/**
	 * Log.
	 *
	 * @param asw_watch de asw watch
	 * @param as_class de as class
	 * @param as_method de as method
	 * @param as_userId de as user id
	 * @param as_localIp de as local ip
	 * @param as_remoteIp de as remote ip
	 * @param ac_return de ac return
	 */
	public static void log(
	    StopWatch asw_watch, String as_class, String as_method, String as_userId, String as_localIp, String as_remoteIp,
	    Collection ac_return
	)
	{
		StringBuilder lsb_message;
		StringBuilder lsb_tag;

		lsb_message     = new StringBuilder();
		lsb_tag         = new StringBuilder();

		lsb_message.append(as_userId);
		lsb_message.append("\t");
		lsb_message.append(as_localIp);
		lsb_message.append("\t");
		lsb_message.append(as_remoteIp);
		lsb_message.append("\t");
		lsb_message.append((ac_return != null) ? ac_return.size() : (-1));

		lsb_tag.append(as_class);
		lsb_tag.append(".");
		lsb_tag.append(as_method);

		asw_watch.stop(lsb_tag.toString(), lsb_message.toString());
	}
}
