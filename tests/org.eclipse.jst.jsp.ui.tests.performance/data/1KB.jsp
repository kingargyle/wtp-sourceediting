<%@ page contentType="text/html; charset=UTF-8" import="java.util.Hashtable,
                                                        java.util.List,
                                                        java.util.ArrayList,
                                                        java.net.URL,
                                                        javax.xml.parsers.*,
                                                        org.w3c.dom.*" %>

<jsp:useBean id="table" class="Hashtable" scope="session"/>
<jsp:useBean id="id" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list" class="ArrayList" scope="request"/>

<%!
public void test()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key = (String)table.get("key");
String value = (String)table.get("value");
String sessionId = id.toString();
boolean x = list.contains(key);
boolean y = list.contains(value);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key%>"><%=key%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value%>" name="<%=value%>" class="textareaenter" readonly><%=id.toString()%></textarea>
    </td>
  </tr>
</table>