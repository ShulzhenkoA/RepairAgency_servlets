<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="cust" %>


<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>RepairAgency</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="shortcut icon" href="#">
</head>
<body>

<header>
    <jsp:include page="common_blocks/header.jsp"/>
</header>

<div class="container-fluid" id="pageBody">
    <cust:blocks servletPath="${requestScope['javax.servlet.forward.servlet_path']}">
        <cust:single-main>
            <main class="col-sm-6 offset-sm-3">
                <jsp:include page="main_blocks/${main_block}"/>
            </main>
        </cust:single-main>
        <cust:aside-main>
            <div class="row">
                <aside class="col-sm-3">
                    <jsp:include page="aside_blocks/${aside_menu}"/>
                </aside>
                <main class="col-sm-9">
                    <jsp:include page="main_blocks/${main_block}"/>
                </main>
            </div>
        </cust:aside-main>
    </cust:blocks>
</div>

<footer>
    <jsp:include page="common_blocks/footer.jsp"/>
</footer>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
</body>
</html>