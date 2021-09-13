package com.b2bsg.common.logging;


/**
 * A factory for creating LoggerHandler objects.
 * @author Edgar Prieto
 */
public class LoggerHandlerFactory implements org.apache.log4j.spi.LoggerFactory
{
	/**
	 * Instancia un nuevo objeto logger handler factory.
	 */
	public LoggerHandlerFactory()
	{
	}

	/** {@inheritdoc} */
	public org.apache.log4j.Logger makeNewLoggerInstance(String name)
	{
		return new LoggerHandler(name);
	}
}
