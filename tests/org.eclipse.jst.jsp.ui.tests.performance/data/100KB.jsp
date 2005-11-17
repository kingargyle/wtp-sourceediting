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
<jsp:useBean id="table10" class="Hashtable" scope="session"/>
<jsp:useBean id="id10" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list10" class="ArrayList" scope="request"/>

<%!
public void test10()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key10 = (String)table10.get("key");
String value10 = (String)table10.get("value");
String sessionId10 = id10.toString();
boolean x10 = list10.contains(key10);
boolean y10 = list10.contains(value10);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key10%>"><%=key10%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x10 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value10%>" name="<%=value10%>" class="textareaenter" readonly><%=id10.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table11" class="Hashtable" scope="session"/>
<jsp:useBean id="id11" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list11" class="ArrayList" scope="request"/>

<%!
public void test11()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key11 = (String)table11.get("key");
String value11 = (String)table11.get("value");
String sessionId11 = id11.toString();
boolean x11 = list11.contains(key11);
boolean y11 = list11.contains(value11);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key11%>"><%=key11%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x11 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value11%>" name="<%=value11%>" class="textareaenter" readonly><%=id11.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table12" class="Hashtable" scope="session"/>
<jsp:useBean id="id12" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list12" class="ArrayList" scope="request"/>

<%!
public void test12()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key12 = (String)table12.get("key");
String value12 = (String)table12.get("value");
String sessionId12 = id12.toString();
boolean x12 = list12.contains(key12);
boolean y12 = list12.contains(value12);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key12%>"><%=key12%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x12 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value12%>" name="<%=value12%>" class="textareaenter" readonly><%=id12.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table13" class="Hashtable" scope="session"/>
<jsp:useBean id="id13" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list13" class="ArrayList" scope="request"/>

<%!
public void test13()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key13 = (String)table13.get("key");
String value13 = (String)table13.get("value");
String sessionId13 = id13.toString();
boolean x13 = list13.contains(key13);
boolean y13 = list13.contains(value13);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key13%>"><%=key13%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x13 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value13%>" name="<%=value13%>" class="textareaenter" readonly><%=id13.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table14" class="Hashtable" scope="session"/>
<jsp:useBean id="id14" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list14" class="ArrayList" scope="request"/>

<%!
public void test14()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key14 = (String)table14.get("key");
String value14 = (String)table14.get("value");
String sessionId14 = id14.toString();
boolean x14 = list14.contains(key14);
boolean y14 = list14.contains(value14);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key14%>"><%=key14%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x14 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value14%>" name="<%=value14%>" class="textareaenter" readonly><%=id14.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table15" class="Hashtable" scope="session"/>
<jsp:useBean id="id15" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list15" class="ArrayList" scope="request"/>

<%!
public void test15()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key15 = (String)table15.get("key");
String value15 = (String)table15.get("value");
String sessionId15 = id15.toString();
boolean x15 = list15.contains(key15);
boolean y15 = list15.contains(value15);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key15%>"><%=key15%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x15 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value15%>" name="<%=value15%>" class="textareaenter" readonly><%=id15.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table16" class="Hashtable" scope="session"/>
<jsp:useBean id="id16" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list16" class="ArrayList" scope="request"/>

<%!
public void test16()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key16 = (String)table16.get("key");
String value16 = (String)table16.get("value");
String sessionId16 = id16.toString();
boolean x16 = list16.contains(key16);
boolean y16 = list16.contains(value16);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key16%>"><%=key16%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x16 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value16%>" name="<%=value16%>" class="textareaenter" readonly><%=id16.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table17" class="Hashtable" scope="session"/>
<jsp:useBean id="id17" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list17" class="ArrayList" scope="request"/>

<%!
public void test17()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key17 = (String)table17.get("key");
String value17 = (String)table17.get("value");
String sessionId17 = id17.toString();
boolean x17 = list17.contains(key17);
boolean y17 = list17.contains(value17);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key17%>"><%=key17%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x17 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value17%>" name="<%=value17%>" class="textareaenter" readonly><%=id17.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table18" class="Hashtable" scope="session"/>
<jsp:useBean id="id18" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list18" class="ArrayList" scope="request"/>

<%!
public void test18()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key18 = (String)table18.get("key");
String value18 = (String)table18.get("value");
String sessionId18 = id18.toString();
boolean x18 = list18.contains(key18);
boolean y18 = list18.contains(value18);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key18%>"><%=key18%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x18 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value18%>" name="<%=value18%>" class="textareaenter" readonly><%=id18.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table19" class="Hashtable" scope="session"/>
<jsp:useBean id="id19" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list19" class="ArrayList" scope="request"/>

<%!
public void test19()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key19 = (String)table19.get("key");
String value19 = (String)table19.get("value");
String sessionId19 = id19.toString();
boolean x19 = list19.contains(key19);
boolean y19 = list19.contains(value19);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key19%>"><%=key19%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x19 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value19%>" name="<%=value19%>" class="textareaenter" readonly><%=id19.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table20" class="Hashtable" scope="session"/>
<jsp:useBean id="id20" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list20" class="ArrayList" scope="request"/>

<%!
public void test20()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key20 = (String)table20.get("key");
String value20 = (String)table20.get("value");
String sessionId20 = id20.toString();
boolean x20 = list20.contains(key20);
boolean y20 = list20.contains(value20);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key20%>"><%=key20%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x20 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value20%>" name="<%=value20%>" class="textareaenter" readonly><%=id20.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table21" class="Hashtable" scope="session"/>
<jsp:useBean id="id21" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list21" class="ArrayList" scope="request"/>

