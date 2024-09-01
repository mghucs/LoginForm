<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	a {
        text-decoration: none;
        transition: text-decoration 0.3s;
    }

    a:hover {
        text-decoration: underline;
    }

    a:visited {
        text-decoration: none;
        color:blue;
    }
</style>
<title>Signup Page</title>
</head>
<body>
	<div align="center">
	<h1>Create an account</h1>
	<form name="loginForm" method="post" action="SignUpServlet">
			<input type="text" name="username" placeholder="Username" style="margin:5px"/> <br/>
			<input type="password" name="password" placeholder="Password" style="margin:5px"/> <br/>
			<input type="submit" value="Create"/>
	</form>
	<c:if test="${sessionScope.created}">
		<h2 style="color:red">Username already exists</h2> 
   		<c:remove var="created"/>
	</c:if>
	<h1><a href="index.jsp">Already have an account?</a></h1>
	</div>
</body>
</html>