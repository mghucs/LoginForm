<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome page</title>
<style>
    /* Remove default button styles */
    .link-button {
        background: none;           /* Remove the default background */
        border: none;               /* Remove the default border */
        color: blue;                /* Link color */
        text-decoration: underline; /* Underline text like links */
        cursor: pointer;            /* Show pointer cursor on hover */
        font-size: inherit;         /* Inherit font size from parent */
        padding: 0;                 /* Remove default padding */
        margin: 0;                  /* Remove default margin */
    }

    /* Add hover effect similar to links */
    .link-button:hover {
        color: darkblue;            /* Darker color on hover */
        text-decoration: underline; /* Ensure underline on hover */
    }
</style>
</head>
<body>
	<div align="center">
		<h1>Welcome ${username}</h1><br/>
		<h1>Your balance is ${balance}</h1><br/>
		<h1><a href="index.jsp">Logout</a></h1>
		<h1>
			<form action="DeleteServlet" method="post">
                <input type="hidden" name="username" value="${username}"/>
	    		<button type="submit" class="link-button">Delete this account</button>
			</form>
		</h1>
	</div>
</body>
</html>