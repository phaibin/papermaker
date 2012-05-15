<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mongodb.BasicDBObject" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GB18030">
    <title>异常详情- ${log.message}</title>
<link rel="stylesheet" href="<c:url value='/css/blueprint/screen.css'/>" type="text/css" media="screen, projection">
<link rel="stylesheet" href="<c:url value='/css/blueprint/print.css'/>" type="text/css" media="print">
<!--[if lt IE 8]>
    <link rel="stylesheet" href="<c:url value='css/blueprint/ie.css'/>" type="text/css" media="screen, projection">
  <![endif]-->
</head>
<body>
<%
    request.setAttribute("vEnter", "\n");
%>

<div class="container">
    <label>${log.priority}</label>
    <br>
    <label><fmt:formatDate value="${log.logTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
    <br>
    <label>${log.message}</label>
    <br>
    <p>${fn:replace(log.detail,vEnter,"<br/>")}</p>
</div>
<div>
${log.raw }
</div>
</body>
</html>