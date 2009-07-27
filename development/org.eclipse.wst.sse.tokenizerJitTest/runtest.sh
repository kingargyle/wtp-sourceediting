# Sample script to invoke the test code
# for bug 284441 (Error: could not match input in HTML Tokenizer)
# https://bugs.eclipse.org/bugs/show_bug.cgi?id=284441

export JAVA_HOME=/shared/webtools/apps/ibm-java-ppc-605

# if no options below are uncommented, the test jar should exhibit the 
# error condition after a few thousand files. 
# You can use almost any directory, as long as there are thousands of files, 
# that can be accessed for read. 

# reduce optimization level for bug 284441
#export IBM_JAVA_OPTIONS=-Xjit:"{org/eclipse/wst/sse/tokenizerJitTest/HTMLHeadTokenizer.*}(optLevel=scorching)"

# eclude methods
export IBM_JAVA_OPTIONS=-Xjit:exclude="{org/eclipse/wst/sse/tokenizerJitTest/HTMLHeadTokenizer.primGetNextToken()Ljava/lang/String;}"

# exclude methods for "real" tokenizer
#-Xjit:verbose={compileStart|compileEnd},vlog=filename.log,count=0,exclude={org/eclipse/wst/html/core/internal/contenttype/HTMLHeadTokenizer.primGetNextToken()Ljava/lang/String;}

#capture log
#export IBM_JAVA_OPTIONS=-Xjit:verbose="{compileStart|compileEnd},vlog=filename.log,exclude={-Xjit:verbose={compileStart|compileEnd},vlog=filename.log,count=0,exclude="{org/eclipse/wst/html/core/internal/contenttype/HTMLHeadTokenizer.primGetNextToken()Ljava/lang/String;}"

${JAVA_HOME}/jre/bin/java -jar testTokenizerJIT.jar $HOME
