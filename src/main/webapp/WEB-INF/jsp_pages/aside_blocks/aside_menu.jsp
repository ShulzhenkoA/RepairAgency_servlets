<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.javaexternal_shulzhenko.repair_service.constants.Role" %>
<%@ page import="ua.javaexternal_shulzhenko.repair_service.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<div class="list-group sticky-top">
    <c:choose>
        <c:when test="${user.role eq Role.CUSTOMER}">
            <a href="${pageContext.request.contextPath}${CRAPaths.CUSTOMER_HOME}"
               class="list-group-item list-group-item-action">
                <fmt:message key="cra.aside_personal_menu.customer.personal_page"/></a>
            <a href="${pageContext.request.contextPath}${CRAPaths.CUSTOMER_ORDER_HISTORY}"
               class="list-group-item list-group-item-action">
                <fmt:message key="cra.aside_personal_menu.customer.order_history"/></a>
        </c:when>
        <c:when test="${user.role eq Role.MASTER}">
            <a href="${pageContext.request.contextPath}${CRAPaths.MASTER_HOME}" class="list-group-item list-group-item-action">
                <fmt:message key="cra.aside_personal_menu.master.active_orders"/></a>
            <a href="${pageContext.request.contextPath}${CRAPaths.MASTER_COMPLETED_ORDERS}"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_personal_menu.master.completed_order"/></a>
        </c:when>
        <c:when test="${user.role eq Role.MANAGER}">
            <a href="${pageContext.request.contextPath}${CRAPaths.MANAGER_HOME}" class="list-group-item list-group-item-action">
                <fmt:message key="cra.aside_personal_menu.manager.pending_orders"/></a>
            <a href="${pageContext.request.contextPath}${CRAPaths.ACTIVE_ORDERS}"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_personal_menu.manager.active_orders"/></a>
            <a href="${pageContext.request.contextPath}${CRAPaths.ORDER_HISTORY}"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_personal_menu.manager.order_history"/></a>
            <a href="${pageContext.request.contextPath}${CRAPaths.CUSTOMERS}"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_personal_menu.manager.cra_customers"/></a>
            <a href="${pageContext.request.contextPath}${CRAPaths.MASTERS}"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_personal_menu.manager.cra_masters"/></a>
        </c:when>
        <c:when test="${user.role eq Role.ADMIN}">
            <a href="${pageContext.request.contextPath}${CRAPaths.ADMIN_HOME}" class="list-group-item list-group-item-action">
                <fmt:message key="cra.aside_personal_menu.admin.sys_info"/></a>
            <a href="${pageContext.request.contextPath}${CRAPaths.MAN_MAS_REGISTRATION}"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_personal_menu.admin.reg_man/mas"/></a>
        </c:when>
    </c:choose>
    <c:choose>
        <c:when test="${user.role eq Role.UNKNOWN or user.role eq Role.CUSTOMER}">
            <a href="${pageContext.request.contextPath}${CRAPaths.CREATE_ORDER}"
               class="list-group-item list-group-item-action"><fmt:message key="cra.aside_menu.create_order"/></a>
        </c:when>
    </c:choose>

    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.HOME}">
            href="#reviewsCards"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}${CRAPaths.REVIEWS}"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"><fmt:message key="cra.aside_menu.reviews"/></a>

    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq CRAPaths.HOME}">
            href="#contacts"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}${CRAPaths.HOME}#contacts"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"><fmt:message key="cra.aside_menu.contacts"/></a>
</div>