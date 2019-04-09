<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>APP信息管理平台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="${pageContext.request.contextPath }/statics/localcss/index.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/statics/css/custom.min.css" rel="stylesheet">
  </head>
  
  <body id="all"> 
      <div id="d_main">
        <h1>APP 信息管理平台</h1>
        <hr id="h1"/>
        <hr id="h2"/>
          <div id="d_menu">
	        <div class="div1">
	            <div class="d2">
		            <div class="d_logo">
		                <img src="${pageContext.request.contextPath }/statics/images/home.PNG" style="width: 50px;height: 50px;">
		            </div>
		            <span><a href="${pageContext.request.contextPath }/dev/tologin">开发者平台</a></span>
	            </div>
	        </div>
	       
	        <div class="div1">
	          <div class="d2">
	            <div class="d_logo">
	                <img src="${pageContext.request.contextPath }/statics/images/manager.PNG" style="width: 60px;height: 50px;">
	            </div>
	            <span><a href="${pageContext.request.contextPath }/manager/tologin">后台管理系统</a></span>
	          </div>
	        </div> 
	        
	        <div class="div1">
	           <div class="d2">
	            <div class="d_logo">
	                <img src="${pageContext.request.contextPath }/statics/images/man.PNG" style="width: 60px;height: 50px;">
	            </div>
	            <span><a href="#">联系我们</a></span>
	           </div>
	        </div> 
	       </div>
      </div>
  </body>
</html>