<%!
public void test21()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key21 = (String)table21.get("key");
String value21 = (String)table21.get("value");
String sessionId21 = id21.toString();
boolean x21 = list21.contains(key21);
boolean y21 = list21.contains(value21);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key21%>"><%=key21%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x21 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value21%>" name="<%=value21%>" class="textareaenter" readonly><%=id21.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table22" class="Hashtable" scope="session"/>
<jsp:useBean id="id22" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list22" class="ArrayList" scope="request"/>

<%!
public void test22()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key22 = (String)table22.get("key");
String value22 = (String)table22.get("value");
String sessionId22 = id22.toString();
boolean x22 = list22.contains(key22);
boolean y22 = list22.contains(value22);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key22%>"><%=key22%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x22 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value22%>" name="<%=value22%>" class="textareaenter" readonly><%=id22.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table23" class="Hashtable" scope="session"/>
<jsp:useBean id="id23" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list23" class="ArrayList" scope="request"/>

<%!
public void test23()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key23 = (String)table23.get("key");
String value23 = (String)table23.get("value");
String sessionId23 = id23.toString();
boolean x23 = list23.contains(key23);
boolean y23 = list23.contains(value23);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key23%>"><%=key23%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x23 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value23%>" name="<%=value23%>" class="textareaenter" readonly><%=id23.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table24" class="Hashtable" scope="session"/>
<jsp:useBean id="id24" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list24" class="ArrayList" scope="request"/>

<%!
public void test24()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key24 = (String)table24.get("key");
String value24 = (String)table24.get("value");
String sessionId24 = id24.toString();
boolean x24 = list24.contains(key24);
boolean y24 = list24.contains(value24);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key24%>"><%=key24%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x24 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value24%>" name="<%=value24%>" class="textareaenter" readonly><%=id24.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table25" class="Hashtable" scope="session"/>
<jsp:useBean id="id25" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list25" class="ArrayList" scope="request"/>

<%!
public void test25()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key25 = (String)table25.get("key");
String value25 = (String)table25.get("value");
String sessionId25 = id25.toString();
boolean x25 = list25.contains(key25);
boolean y25 = list25.contains(value25);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key25%>"><%=key25%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x25 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value25%>" name="<%=value25%>" class="textareaenter" readonly><%=id25.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table26" class="Hashtable" scope="session"/>
<jsp:useBean id="id26" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list26" class="ArrayList" scope="request"/>

<%!
public void test26()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key26 = (String)table26.get("key");
String value26 = (String)table26.get("value");
String sessionId26 = id26.toString();
boolean x26 = list26.contains(key26);
boolean y26 = list26.contains(value26);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key26%>"><%=key26%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x26 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value26%>" name="<%=value26%>" class="textareaenter" readonly><%=id26.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table27" class="Hashtable" scope="session"/>
<jsp:useBean id="id27" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list27" class="ArrayList" scope="request"/>

<%!
public void test27()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key27 = (String)table27.get("key");
String value27 = (String)table27.get("value");
String sessionId27 = id27.toString();
boolean x27 = list27.contains(key27);
boolean y27 = list27.contains(value27);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key27%>"><%=key27%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x27 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value27%>" name="<%=value27%>" class="textareaenter" readonly><%=id27.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table28" class="Hashtable" scope="session"/>
<jsp:useBean id="id28" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list28" class="ArrayList" scope="request"/>

<%!
public void test28()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key28 = (String)table28.get("key");
String value28 = (String)table28.get("value");
String sessionId28 = id28.toString();
boolean x28 = list28.contains(key28);
boolean y28 = list28.contains(value28);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key28%>"><%=key28%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x28 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value28%>" name="<%=value28%>" class="textareaenter" readonly><%=id28.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table29" class="Hashtable" scope="session"/>
<jsp:useBean id="id29" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list29" class="ArrayList" scope="request"/>

<%!
public void test29()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key29 = (String)table29.get("key");
String value29 = (String)table29.get("value");
String sessionId29 = id29.toString();
boolean x29 = list29.contains(key29);
boolean y29 = list29.contains(value29);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key29%>"><%=key29%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x29 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value29%>" name="<%=value29%>" class="textareaenter" readonly><%=id29.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table30" class="Hashtable" scope="session"/>
<jsp:useBean id="id30" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list30" class="ArrayList" scope="request"/>

<%!
public void test30()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key30 = (String)table30.get("key");
String value30 = (String)table30.get("value");
String sessionId30 = id30.toString();
boolean x30 = list30.contains(key30);
boolean y30 = list30.contains(value30);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key30%>"><%=key30%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x30 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value30%>" name="<%=value30%>" class="textareaenter" readonly><%=id30.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table31" class="Hashtable" scope="session"/>
<jsp:useBean id="id31" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list31" class="ArrayList" scope="request"/>

<%!
public void test31()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key31 = (String)table31.get("key");
String value31 = (String)table31.get("value");
String sessionId31 = id31.toString();
boolean x31 = list31.contains(key31);
boolean y31 = list31.contains(value31);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key31%>"><%=key31%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x31 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value31%>" name="<%=value31%>" class="textareaenter" readonly><%=id31.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table32" class="Hashtable" scope="session"/>
<jsp:useBean id="id32" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list32" class="ArrayList" scope="request"/>

