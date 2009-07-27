REM  Sample script to invoke the test code
REM  for bug 284441 (Error: could not match input in HTML Tokenizer)
REM  https://bugs.eclipse.org/bugs/show_bug.cgi?id=284441

REM Remember to use "set local", especially when experimenting, or else a setting my linger in subsequent run. 
SETLOCAL

SET JAVA_HOME=D:\JDKs\ibm-java-win-605

REM  if no options below are uncommented, the test jar should exhibit the 
REM  error condition after a few thousand files (usually 2 thousand, sometimes I've seen 20 thousand required). 
REM  You can use almost any directory, as long as there are thousands of files, 
REM  that can be accessed for read. The default is simply to use the "top" directory, '/'.
REM  The test is hard coded to scan up to only 50000. 

REM  eclude method
REM SET IBM_JAVA_OPTIONS=-Xjit:exclude="{org/eclipse/wst/sse/tokenizerJitTest/HTMLHeadTokenizer.primGetNextToken()Ljava/lang/String;}"

REM capture log
REM SET IBM_JAVA_OPTIONS=-Xjit:verbose="{compileStart|compileEnd}",vlog=filename.log,count=0

%JAVA_HOME%/jre/bin/java -jar testJITBug.jar /

ENDLOCAL
