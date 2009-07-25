Sam

export JAVA_HOME=/shared/webtools/apps/ibm-java-ppc-605

# reduce optimization level 
#export IBM_JAVA_OPTIONS=-Xjit:"{org/eclipse/wst/sse/tokenizerJitTest/IntStack;)V|org/eclipse/wst/sse/tokenizerJitTest/HTMLHeadTokenizer.*}(optLevel=scorching)"

# eclude methods
#export IBM_JAVA_OPTIONS=-Xjit:exclude="{com/ibm/ISecurityLocalObjectBaseL13Impl/CSIServerRI.send_exception(Lorg/eclipse/wst/sse/tokenizerJitTest/IntStack;)V|org/eclipse/wst/sse/tokenizerJitTest/HTMLHeadTokenizer.*}"

#capture log
#export IBM_JAVA_OPTIONS=-Xjit:verbose="{compileStart|compileEnd},vlog=filename.log,exclude={com/ibm/ISecurityLocalObjectBaseL13Impl/CSIServerRI.send_exception(Lorg/eclipse/wst/sse/tokenizerJitTest/IntStack;)V|org/eclipse/wst/sse/tokenizerJitTest/HTMLHeadTokenizer.*}"

${JAVA_HOME}/jre/bin/java -jar testTokenizerJIT.jar $HOME
