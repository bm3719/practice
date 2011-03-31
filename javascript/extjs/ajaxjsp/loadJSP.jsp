<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<%
 String q =request.getParameter("name");
 String str="This is JSP string loading from JSP page in ajax, loading time :";
 java.util.Date dt=new java.util.Date();
 out.print(str+dt);
%>
