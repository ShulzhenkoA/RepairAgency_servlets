<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>RepairAgency</title>
    <link rel="stylesheet" href="static/css/bootstrap.css">
    <link rel="stylesheet" href="static/css/all.min.css">
    <link rel="stylesheet" href="static/css/main.css">
</head>
<body>
<header>
    <jsp:include page="common_blocks/header.jsp"/>
</header>

<div class="container-fluid" id="pageBody">
    <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/login' ||
                        requestScope['javax.servlet.forward.servlet_path'] == '/registration'}">
            <div class="col-sm-12">
                <div class="col-sm-6 offset-sm-3">
                    <jsp:include page="snippets/${main_block}"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <aside class="col-sm-3">
                    <jsp:include page="aside_blocks/${aside_menu}"/>
                </aside>
                <main class="col-sm-9">
                    <jsp:include page="main_blocks/${main_block}"/>
                </main>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<footer>
    <jsp:include page="common_blocks/footer.jsp"/>
</footer>
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
</body>
</html>