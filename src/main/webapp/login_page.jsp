<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>login page</title>
</head>
<body>
    <div class="form">
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="language" value="English">
            Email <input type="text" required placeholder="email" name="email">   ${requestScope.error}<br><br>
            Password <input type="password" required placeholder="password" name="password"><br><br>
            <input type="submit" value="Submit">
        </form>
    </div>
</body>
</html>

