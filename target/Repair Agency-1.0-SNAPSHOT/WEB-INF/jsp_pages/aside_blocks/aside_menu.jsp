<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="list-group sticky-top">
    <a href="#loginForm" class="list-group-item list-group-item-action"> Замовити ремонт </a>
    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/home'}">
            href="#contacts"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}/home#contacts"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"> Контакти </a>
    <a <c:choose>
        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/home'}">
            href="#reviewsCards"
        </c:when>
        <c:otherwise>
            href="${pageContext.request.contextPath}/reviews"
        </c:otherwise>
    </c:choose> class="list-group-item list-group-item-action"> Відгуки </a>
</div>