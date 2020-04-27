<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-sm navbar-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home"><img src="static/img/logo.png"
                                                                                alt="logo"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <span class="navbar-text">Агенство Ремонту Автомобілів</span>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <div class="dropdown dropleft">
                    <button type="button" class="btn dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-globe-americas"></i>
                        <div class="dropdown-menu" overflow="hidden">
                            <a class="dropdown-item" href="#"><img src="static/img/ukr.png" alt="Ukraine flag">
                                Українська</a>
                            <a class="dropdown-item" href="#"><img src="static/img/usa.png" alt="USA flag">
                                Англійська(США)</a>
                        </div>
                    </button>
                </div>
            </li>
            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] != '/login' &&
                                requestScope['javax.servlet.forward.servlet_path'] != '/registration'}">
                    <li class="nav-item">
                        <button type="button" class="btn" data-toggle="modal" data-target="#registerForm">
                            <i class="fas fa-user"></i> Реєстрація
                        </button>
                        <div class="modal" id="registerForm">
                            <jsp:include page="../snippets/registration_form.jsp"/>
                        </div>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="btn" data-toggle="modal" data-target="#loginForm"
                                aria-pressed="true">
                            <i class="fas fa-sign-in-alt"></i> Вхід
                        </button>
                        <div class="modal" id="loginForm">
                            <jsp:include page="../snippets/login_form.jsp"/>
                        </div>
                    </li>
                </c:when>
            </c:choose>
        </ul>
    </div>
</nav>
