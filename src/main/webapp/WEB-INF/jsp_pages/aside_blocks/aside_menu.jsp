<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<div class="list-group sticky-top">

    <c:choose>
        <c:when test="${user.role eq Role.REGISTERED_USER}">
            <a href="${pageContext.request.contextPath}/user_home"
               class="list-group-item list-group-item-action">
                <fmt:message key="cra.aside_personal_menu.user.personal_page"/></a>
            <a href="${pageContext.request.contextPath}/user_order_history"
               class="list-group-item list-group-item-action">
                <fmt:message key="cra.aside_personal_menu.user.order_history"/></a>
        </c:when>
        <c:when test="${user.role eq Role.ADMIN}">
            <a href="${pageContext.request.contextPath}/admin_home" class="list-group-item list-group-item-action">
                System info</a>
            <a href="${pageContext.request.contextPath}/man_mas_registration"
               class="list-group-item list-group-item-action">Register manager/master</a>
        </c:when>
        <c:when test="${user.role eq Role.MASTER}">
            <a href="${pageContext.request.contextPath}/master_home" class="list-group-item list-group-item-action">
                Active orders</a>
            <a href="${pageContext.request.contextPath}/master_completed_orders"
               class="list-group-item list-group-item-action">Completed orders</a>
        </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${user.role eq Role.UNKNOWN or user.role eq Role.REGISTERED_USER}">
            <a href="${pageContext.request.contextPath}/create_order"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_menu.create_order"/></a>
        </c:when>
    </c:choose>
    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/home'}">
            href="#contacts"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}/home#contacts"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"><fmt:message key="cra.aside_menu.contacts"/></a>

    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/home'}">
            href="#reviewsCards"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}/reviews"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"><fmt:message key="cra.aside_menu.reviews"/></a>
</div>