<%!
public void test32()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key32 = (String)table32.get("key");
String value32 = (String)table32.get("value");
String sessionId32 = id32.toString();
boolean x32 = list32.contains(key32);
boolean y32 = list32.contains(value32);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key32%>"><%=key32%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x32 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value32%>" name="<%=value32%>" class="textareaenter" readonly><%=id32.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table33" class="Hashtable" scope="session"/>
<jsp:useBean id="id33" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list33" class="ArrayList" scope="request"/>

<%!
public void test33()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key33 = (String)table33.get("key");
String value33 = (String)table33.get("value");
String sessionId33 = id33.toString();
boolean x33 = list33.contains(key33);
boolean y33 = list33.contains(value33);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key33%>"><%=key33%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x33 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value33%>" name="<%=value33%>" class="textareaenter" readonly><%=id33.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table34" class="Hashtable" scope="session"/>
<jsp:useBean id="id34" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list34" class="ArrayList" scope="request"/>

<%!
public void test34()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key34 = (String)table34.get("key");
String value34 = (String)table34.get("value");
String sessionId34 = id34.toString();
boolean x34 = list34.contains(key34);
boolean y34 = list34.contains(value34);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key34%>"><%=key34%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x34 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value34%>" name="<%=value34%>" class="textareaenter" readonly><%=id34.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table35" class="Hashtable" scope="session"/>
<jsp:useBean id="id35" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list35" class="ArrayList" scope="request"/>

<%!
public void test35()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key35 = (String)table35.get("key");
String value35 = (String)table35.get("value");
String sessionId35 = id35.toString();
boolean x35 = list35.contains(key35);
boolean y35 = list35.contains(value35);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key35%>"><%=key35%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x35 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value35%>" name="<%=value35%>" class="textareaenter" readonly><%=id35.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table36" class="Hashtable" scope="session"/>
<jsp:useBean id="id36" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list36" class="ArrayList" scope="request"/>

<%!
public void test36()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key36 = (String)table36.get("key");
String value36 = (String)table36.get("value");
String sessionId36 = id36.toString();
boolean x36 = list36.contains(key36);
boolean y36 = list36.contains(value36);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key36%>"><%=key36%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x36 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value36%>" name="<%=value36%>" class="textareaenter" readonly><%=id36.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table37" class="Hashtable" scope="session"/>
<jsp:useBean id="id37" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list37" class="ArrayList" scope="request"/>

<%!
public void test37()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key37 = (String)table37.get("key");
String value37 = (String)table37.get("value");
String sessionId37 = id37.toString();
boolean x37 = list37.contains(key37);
boolean y37 = list37.contains(value37);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key37%>"><%=key37%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x37 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value37%>" name="<%=value37%>" class="textareaenter" readonly><%=id37.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table38" class="Hashtable" scope="session"/>
<jsp:useBean id="id38" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list38" class="ArrayList" scope="request"/>

<%!
public void test38()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key38 = (String)table38.get("key");
String value38 = (String)table38.get("value");
String sessionId38 = id38.toString();
boolean x38 = list38.contains(key38);
boolean y38 = list38.contains(value38);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key38%>"><%=key38%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x38 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value38%>" name="<%=value38%>" class="textareaenter" readonly><%=id38.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table39" class="Hashtable" scope="session"/>
<jsp:useBean id="id39" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list39" class="ArrayList" scope="request"/>

<%!
public void test39()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key39 = (String)table39.get("key");
String value39 = (String)table39.get("value");
String sessionId39 = id39.toString();
boolean x39 = list39.contains(key39);
boolean y39 = list39.contains(value39);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key39%>"><%=key39%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x39 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value39%>" name="<%=value39%>" class="textareaenter" readonly><%=id39.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table40" class="Hashtable" scope="session"/>
<jsp:useBean id="id40" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list40" class="ArrayList" scope="request"/>

<%!
public void test40()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key40 = (String)table40.get("key");
String value40 = (String)table40.get("value");
String sessionId40 = id40.toString();
boolean x40 = list40.contains(key40);
boolean y40 = list40.contains(value40);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key40%>"><%=key40%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x40 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value40%>" name="<%=value40%>" class="textareaenter" readonly><%=id40.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table41" class="Hashtable" scope="session"/>
<jsp:useBean id="id41" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list41" class="ArrayList" scope="request"/>

<%!
public void test41()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key41 = (String)table41.get("key");
String value41 = (String)table41.get("value");
String sessionId41 = id41.toString();
boolean x41 = list41.contains(key41);
boolean y41 = list41.contains(value41);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key41%>"><%=key41%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x41 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value41%>" name="<%=value41%>" class="textareaenter" readonly><%=id41.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table42" class="Hashtable" scope="session"/>
<jsp:useBean id="id42" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list42" class="ArrayList" scope="request"/>

<%!
public void test42()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key42 = (String)table42.get("key");
String value42 = (String)table42.get("value");
String sessionId42 = id42.toString();
boolean x42 = list42.contains(key42);
boolean y42 = list42.contains(value42);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key42%>"><%=key42%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x42 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value42%>" name="<%=value42%>" class="textareaenter" readonly><%=id42.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table43" class="Hashtable" scope="session"/>
<jsp:useBean id="id43" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list43" class="ArrayList" scope="request"/>

<%!
public void test43()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key43 = (String)table43.get("key");
String value43 = (String)table43.get("value");
String sessionId43 = id43.toString();
boolean x43 = list43.contains(key43);
boolean y43 = list43.contains(value43);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key43%>"><%=key43%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x43 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value43%>" name="<%=value43%>" class="textareaenter" readonly><%=id43.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table44" class="Hashtable" scope="session"/>
<jsp:useBean id="id44" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list44" class="ArrayList" scope="request"/>

