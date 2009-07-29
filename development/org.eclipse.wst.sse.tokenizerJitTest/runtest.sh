# Sample script to invoke the test code
# for bug 284441 (Error: could not match input in HTML Tokenizer)
# https://bugs.eclipse.org/bugs/show_bug.cgi?id=284441




REM change appropriate to your system's Java install
export JAVA_HOME=/shared/webtools/apps/ibm-java-ppc-605

REM  if no options below are uncommented, the test jar should exhibit the 
REM  error condition after a few thousand scans (usually 2 thousand, sometimes I've seen 20 thousand required). 
REM  The test is coded to scan up to only 50000 attemps. 

REM quicker and more consistently see error with immedate JIT compile
export IBM_JAVA_OPTIONS=-Xjit:count=0

# eclude methods
export IBM_JAVA_OPTIONS=-Xjit:exclude="{org/eclipse/wst/sse/tokenizerJitTest/HTMLHeadTokenizer.primGetNextToken()Ljava/lang/String;}"

#capture log
#export IBM_JAVA_OPTIONS=-Xjit:verbose="{compileStart|compileEnd}",vlog=filename.log,count=0

${JAVA_HOME}/jre/bin/java -jar testTokenizerJIT.jar
