<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="ra_language"/>

<div class="list-group sticky-top">
    <a href="#loginForm" class="list-group-item list-group-item-action"><fmt:message key="ra.aside_menu.order_repair"/></a>
    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/home'}">
            href="#contacts"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}/home#contacts"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"><fmt:message key="ra.aside_menu.contacts"/></a>
    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/home'}">
            href="#reviewsCards"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}/reviews"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"><fmt:message key="ra.aside_menu.reviews"/></a>
</div>