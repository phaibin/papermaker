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
        <title>关联日志</title>
    </head>
    <body>
        <table  border=1 cellspacing=0   width=100% bordercolorlight=#333333 bordercolordark=#efefef top=100px>
            <th>时间</th><th>日志级别</th><th>系统</th><th>来源</th><th>分类</th><th>信息</th><th>操作</th>
            <c:forEach var="logItem" items="${logs}" varStatus="status">
                <tr>
                    <td style="width: 180px"><fmt:formatDate value="${logItem.logTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td style="width: 100px">${logItem.priority}</td>
                    <td style="width: 100px">${logItem.module}</td>
                    <td style="width: 180px">${logItem.logFrom}</td>
                    <td style="width: 230px">${logItem.className}</td>
                    <td>${logItem.message}</td>
                    <td><a href="<c:url value="/log/${logItem.id}?date=${logItem.logTime }&priority=${logItem.priority }"/>">查看详情</a></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>