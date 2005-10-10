@echo off
setlocal

REM update drive and location for java home, for your machine
set JAVA_HOME=D:\JDKs\j2sdk1.4.2_08


REM updated drive and locations, for your machine where 'testarea' contains the 'plugins' directory
set ECLIPSE_ROOT=D:\\tests\\testarea

REM updated drive and locations, for your machine where 'testarea' contains the 'configuration' directory
set ROOT_ECLIPSE_TESTAREA=file:///D:/tests/testarea


set APP_ID=TestStartup.testApplication

REM following should not (have to) be changed
set PATH=%JAVA_HOME%\bin;%PATH%
set JAVACMD=%JAVA_HOME%\bin\java.exe


set RUN_CLASSPATH= -classpath %ECLIPSE_ROOT%\plugins\org.eclipse.osgi_3.1.0.jar

set CONFIG=-Dosgi.configuration.area=%ROOT_ECLIPSE_TESTAREA%\configuration\



set OSGI_SYSPATH=-Dosgi.syspath=D:\\tests\\testarea\\plugins


REM use one or the other (or both) of the following "java" lines to see first work correctly, or second one not
echo.
echo                    Note: with osgi debug console and "noShutdown=true" you will need to type 'exit' to proceed
echo                    and/or you may want to see resutls of    'getprop osgi.syspath'
echo.

REM case that works ok, with syspath explicitly overriding the one in 'config.ini'
%JAVA_HOME%\bin\java.exe %OSGI_SYSPATH% %RUN_CLASSPATH% %CONFIG% -Dosgi.console -Dosgi.noShutdown=true -Dosgi.debug org.eclipse.core.runtime.adaptor.EclipseStarter -application %APP_ID%

REM case that does not work   ...       and results in garbled "osgi.syspath" property, as can be seen using 'getprop osgi.syspath' in osgi console

%JAVACMD%  %RUN_CLASSPATH% %CONFIG% -Dosgi.console -Dosgi.noShutdown=true -Dosgi.debug org.eclipse.core.runtime.adaptor.EclipseStarter -debug -application %APP_ID%




endlocal
