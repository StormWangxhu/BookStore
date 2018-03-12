<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
		background: #4682B4; 
	}
	a {
		text-transform:none;
		text-decoration:none;
	} 
	a:hover {
		text-decoration:underline;
	}
</style>
  </head>
  
  <body>
<h1 style="text-align: center;">ITCAST书店</h1>
<div style="font-size: 10pt;">
		您好：张三&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/jsps/cart/list.jsp'/>" target="body">我的购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/jsps/order/list.jsp'/>" target="body">我的订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:alert('您已经退出');" target="_parent">退出</a>
		<br/>
		<a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">登录</a> |&nbsp; 
		<a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">注册</a>
</div>
  </body>
</html>