<%!
public void test44()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key44 = (String)table44.get("key");
String value44 = (String)table44.get("value");
String sessionId44 = id44.toString();
boolean x44 = list44.contains(key44);
boolean y44 = list44.contains(value44);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key44%>"><%=key44%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x44 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value44%>" name="<%=value44%>" class="textareaenter" readonly><%=id44.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table45" class="Hashtable" scope="session"/>
<jsp:useBean id="id45" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list45" class="ArrayList" scope="request"/>

<%!
public void test45()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key45 = (String)table45.get("key");
String value45 = (String)table45.get("value");
String sessionId45 = id45.toString();
boolean x45 = list45.contains(key45);
boolean y45 = list45.contains(value45);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key45%>"><%=key45%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x45 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value45%>" name="<%=value45%>" class="textareaenter" readonly><%=id45.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table46" class="Hashtable" scope="session"/>
<jsp:useBean id="id46" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list46" class="ArrayList" scope="request"/>

<%!
public void test46()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key46 = (String)table46.get("key");
String value46 = (String)table46.get("value");
String sessionId46 = id46.toString();
boolean x46 = list46.contains(key46);
boolean y46 = list46.contains(value46);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key46%>"><%=key46%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x46 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value46%>" name="<%=value46%>" class="textareaenter" readonly><%=id46.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table47" class="Hashtable" scope="session"/>
<jsp:useBean id="id47" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list47" class="ArrayList" scope="request"/>

<%!
public void test47()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key47 = (String)table47.get("key");
String value47 = (String)table47.get("value");
String sessionId47 = id47.toString();
boolean x47 = list47.contains(key47);
boolean y47 = list47.contains(value47);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key47%>"><%=key47%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x47 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value47%>" name="<%=value47%>" class="textareaenter" readonly><%=id47.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table48" class="Hashtable" scope="session"/>
<jsp:useBean id="id48" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list48" class="ArrayList" scope="request"/>

<%!
public void test48()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key48 = (String)table48.get("key");
String value48 = (String)table48.get("value");
String sessionId48 = id48.toString();
boolean x48 = list48.contains(key48);
boolean y48 = list48.contains(value48);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key48%>"><%=key48%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x48 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value48%>" name="<%=value48%>" class="textareaenter" readonly><%=id48.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table49" class="Hashtable" scope="session"/>
<jsp:useBean id="id49" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list49" class="ArrayList" scope="request"/>

<%!
public void test49()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key49 = (String)table49.get("key");
String value49 = (String)table49.get("value");
String sessionId49 = id49.toString();
boolean x49 = list49.contains(key49);
boolean y49 = list49.contains(value49);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key49%>"><%=key49%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x49 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value49%>" name="<%=value49%>" class="textareaenter" readonly><%=id49.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table50" class="Hashtable" scope="session"/>
<jsp:useBean id="id50" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list50" class="ArrayList" scope="request"/>

<%!
public void test50()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key50 = (String)table50.get("key");
String value50 = (String)table50.get("value");
String sessionId50 = id50.toString();
boolean x50 = list50.contains(key50);
boolean y50 = list50.contains(value50);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key50%>"><%=key50%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x50 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value50%>" name="<%=value50%>" class="textareaenter" readonly><%=id50.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table51" class="Hashtable" scope="session"/>
<jsp:useBean id="id51" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list51" class="ArrayList" scope="request"/>

<%!
public void test51()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key51 = (String)table51.get("key");
String value51 = (String)table51.get("value");
String sessionId51 = id51.toString();
boolean x51 = list51.contains(key51);
boolean y51 = list51.contains(value51);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key51%>"><%=key51%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x51 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value51%>" name="<%=value51%>" class="textareaenter" readonly><%=id51.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table52" class="Hashtable" scope="session"/>
<jsp:useBean id="id52" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list52" class="ArrayList" scope="request"/>

<%!
public void test52()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key52 = (String)table52.get("key");
String value52 = (String)table52.get("value");
String sessionId52 = id52.toString();
boolean x52 = list52.contains(key52);
boolean y52 = list52.contains(value52);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key52%>"><%=key52%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x52 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value52%>" name="<%=value52%>" class="textareaenter" readonly><%=id52.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table53" class="Hashtable" scope="session"/>
<jsp:useBean id="id53" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list53" class="ArrayList" scope="request"/>

<%!
public void test53()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key53 = (String)table53.get("key");
String value53 = (String)table53.get("value");
String sessionId53 = id53.toString();
boolean x53 = list53.contains(key53);
boolean y53 = list53.contains(value53);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key53%>"><%=key53%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x53 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value53%>" name="<%=value53%>" class="textareaenter" readonly><%=id53.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table54" class="Hashtable" scope="session"/>
<jsp:useBean id="id54" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list54" class="ArrayList" scope="request"/>

<%!
public void test54()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key54 = (String)table54.get("key");
String value54 = (String)table54.get("value");
String sessionId54 = id54.toString();
boolean x54 = list54.contains(key54);
boolean y54 = list54.contains(value54);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key54%>"><%=key54%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x54 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value54%>" name="<%=value54%>" class="textareaenter" readonly><%=id54.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table55" class="Hashtable" scope="session"/>
<jsp:useBean id="id55" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list55" class="ArrayList" scope="request"/>

