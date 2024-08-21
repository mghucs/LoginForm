<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Signup Page</title>
</head>
<body>
	<div align="center">
	<h1>Create a free account</h1>
	<form name="loginForm" method="post" action="SignUpServlet">
		Username: <input type="text" name="username"/> <br/>
		Password: <input type="password" name="password"/> <br/>
		<input type="submit" value="Create"/>
	</form>
	<h1> <a href="index.jsp">Go back to login</a></h1>
	</div>
</body>
</html>