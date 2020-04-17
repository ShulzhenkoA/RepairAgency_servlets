<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Repair Service home</title>
    <c:set var="language" scope="session" value="English"/>
</head>
    <body>
        <h1>Welcome to Personal Page</h1>
        <br><br><br>
        <h1>${sessionScope.user_name} ${sessionScope.user_role}</h1>
        <br><br><br>
        <a href="<c:url value="/exc_servlet"/>"> Generate exception</a>
    </body>
</html>
