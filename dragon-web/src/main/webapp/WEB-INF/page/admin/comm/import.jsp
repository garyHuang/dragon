<%@page import="com.dragon.utils.ContextUtils"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String basePath = ContextUtils.getBasePath();
request.setAttribute("basePath" , basePath) ;
String str = org.dragon.utils.ConvertUtils.toStr(session.getAttribute("time")); 
session.setAttribute("time" , "?t=1") ;
%>
<base href="${basePath}"/>
<title>dragon</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/themes/default/easyui.css${time}">
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/themes/icon.css${time}">
<script type="text/javascript" src="<%=basePath%>static/jquery.min.js${time}"></script>
<script type="text/javascript" src="<%=basePath%>static/jquery.easyui.min.js${time}"></script>
<script type="text/javascript" src="<%=basePath%>static/easyui-lang-zh_CN.js${time}"></script>
<script type="text/javascript" src="<%=basePath%>static/dragon.js${time}"></script>
<script>
var basePath="${basePath}";
</script>