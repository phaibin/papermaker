<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>JJE-日志监控</title>
<link rel="stylesheet" href="<c:url value='/css/blueprint/screen.css'/>" type="text/css" media="screen, projection">
<link rel="stylesheet" href="<c:url value='/css/blueprint/print.css'/>" type="text/css" media="print">
<!--[if lt IE 8]>
    <link rel="stylesheet" href="<c:url value='css/blueprint/ie.css'/>" type="text/css" media="screen, projection">
  <![endif]-->
<link rel="stylesheet" href="<c:url value='/css/ulTable.css'/>" type="text/css" media="screen, projection">
</head>
<body>
    <div id="content">
        <ul class="menu">
            <li>时间</li>
            <li>日志级别</li>
            <li>系统</li>
            <li>来源</li>
            <li>分类</li>
            <li>信息</li>
            <li>操作</li>
        </ul>
        <c:forEach var="logItem" items="${logs}" varStatus="status">
        <ul class="menu">
          <li><fmt:formatDate value="${logItem.logTime}" pattern="yyyy-MM-dd HH:mm:ss" /></li>
          <li>${logItem.priority}</li>
          <li>${logItem.module}</li>
          <li>${logItem.logFrom}</li>
          <li>${logItem.className}</li>
          <li>${fn:substring(logItem.message, 0, 150)}</li>
          <li><a href="<c:url value="/log/${logItem.id}?date=${logItem.logTime }&priority=${logItem.priority }"/>">查看详情</a></li>
          <li><a href="<c:url value="/associate/${logItem.id}?date=${logItem.logTime }&priority=${logItem.priority }"/>">时间关联日志</a></li>
        </ul>
        </c:forEach>
       	<a id="next" href="<c:url value='${nextPage}'/>">next page?</a>
    </div>
</body>
</html>
    