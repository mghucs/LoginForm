<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        text-decoration: none; /* Underline text like links */
        cursor: pointer;            /* Show pointer cursor on hover */
        font-size: inherit;         /* Inherit font size from parent */
        padding: 0;                 /* Remove default padding */
        margin: 0;                  /* Remove default margin */
  		font-family: "Times New Roman", Times, serif;
    }

    /* Add hover effect similar to links */
    .link-button:hover {
        color: darkblue;            /* Darker color on hover */
        text-decoration: underline; /* Ensure underline on hover */
    }
    
	a {
        text-decoration: none;
        transition: text-decoration 0.3s;
  		font-weight: normal;
    }

    a:hover {
        text-decoration: underline;
    }

    a:visited {
        text-decoration: none;
        color:blue;
    }
    
    input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
	  -webkit-appearance: none;
	  margin: 0;
	}
	
	input[type=number] {
	  -moz-appearance: textfield;
	}
</style>
</head>
<body>
	<div align="center">
		<h1>Welcome ${sessionScope.username}</h1>
		<h1>Your balance is <fmt:formatNumber value="${sessionScope.balance}" type="currency" currencySymbol="$" maxFractionDigits="2" minFractionDigits="2" /></h1><br/>
		<form action="BalanceServlet" method="post">
            <input type="hidden" name="username" value="${sessionScope.username}"/>
            <input type="number" name="amount" placeholder="Amount" min="1" step="any" required="true"/>
    		<button type="submit" name="deposit">Deposit</button>
    		<button type="submit" name="withdraw">Withdraw</button>
		</form>
		<c:if test="${sessionScope.insufficient}">
			<h2 style="color:red">Insufficient funds</h2> 
    		<c:remove var="insufficient"/>
		</c:if>
		<h1><a href="index.jsp">Logout</a></h1>
		<h1>
			<form action="DeleteServlet" method="post">
                <input type="hidden" name="username" value="${sessionScope.username}"/>
	    		<button type="submit" class="link-button">Delete this account</button>
			</form>
		</h1>
	</div>
</body>
</html>