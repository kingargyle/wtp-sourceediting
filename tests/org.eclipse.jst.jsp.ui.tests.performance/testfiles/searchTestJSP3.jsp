<html>
	<head>
		<title>searchTestJSP3 - beans</title>
	</head>
	
	<body>
	
		<!-- define some beans -->
		<!-- default package bean -->
		<jsp:useBean id="judo1" class="Jellybean"></jsp:useBean>
		
		<!-- com.ibm.test package bean -->
		<jsp:useBean id="judo2" class="com.ibm.test.Jellybean2" />
	
	
		<%
			// reference some beans
			judo1.foo();
			String llcoolj = judo2.getFuBu();
			Jellybean judo3 = new Jellybean();
		%>
		
		<p>
			filler to divide the page into more partitions
		</p>
		
		<%
			// the alignment is funky on purpose
			judo3.foo();
				judo3.boo();
		judo2.getFuBu();
				
				judo1.foo();
					judo1.boo();
	%>

	</body>
	
</html>