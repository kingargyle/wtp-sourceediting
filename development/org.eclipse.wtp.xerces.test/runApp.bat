REM Remember to use "set local", especially when experimenting, or else a setting my linger in subsequent run. 
SETLOCAL

REM change appropriate to your system's Java install
SET JAVA_HOME=D:\JDKs\ibm-java-win-605

REM This invoction, with no arguments, uses the direct instantiation method, plus the failing service registry method.
    eclipsec -debug -consolelog -nosplash -data ./testworkspace -application org.eclipse.wtp.xerces.test.testxmlcontenttype -vm %JAVA_HOME%/jre/bin/java 

REM uncomment following line to show it works ok if TCCL is used
REM eclipsec -debug -consolelog -nosplash -data ./testworkspace -application org.eclipse.wtp.xerces.test.testxmlcontenttype -vm %JAVA_HOME%/jre/bin/java -useTCCL

pause 

ENDLOCAL
