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
       <a href="<c:url value="/index"/>" >首页</a>
       <a href="<c:url value="/admin/create"/>" >添加监控</a>
       <a href="<c:url value="/admin/start"/>" >开始扫描</a>
       <a href="<c:url value="/admin/deletelog"/>" >删除日志</a>
       <div>监控列表</div>
       <table  border=1 cellspacing=0   width=100% bordercolorlight=#333333 bordercolordark=#efefef top=100px>
            <th>文件地址</th><th>日志类型</th><th >操作</th>
            <c:forEach var="logItem" items="${logs}" varStatus="status">
                <tr>
                    <td>${logItem.path}</td>
                    <td>${logItem.type}</td>
                    <td><a href="<c:url value="/admin/delete/${logItem.id}"/>">删除监控</a></td>
                </tr>
            </c:forEach>
        </table>
        
        
    </body>
</html>