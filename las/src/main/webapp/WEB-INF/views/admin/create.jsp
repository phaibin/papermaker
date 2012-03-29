<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>JJE-日志监控-监控日志管理</title>
    </head>
    <body>
    
      <h1 align="center">增加监控文件</h1>
      
        <form:form modelAttribute="monitLog" id="MonitLog" method="POST">
                   文件地址：<form:input path="path" />
                  文件类型：
            <form:select path="type" >
                <form:option value="log4j">log4j</form:option>
                <form:option value="weblogic">weblogic</form:option>
            </form:select>
            <input type="submit" value="添加" />
        </form:form>
    </body>
</html>