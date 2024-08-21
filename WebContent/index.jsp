<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div align="center">
		<h1>Login to your account</h1>
		<form name="loginForm" method="post" action="loginServlet">
			Username: <input type="text" name="username"/> <br/>
			Password: <input type="password" name="password"/> <br/>
			<input type="submit" value="Login" />
		</form>
		<h1> <a href="signup.jsp">Create your account</a></h1> 
	</div>
</body>
</html>