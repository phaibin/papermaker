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
<title>JJE-日志监控</title>
<link rel="stylesheet" href="<c:url value='/css/blueprint/screen.css'/>" type="text/css" media="screen, projection">
<link rel="stylesheet" href="<c:url value='/css/blueprint/print.css'/>" type="text/css" media="print">
<!--[if lt IE 8]>
    <link rel="stylesheet" href="<c:url value='css/blueprint/ie.css'/>" type="text/css" media="screen, projection">
  <![endif]-->
<link rel="stylesheet" href="<c:url value='/css/ulTable.css'/>" type="text/css" media="screen, projection">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.infinitescroll.min.js'/>"></script>

</head>
<body>
<div class="wrap">
	<div>
		<a href="<c:url value="/admin/index"/>" style="left: 10px;">监控日志管理</a>
	</div>
	<div>日志查询</div>
	<c:url value="/query" var="logQueryFormQueryAction" />
	<form:form modelAttribute="logQueryForm" id="LogQueryForm" action="${logQueryFormQueryAction }" method="POST">
		<form:hidden path="page" />
            开始日期：<form:input path="begin" size="20" />
            结束日期：<form:input path="end" size="20" />
            日志级别：
            <form:select path="priority">
			<c:forEach items="${priorityList }" var="item">
				<form:option value="${item }">${item }</form:option>
			</c:forEach>
		</form:select>
            系统：<form:input path="module" size="5" />
		<input type="submit" value="查询" />
	</form:form>
	total records : ${totalRecord} 
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
        		<a id="next" href="<c:url value='${nextPage}'/>">
		<c:if test='${nextPage}'>
        		next page?
		</c:if>
        		</a>
	</div>
</div>
	<script type="text/javascript">
		
		  $('#content').infinitescroll({
		        navSelector     : "a#next:last",
		        nextSelector    : "a#next:last",
		        itemSelector    : "#content ul",
		        debug           : true,
		        dataType        : 'html',
		    }, function(newElements){
		    });

	</script>
</body>
</html>
