<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="ra_language"/>

<nav class="navbar navbar-expand-sm navbar-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/home"><img src="static/img/logo.png"
                                                                                alt="logo"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <span class="navbar-text"><fmt:message key="ra.header.site_title"/></span>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <div class="dropdown dropleft">
                    <button type="button" class="btn dropdown-toggle" data-toggle="dropdown">
                        <i class="fas fa-globe-americas"></i>
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/language?lang=uk&prevURL=${requestScope['javax.servlet.forward.request_uri']}${page != null ? '&page='.concat(page) : ''}">
                            <img src="static/img/ukr.png" alt="Ukraine flag"> <fmt:message key="ra.header.lang_uk"/>
                        </a>
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/language?lang=en&prevURL=${requestScope['javax.servlet.forward.request_uri']}${page != null ? '&page='.concat(page) : ''}">
                            <img src="static/img/usa.png" alt="USA flag"> <fmt:message key="ra.header.lang_en"/>
                        </a>
                    </div>
                </div>
            </li>
            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] != '/login' &&
                                requestScope['javax.servlet.forward.servlet_path'] != '/registration'}">
                    <li class="nav-item">
                        <button type="button" class="btn" data-toggle="modal" data-target="#registerForm">
                            <i class="fas fa-user"></i> <fmt:message key="ra.header.registration"/>
                        </button>
                        <div class="modal" id="registerForm">
                            <jsp:include page="../snippets/registration_form.jsp"/>
                        </div>
                    </li>
                    <li class="nav-item">
                        <button type="button" class="btn" data-toggle="modal" data-target="#loginForm"
                                aria-pressed="true">
                            <i class="fas fa-sign-in-alt"></i> <fmt:message key="ra.header.login"/>
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
