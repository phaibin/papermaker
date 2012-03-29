<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>JJE-日志删除</title>
        <script type="text/javascript">
       
       function isDateTime(dateTime){                
    	    var reg = /^(\d{4})(-|\/)(\d{2})\2(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;  
    	    var r = dateTime.match(reg);
    	    if(r==null)return false;  
    	    var d= new Date(r[1],r[3]-1,r[4],r[5],r[6],r[7]);  
    	    return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);  
    	 }
       
       function doSubmit()
       {
    	   var dt =document.getElementById("dt").value;
    	   if(null!=dt && ""!=dt)
    	   {
    		   if(!isDateTime(dt))
    			   {
    			     alert("日期格式错误；格式为2012-01-30 12:37:14");
    			     return false;
    			   }
    	   }
    	   return true;
       }
       
</script>
    </head>
    <body>
        <form:form modelAttribute="logDelForm" id="LogDelForm" method="POST" >
                               日期：<form:input path="dt" />
             <input type="submit" value="删除"  onclick="return doSubmit()"/>
        </form:form>
        <di>注意：删除此日期之前的所有记录</di>
    </body>
</html>