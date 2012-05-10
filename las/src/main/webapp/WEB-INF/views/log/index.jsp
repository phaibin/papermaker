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
        <script type="text/javascript">
       function pageCommit(oper)
       {
           	 var currentPage =document.getElementById("page").value;
             currentPage=("add"==oper)?++currentPage:--currentPage;
             document.getElementById("page").value =currentPage;
             document.forms[0].submit();
       }
       function isDateTime(dateTime){                
    	   
    	    var reg = /^(\d{4})(-|\/)(\d{2})\2(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;  
    	    var r = dateTime.match(reg);
    	    if(r==null)return false;  
    	    var d= new Date(r[1],r[3]-1,r[4],r[5],r[6],r[7]);  
    	    return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);  
    	 }
       
       function doSubmit()
       {
    	   document.getElementById("page").value = 1;
    	   var begin =document.getElementById("begin").value;
    	   var end =document.getElementById("end").value;
    	   if(null!=begin && ""!=begin)
    	   {
    		   if(!isDateTime(begin))
    			   {
    			     alert("开始日期格式错误；格式为2012-01-30 11:37:14");
    			     return false;
    			   }
    	   }
    	   if(null!=end && ""!=end)
    	   {
    		   if(!isDateTime(end))
    			   {
    			     alert("结束日期格式错误；格式为2012-01-30 12:37:14");
    			     return false;
    			   }
    	   }
    	   return true;
       }
       
</script>
       
    </head>
    <body>
    <div>
        <a href="<c:url value="/admin/index"/>" style="left:10px;">监控日志管理</a>
    </div>
    <div>日志查询</div>
        <c:url value="/query" var="logQueryFormQueryAction"/>
        <form:form modelAttribute="logQueryForm" id="LogQueryForm" action="${logQueryFormQueryAction }" method="POST" >
            <form:hidden path="page"/>
            开始日期：<form:input path="begin" />
            结束日期：<form:input path="end" />
            日志级别：
            <form:select path="priority" >
            <c:forEach items="${priorityList }" var="item">
                <form:option value="${item }">${item }</form:option>
            </c:forEach>
            </form:select>
            系统：<form:input path="module"/>
            <input type="submit" value="查询" onclick="return doSubmit()"/>
        </form:form>
        <table  border=1 cellspacing=0   width=100% bordercolorlight=#333333 bordercolordark=#efefef top=100px>
            <th>时间</th><th>日志级别</th><th>系统</th><th>来源</th><th>分类</th><th>信息</th><th colspan="2">操作</th>
            <c:forEach var="logItem" items="${logs}" varStatus="status">
                <tr>
                    <td style="width: 180px"><fmt:formatDate value="${logItem.logTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td style="width: 100px">${logItem.priority}</td>
                    <td style="width: 100px">${logItem.module}</td>
                    <td style="width: 150px">${logItem.logFrom}</td>
                    <td style="width: 230px">${logItem.className}</td>
                    <td >${logItem.message}</td>
                    <td style="width:90px;"><a href="<c:url value="/log/${logItem.id}?date=${logItem.logTime }&priority=${logItem.priority }"/>">查看详情</a></td>
                    <td style="width:130px;"><a href="<c:url value="/associate/${logItem.id}?date=${logItem.logTime }&priority=${logItem.priority }"/>">时间关联日志</a></td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <br>
        <div style="float:right;">
            <span>共有页面${totalPage}</span>
            <span>|当前页面${logQueryForm.page}|</span>
            <span><a href="javascript:pageCommit('r')">上一页</a></span>
            <span><a href="javascript:pageCommit('add')">下一页</a></span>
         
        </div>
    </body>
</html>