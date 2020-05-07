<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:choose>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_home'}">
        <h4 class="up_h">Your current orders info:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_order_history'}">
        <h4 class="up_h">Your previous orders info:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/master_home'}">
        <h4 class="up_h">Active orders that you have in progress:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/master_completed_orders'}">
        <h4 class="up_h">Your completed orders info:</h4>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${not empty orders}">
        <c:forEach var="order" items="${orders}" varStatus="status">
            <cust:order loop_num="${status.count}" order_obejct="${order}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:otherwise>
        <div class="col-md-12 order rounded page-content">
            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_home'}">
                    <h6>You haven't active orders. To create an order, click on the 'Make repair order' tab.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_order_history'}">
                    <h6>You haven't previous orders.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/master_home'}">
                    <h6>You haven't active orders.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/master_completed_orders'}">
                    <h6>You haven't completed orders.</h6>
                </c:when>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>

