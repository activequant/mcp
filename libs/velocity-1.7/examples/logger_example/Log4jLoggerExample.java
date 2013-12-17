/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.Log4JLogChute;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

/**
 *  Simple example class to show how to use an existing Log4j Logger
 *  as the Velocity logging target.
 *
 * @author <a href="mailto:geirm@apache.org">Geir Magnusson Jr.</a>
 * @version $Id: Log4jLoggerExample.java 463298 2006-10-12 16:10:32Z henning $
 */
public class Log4jLoggerExample
{
    public static String LOGGER_NAME = "velexample";

    public static void main( String args[] )
        throws Exception
    {
        /*
         *  configure log4j to log to console
         */
        BasicConfigurator.configure();

        Logger log = Logger.getLogger(LOGGER_NAME);

        log.info("Hello from Log4jLoggerExample - ready to start velocity");

        /*
         *  now create a new VelocityEngine instance, and
         *  configure it to use the logger
         */
        VelocityEngine ve = new VelocityEngine();

        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                       "org.apache.velocity.runtime.log.Log4JLogChute");

        ve.setProperty(Log4JLogChute.RUNTIME_LOG_LOG4J_LOGGER, LOGGER_NAME);

        ve.init();

        log.info("this should follow the initialization output from velocity");
    }
}