<%!
public void test55()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key55 = (String)table55.get("key");
String value55 = (String)table55.get("value");
String sessionId55 = id55.toString();
boolean x55 = list55.contains(key55);
boolean y55 = list55.contains(value55);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key55%>"><%=key55%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x55 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value55%>" name="<%=value55%>" class="textareaenter" readonly><%=id55.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table56" class="Hashtable" scope="session"/>
<jsp:useBean id="id56" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list56" class="ArrayList" scope="request"/>

<%!
public void test56()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key56 = (String)table56.get("key");
String value56 = (String)table56.get("value");
String sessionId56 = id56.toString();
boolean x56 = list56.contains(key56);
boolean y56 = list56.contains(value56);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key56%>"><%=key56%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x56 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value56%>" name="<%=value56%>" class="textareaenter" readonly><%=id56.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table57" class="Hashtable" scope="session"/>
<jsp:useBean id="id57" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list57" class="ArrayList" scope="request"/>

<%!
public void test57()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key57 = (String)table57.get("key");
String value57 = (String)table57.get("value");
String sessionId57 = id57.toString();
boolean x57 = list57.contains(key57);
boolean y57 = list57.contains(value57);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key57%>"><%=key57%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x57 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value57%>" name="<%=value57%>" class="textareaenter" readonly><%=id57.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table58" class="Hashtable" scope="session"/>
<jsp:useBean id="id58" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list58" class="ArrayList" scope="request"/>

<%!
public void test58()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key58 = (String)table58.get("key");
String value58 = (String)table58.get("value");
String sessionId58 = id58.toString();
boolean x58 = list58.contains(key58);
boolean y58 = list58.contains(value58);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key58%>"><%=key58%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x58 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value58%>" name="<%=value58%>" class="textareaenter" readonly><%=id58.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table59" class="Hashtable" scope="session"/>
<jsp:useBean id="id59" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list59" class="ArrayList" scope="request"/>

<%!
public void test59()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key59 = (String)table59.get("key");
String value59 = (String)table59.get("value");
String sessionId59 = id59.toString();
boolean x59 = list59.contains(key59);
boolean y59 = list59.contains(value59);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key59%>"><%=key59%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x59 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value59%>" name="<%=value59%>" class="textareaenter" readonly><%=id59.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table60" class="Hashtable" scope="session"/>
<jsp:useBean id="id60" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list60" class="ArrayList" scope="request"/>

<%!
public void test60()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key60 = (String)table60.get("key");
String value60 = (String)table60.get("value");
String sessionId60 = id60.toString();
boolean x60 = list60.contains(key60);
boolean y60 = list60.contains(value60);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key60%>"><%=key60%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x60 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value60%>" name="<%=value60%>" class="textareaenter" readonly><%=id60.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table61" class="Hashtable" scope="session"/>
<jsp:useBean id="id61" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list61" class="ArrayList" scope="request"/>

<%!
public void test61()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key61 = (String)table61.get("key");
String value61 = (String)table61.get("value");
String sessionId61 = id61.toString();
boolean x61 = list61.contains(key61);
boolean y61 = list61.contains(value61);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key61%>"><%=key61%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x61 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value61%>" name="<%=value61%>" class="textareaenter" readonly><%=id61.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table62" class="Hashtable" scope="session"/>
<jsp:useBean id="id62" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list62" class="ArrayList" scope="request"/>

<%!
public void test62()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key62 = (String)table62.get("key");
String value62 = (String)table62.get("value");
String sessionId62 = id62.toString();
boolean x62 = list62.contains(key62);
boolean y62 = list62.contains(value62);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key62%>"><%=key62%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x62 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value62%>" name="<%=value62%>" class="textareaenter" readonly><%=id62.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table63" class="Hashtable" scope="session"/>
<jsp:useBean id="id63" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list63" class="ArrayList" scope="request"/>

<%!
public void test63()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key63 = (String)table63.get("key");
String value63 = (String)table63.get("value");
String sessionId63 = id63.toString();
boolean x63 = list63.contains(key63);
boolean y63 = list63.contains(value63);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key63%>"><%=key63%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x63 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value63%>" name="<%=value63%>" class="textareaenter" readonly><%=id63.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table64" class="Hashtable" scope="session"/>
<jsp:useBean id="id64" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list64" class="ArrayList" scope="request"/>

<%!
public void test64()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key64 = (String)table64.get("key");
String value64 = (String)table64.get("value");
String sessionId64 = id64.toString();
boolean x64 = list64.contains(key64);
boolean y64 = list64.contains(value64);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key64%>"><%=key64%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x64 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value64%>" name="<%=value64%>" class="textareaenter" readonly><%=id64.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table65" class="Hashtable" scope="session"/>
<jsp:useBean id="id65" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list65" class="ArrayList" scope="request"/>

<%!
public void test65()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key65 = (String)table65.get("key");
String value65 = (String)table65.get("value");
String sessionId65 = id65.toString();
boolean x65 = list65.contains(key65);
boolean y65 = list65.contains(value65);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key65%>"><%=key65%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x65 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value65%>" name="<%=value65%>" class="textareaenter" readonly><%=id65.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table66" class="Hashtable" scope="session"/>
<jsp:useBean id="id66" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list66" class="ArrayList" scope="request"/>

<%!
public void test66()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key66 = (String)table66.get("key");
String value66 = (String)table66.get("value");
String sessionId66 = id66.toString();
boolean x66 = list66.contains(key66);
boolean y66 = list66.contains(value66);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key66%>"><%=key66%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x66 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value66%>" name="<%=value66%>" class="textareaenter" readonly><%=id66.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table67" class="Hashtable" scope="session"/>
<jsp:useBean id="id67" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list67" class="ArrayList" scope="request"/>

