<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Repair Service home</title>
    <c:set var="language" scope="session" value="English"/>
</head>
    <body>
        <h1>Welcome to Repair Service</h1>
        <br>
        <a href="<c:url value="/registration"/>">Register</a>
        <br>
        <a href="<c:url value="/login"/>">Sign in</a>
    </body>
</html>

