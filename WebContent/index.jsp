<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
    img {
		float: none;
		max-width:230px;
		max-height:95px;
		width: auto;
		height: auto;
    }
</style>
</head>
<body>
	<div align="center">
		<img src="HostedFTP.png" width="450" height="100" border="0" />
		<h1>Login</h1>
		<form name="loginForm" method="post" action="loginServlet">
			<input type="text" name="username" placeholder="Username" value="${username}" style="margin:5px"/> <br/>
			<input type="password" name="password" placeholder="Password" style="margin:5px"/> <br/>
			<input type="submit" value="Login" />
		</form>
		<c:if test="${sessionScope.incorrect}">
			<h2 style="color:red">Incorrect username or password</h2> 
    		<c:remove var="incorrect"/>
		</c:if>
		<h1>New to Hostedftp? <a href="signup.jsp">Create an account</a></h1> 
	</div>
</body>
</html>