<%!
public void test67()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key67 = (String)table67.get("key");
String value67 = (String)table67.get("value");
String sessionId67 = id67.toString();
boolean x67 = list67.contains(key67);
boolean y67 = list67.contains(value67);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key67%>"><%=key67%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x67 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value67%>" name="<%=value67%>" class="textareaenter" readonly><%=id67.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table68" class="Hashtable" scope="session"/>
<jsp:useBean id="id68" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list68" class="ArrayList" scope="request"/>

<%!
public void test68()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key68 = (String)table68.get("key");
String value68 = (String)table68.get("value");
String sessionId68 = id68.toString();
boolean x68 = list68.contains(key68);
boolean y68 = list68.contains(value68);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key68%>"><%=key68%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x68 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value68%>" name="<%=value68%>" class="textareaenter" readonly><%=id68.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table69" class="Hashtable" scope="session"/>
<jsp:useBean id="id69" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list69" class="ArrayList" scope="request"/>

<%!
public void test69()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key69 = (String)table69.get("key");
String value69 = (String)table69.get("value");
String sessionId69 = id69.toString();
boolean x69 = list69.contains(key69);
boolean y69 = list69.contains(value69);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key69%>"><%=key69%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x69 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value69%>" name="<%=value69%>" class="textareaenter" readonly><%=id69.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table70" class="Hashtable" scope="session"/>
<jsp:useBean id="id70" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list70" class="ArrayList" scope="request"/>

<%!
public void test70()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key70 = (String)table70.get("key");
String value70 = (String)table70.get("value");
String sessionId70 = id70.toString();
boolean x70 = list70.contains(key70);
boolean y70 = list70.contains(value70);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key70%>"><%=key70%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x70 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value70%>" name="<%=value70%>" class="textareaenter" readonly><%=id70.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table71" class="Hashtable" scope="session"/>
<jsp:useBean id="id71" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list71" class="ArrayList" scope="request"/>

<%!
public void test71()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key71 = (String)table71.get("key");
String value71 = (String)table71.get("value");
String sessionId71 = id71.toString();
boolean x71 = list71.contains(key71);
boolean y71 = list71.contains(value71);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key71%>"><%=key71%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x71 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value71%>" name="<%=value71%>" class="textareaenter" readonly><%=id71.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table72" class="Hashtable" scope="session"/>
<jsp:useBean id="id72" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list72" class="ArrayList" scope="request"/>

<%!
public void test72()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key72 = (String)table72.get("key");
String value72 = (String)table72.get("value");
String sessionId72 = id72.toString();
boolean x72 = list72.contains(key72);
boolean y72 = list72.contains(value72);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key72%>"><%=key72%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x72 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value72%>" name="<%=value72%>" class="textareaenter" readonly><%=id72.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table73" class="Hashtable" scope="session"/>
<jsp:useBean id="id73" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list73" class="ArrayList" scope="request"/>

<%!
public void test73()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key73 = (String)table73.get("key");
String value73 = (String)table73.get("value");
String sessionId73 = id73.toString();
boolean x73 = list73.contains(key73);
boolean y73 = list73.contains(value73);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key73%>"><%=key73%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x73 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value73%>" name="<%=value73%>" class="textareaenter" readonly><%=id73.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table74" class="Hashtable" scope="session"/>
<jsp:useBean id="id74" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list74" class="ArrayList" scope="request"/>

<%!
public void test74()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key74 = (String)table74.get("key");
String value74 = (String)table74.get("value");
String sessionId74 = id74.toString();
boolean x74 = list74.contains(key74);
boolean y74 = list74.contains(value74);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key74%>"><%=key74%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x74 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value74%>" name="<%=value74%>" class="textareaenter" readonly><%=id74.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table75" class="Hashtable" scope="session"/>
<jsp:useBean id="id75" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list75" class="ArrayList" scope="request"/>

<%!
public void test75()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key75 = (String)table75.get("key");
String value75 = (String)table75.get("value");
String sessionId75 = id75.toString();
boolean x75 = list75.contains(key75);
boolean y75 = list75.contains(value75);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key75%>"><%=key75%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x75 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value75%>" name="<%=value75%>" class="textareaenter" readonly><%=id75.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table76" class="Hashtable" scope="session"/>
<jsp:useBean id="id76" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list76" class="ArrayList" scope="request"/>

<%!
public void test76()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key76 = (String)table76.get("key");
String value76 = (String)table76.get("value");
String sessionId76 = id76.toString();
boolean x76 = list76.contains(key76);
boolean y76 = list76.contains(value76);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key76%>"><%=key76%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x76 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value76%>" name="<%=value76%>" class="textareaenter" readonly><%=id76.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table77" class="Hashtable" scope="session"/>
<jsp:useBean id="id77" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list77" class="ArrayList" scope="request"/>

<%!
public void test77()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key77 = (String)table77.get("key");
String value77 = (String)table77.get("value");
String sessionId77 = id77.toString();
boolean x77 = list77.contains(key77);
boolean y77 = list77.contains(value77);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key77%>"><%=key77%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x77 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value77%>" name="<%=value77%>" class="textareaenter" readonly><%=id77.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table78" class="Hashtable" scope="session"/>
<jsp:useBean id="id78" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list78" class="ArrayList" scope="request"/>

