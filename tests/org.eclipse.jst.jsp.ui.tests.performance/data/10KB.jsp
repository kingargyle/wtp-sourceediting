<%@ page contentType="text/html; charset=UTF-8" import="java.util.Hashtable,
                                                        java.util.List,
                                                        java.util.ArrayList,
                                                        java.net.URL,
                                                        javax.xml.parsers.*,
                                                        org.w3c.dom.*" %>

<jsp:useBean id="table0" class="Hashtable" scope="session"/>
<jsp:useBean id="id0" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list0" class="ArrayList" scope="request"/>

<%!
public void test0()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key0 = (String)table0.get("key");
String value0 = (String)table0.get("value");
String sessionId0 = id0.toString();
boolean x0 = list0.contains(key0);
boolean y0 = list0.contains(value0);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key0%>"><%=key0%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x0 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value0%>" name="<%=value0%>" class="textareaenter" readonly><%=id0.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table1" class="Hashtable" scope="session"/>
<jsp:useBean id="id1" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list1" class="ArrayList" scope="request"/>

<%!
public void test1()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key1 = (String)table1.get("key");
String value1 = (String)table1.get("value");
String sessionId1 = id1.toString();
boolean x1 = list1.contains(key1);
boolean y1 = list1.contains(value1);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key1%>"><%=key1%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x1 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value1%>" name="<%=value1%>" class="textareaenter" readonly><%=id1.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table2" class="Hashtable" scope="session"/>
<jsp:useBean id="id2" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list2" class="ArrayList" scope="request"/>

<%!
public void test2()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key2 = (String)table2.get("key");
String value2 = (String)table2.get("value");
String sessionId2 = id2.toString();
boolean x2 = list2.contains(key2);
boolean y2 = list2.contains(value2);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key2%>"><%=key2%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x2 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value2%>" name="<%=value2%>" class="textareaenter" readonly><%=id2.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table3" class="Hashtable" scope="session"/>
<jsp:useBean id="id3" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list3" class="ArrayList" scope="request"/>

<%!
public void test3()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key3 = (String)table3.get("key");
String value3 = (String)table3.get("value");
String sessionId3 = id3.toString();
boolean x3 = list3.contains(key3);
boolean y3 = list3.contains(value3);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key3%>"><%=key3%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x3 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value3%>" name="<%=value3%>" class="textareaenter" readonly><%=id3.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table4" class="Hashtable" scope="session"/>
<jsp:useBean id="id4" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list4" class="ArrayList" scope="request"/>

<%!
public void test4()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key4 = (String)table4.get("key");
String value4 = (String)table4.get("value");
String sessionId4 = id4.toString();
boolean x4 = list4.contains(key4);
boolean y4 = list4.contains(value4);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key4%>"><%=key4%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x4 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value4%>" name="<%=value4%>" class="textareaenter" readonly><%=id4.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table5" class="Hashtable" scope="session"/>
<jsp:useBean id="id5" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list5" class="ArrayList" scope="request"/>

<%!
public void test5()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key5 = (String)table5.get("key");
String value5 = (String)table5.get("value");
String sessionId5 = id5.toString();
boolean x5 = list5.contains(key5);
boolean y5 = list5.contains(value5);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key5%>"><%=key5%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x5 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value5%>" name="<%=value5%>" class="textareaenter" readonly><%=id5.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table6" class="Hashtable" scope="session"/>
<jsp:useBean id="id6" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list6" class="ArrayList" scope="request"/>

<%!
public void test6()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key6 = (String)table6.get("key");
String value6 = (String)table6.get("value");
String sessionId6 = id6.toString();
boolean x6 = list6.contains(key6);
boolean y6 = list6.contains(value6);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key6%>"><%=key6%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x6 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value6%>" name="<%=value6%>" class="textareaenter" readonly><%=id6.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table7" class="Hashtable" scope="session"/>
<jsp:useBean id="id7" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list7" class="ArrayList" scope="request"/>

<%!
public void test7()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key7 = (String)table7.get("key");
String value7 = (String)table7.get("value");
String sessionId7 = id7.toString();
boolean x7 = list7.contains(key7);
boolean y7 = list7.contains(value7);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key7%>"><%=key7%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x7 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value7%>" name="<%=value7%>" class="textareaenter" readonly><%=id7.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table8" class="Hashtable" scope="session"/>
<jsp:useBean id="id8" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list8" class="ArrayList" scope="request"/>

<%!
public void test8()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key8 = (String)table8.get("key");
String value8 = (String)table8.get("value");
String sessionId8 = id8.toString();
boolean x8 = list8.contains(key8);
boolean y8 = list8.contains(value8);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key8%>"><%=key8%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x8 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value8%>" name="<%=value8%>" class="textareaenter" readonly><%=id8.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table9" class="Hashtable" scope="session"/>
<jsp:useBean id="id9" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list9" class="ArrayList" scope="request"/>

<%!
public void test9()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key9 = (String)table9.get("key");
String value9 = (String)table9.get("value");
String sessionId9 = id9.toString();
boolean x9 = list9.contains(key9);
boolean y9 = list9.contains(value9);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key9%>"><%=key9%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x9 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value9%>" name="<%=value9%>" class="textareaenter" readonly><%=id9.toString()%></textarea>
    </td>
  </tr>
</table>
