<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mongodb.BasicDBObject" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GB18030">
    <title>异常详情- ${log.message}</title>
</head>
<body>
<%
    request.setAttribute("vEnter", "\n");
%>

<div>
    ${log.priority}
    <br>
    <fmt:formatDate value="${log.logTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
    <br>
    ${log.message}
    <br>
    ${fn:replace(log.detail,vEnter,"<br/>")}
</div>
</body>
</html>