<%!
public void test78()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key78 = (String)table78.get("key");
String value78 = (String)table78.get("value");
String sessionId78 = id78.toString();
boolean x78 = list78.contains(key78);
boolean y78 = list78.contains(value78);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key78%>"><%=key78%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x78 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value78%>" name="<%=value78%>" class="textareaenter" readonly><%=id78.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table79" class="Hashtable" scope="session"/>
<jsp:useBean id="id79" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list79" class="ArrayList" scope="request"/>

<%!
public void test79()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key79 = (String)table79.get("key");
String value79 = (String)table79.get("value");
String sessionId79 = id79.toString();
boolean x79 = list79.contains(key79);
boolean y79 = list79.contains(value79);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key79%>"><%=key79%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x79 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value79%>" name="<%=value79%>" class="textareaenter" readonly><%=id79.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table80" class="Hashtable" scope="session"/>
<jsp:useBean id="id80" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list80" class="ArrayList" scope="request"/>

<%!
public void test80()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key80 = (String)table80.get("key");
String value80 = (String)table80.get("value");
String sessionId80 = id80.toString();
boolean x80 = list80.contains(key80);
boolean y80 = list80.contains(value80);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key80%>"><%=key80%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x80 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value80%>" name="<%=value80%>" class="textareaenter" readonly><%=id80.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table81" class="Hashtable" scope="session"/>
<jsp:useBean id="id81" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list81" class="ArrayList" scope="request"/>

<%!
public void test81()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key81 = (String)table81.get("key");
String value81 = (String)table81.get("value");
String sessionId81 = id81.toString();
boolean x81 = list81.contains(key81);
boolean y81 = list81.contains(value81);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key81%>"><%=key81%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x81 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value81%>" name="<%=value81%>" class="textareaenter" readonly><%=id81.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table82" class="Hashtable" scope="session"/>
<jsp:useBean id="id82" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list82" class="ArrayList" scope="request"/>

<%!
public void test82()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key82 = (String)table82.get("key");
String value82 = (String)table82.get("value");
String sessionId82 = id82.toString();
boolean x82 = list82.contains(key82);
boolean y82 = list82.contains(value82);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key82%>"><%=key82%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x82 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value82%>" name="<%=value82%>" class="textareaenter" readonly><%=id82.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table83" class="Hashtable" scope="session"/>
<jsp:useBean id="id83" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list83" class="ArrayList" scope="request"/>

<%!
public void test83()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key83 = (String)table83.get("key");
String value83 = (String)table83.get("value");
String sessionId83 = id83.toString();
boolean x83 = list83.contains(key83);
boolean y83 = list83.contains(value83);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key83%>"><%=key83%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x83 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value83%>" name="<%=value83%>" class="textareaenter" readonly><%=id83.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table84" class="Hashtable" scope="session"/>
<jsp:useBean id="id84" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list84" class="ArrayList" scope="request"/>

<%!
public void test84()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key84 = (String)table84.get("key");
String value84 = (String)table84.get("value");
String sessionId84 = id84.toString();
boolean x84 = list84.contains(key84);
boolean y84 = list84.contains(value84);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key84%>"><%=key84%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x84 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value84%>" name="<%=value84%>" class="textareaenter" readonly><%=id84.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table85" class="Hashtable" scope="session"/>
<jsp:useBean id="id85" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list85" class="ArrayList" scope="request"/>

<%!
public void test85()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key85 = (String)table85.get("key");
String value85 = (String)table85.get("value");
String sessionId85 = id85.toString();
boolean x85 = list85.contains(key85);
boolean y85 = list85.contains(value85);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key85%>"><%=key85%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x85 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value85%>" name="<%=value85%>" class="textareaenter" readonly><%=id85.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table86" class="Hashtable" scope="session"/>
<jsp:useBean id="id86" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list86" class="ArrayList" scope="request"/>

<%!
public void test86()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key86 = (String)table86.get("key");
String value86 = (String)table86.get("value");
String sessionId86 = id86.toString();
boolean x86 = list86.contains(key86);
boolean y86 = list86.contains(value86);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key86%>"><%=key86%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x86 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value86%>" name="<%=value86%>" class="textareaenter" readonly><%=id86.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table87" class="Hashtable" scope="session"/>
<jsp:useBean id="id87" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list87" class="ArrayList" scope="request"/>

<%!
public void test87()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key87 = (String)table87.get("key");
String value87 = (String)table87.get("value");
String sessionId87 = id87.toString();
boolean x87 = list87.contains(key87);
boolean y87 = list87.contains(value87);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key87%>"><%=key87%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x87 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value87%>" name="<%=value87%>" class="textareaenter" readonly><%=id87.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table88" class="Hashtable" scope="session"/>
<jsp:useBean id="id88" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list88" class="ArrayList" scope="request"/>

<%!
public void test88()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key88 = (String)table88.get("key");
String value88 = (String)table88.get("value");
String sessionId88 = id88.toString();
boolean x88 = list88.contains(key88);
boolean y88 = list88.contains(value88);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key88%>"><%=key88%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x88 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value88%>" name="<%=value88%>" class="textareaenter" readonly><%=id88.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table89" class="Hashtable" scope="session"/>
<jsp:useBean id="id89" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list89" class="ArrayList" scope="request"/>

<%!
public void test89()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key89 = (String)table89.get("key");
String value89 = (String)table89.get("value");
String sessionId89 = id89.toString();
boolean x89 = list89.contains(key89);
boolean y89 = list89.contains(value89);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key89%>"><%=key89%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x89 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value89%>" name="<%=value89%>" class="textareaenter" readonly><%=id89.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table90" class="Hashtable" scope="session"/>
<jsp:useBean id="id90" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list90" class="ArrayList" scope="request"/>

