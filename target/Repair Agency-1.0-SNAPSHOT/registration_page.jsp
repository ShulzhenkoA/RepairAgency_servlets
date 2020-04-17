<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
    <div class="form">
        <form action="${pageContext.request.contextPath}/registration" method="post">
            <input type="hidden" name="language" value="English">
            First name <input type="text" required placeholder="first name" name="firstName"><br><br>
            Last name <input type="text" required placeholder="last name" name="lastName"><br><br>
            Email <input type="text" required placeholder="email" name="email"><br><br>
            Password <input type="password" required placeholder="password" name="password"><br><br>
            <input type="submit" value="Submit">
        </form>
    </div>
</body>
</html>