<%!
public void test90()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key90 = (String)table90.get("key");
String value90 = (String)table90.get("value");
String sessionId90 = id90.toString();
boolean x90 = list90.contains(key90);
boolean y90 = list90.contains(value90);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key90%>"><%=key90%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x90 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value90%>" name="<%=value90%>" class="textareaenter" readonly><%=id90.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table91" class="Hashtable" scope="session"/>
<jsp:useBean id="id91" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list91" class="ArrayList" scope="request"/>

<%!
public void test91()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key91 = (String)table91.get("key");
String value91 = (String)table91.get("value");
String sessionId91 = id91.toString();
boolean x91 = list91.contains(key91);
boolean y91 = list91.contains(value91);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key91%>"><%=key91%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x91 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value91%>" name="<%=value91%>" class="textareaenter" readonly><%=id91.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table92" class="Hashtable" scope="session"/>
<jsp:useBean id="id92" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list92" class="ArrayList" scope="request"/>

<%!
public void test92()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key92 = (String)table92.get("key");
String value92 = (String)table92.get("value");
String sessionId92 = id92.toString();
boolean x92 = list92.contains(key92);
boolean y92 = list92.contains(value92);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key92%>"><%=key92%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x92 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value92%>" name="<%=value92%>" class="textareaenter" readonly><%=id92.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table93" class="Hashtable" scope="session"/>
<jsp:useBean id="id93" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list93" class="ArrayList" scope="request"/>

<%!
public void test93()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key93 = (String)table93.get("key");
String value93 = (String)table93.get("value");
String sessionId93 = id93.toString();
boolean x93 = list93.contains(key93);
boolean y93 = list93.contains(value93);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key93%>"><%=key93%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x93 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value93%>" name="<%=value93%>" class="textareaenter" readonly><%=id93.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table94" class="Hashtable" scope="session"/>
<jsp:useBean id="id94" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list94" class="ArrayList" scope="request"/>

<%!
public void test94()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key94 = (String)table94.get("key");
String value94 = (String)table94.get("value");
String sessionId94 = id94.toString();
boolean x94 = list94.contains(key94);
boolean y94 = list94.contains(value94);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key94%>"><%=key94%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x94 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value94%>" name="<%=value94%>" class="textareaenter" readonly><%=id94.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table95" class="Hashtable" scope="session"/>
<jsp:useBean id="id95" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list95" class="ArrayList" scope="request"/>

<%!
public void test95()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key95 = (String)table95.get("key");
String value95 = (String)table95.get("value");
String sessionId95 = id95.toString();
boolean x95 = list95.contains(key95);
boolean y95 = list95.contains(value95);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key95%>"><%=key95%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x95 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value95%>" name="<%=value95%>" class="textareaenter" readonly><%=id95.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table96" class="Hashtable" scope="session"/>
<jsp:useBean id="id96" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list96" class="ArrayList" scope="request"/>

<%!
public void test96()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key96 = (String)table96.get("key");
String value96 = (String)table96.get("value");
String sessionId96 = id96.toString();
boolean x96 = list96.contains(key96);
boolean y96 = list96.contains(value96);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key96%>"><%=key96%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x96 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value96%>" name="<%=value96%>" class="textareaenter" readonly><%=id96.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table97" class="Hashtable" scope="session"/>
<jsp:useBean id="id97" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list97" class="ArrayList" scope="request"/>

<%!
public void test97()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key97 = (String)table97.get("key");
String value97 = (String)table97.get("value");
String sessionId97 = id97.toString();
boolean x97 = list97.contains(key97);
boolean y97 = list97.contains(value97);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key97%>"><%=key97%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x97 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value97%>" name="<%=value97%>" class="textareaenter" readonly><%=id97.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table98" class="Hashtable" scope="session"/>
<jsp:useBean id="id98" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list98" class="ArrayList" scope="request"/>

<%!
public void test98()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key98 = (String)table98.get("key");
String value98 = (String)table98.get("value");
String sessionId98 = id98.toString();
boolean x98 = list98.contains(key98);
boolean y98 = list98.contains(value98);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key98%>"><%=key98%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x98 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value98%>" name="<%=value98%>" class="textareaenter" readonly><%=id98.toString()%></textarea>
    </td>
  </tr>
</table>
<jsp:useBean id="table99" class="Hashtable" scope="session"/>
<jsp:useBean id="id99" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="list99" class="ArrayList" scope="request"/>

<%!
public void test99()
{
  for (int i = 0; i < 10; i++)
  {
    System.out.println("This is a test...");
  }
}
%>

<%
String key99 = (String)table99.get("key");
String value99 = (String)table99.get("value");
String sessionId99 = id99.toString();
boolean x99 = list99.contains(key99);
boolean y99 = list99.contains(value99);
%>

<table width="95%" border=0 cellpadding=3 cellspacing=0>
  <tr>
    <td class="labels" height=25 valign="bottom" align="left" nowrap>
      <label for="<%=key99%>"><%=key99%></label>
    </td>
  </tr>
</table>
<table cellpadding=3 cellspacing=0 class="<%=(x99 ? "fixfragtable" : "innerfixfragtable")%>">
  <tr>
    <td>
      <textarea id="<%=value99%>" name="<%=value99%>" class="textareaenter" readonly><%=id99.toString()%></textarea>
    </td>
  </tr>
</table>
