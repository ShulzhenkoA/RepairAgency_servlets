<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>
<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="other_cust"%>
<%@ page import="ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationConstants" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>


<c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_home'}">
    <h4 class="up_h"> Your current orders info:</h4>
</c:if>
<c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_order_history'}">
    <h4 class="up_h"> Your previous orders info:</h4>
</c:if>
<c:choose>
    <c:when test="${not empty orders}">
        <c:forEach var="order" items="${orders}" varStatus="status">
            <other_cust:entity-page-counter loop_count_num="${status.count}"
                                            page_num="${param.page}"
                                            entities_page_amount="${PaginationConstants.ORDERS_FOR_USER_PAGE.amount}"/>.

            <cust:order order_obejct="${order}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:otherwise>
        <div class="col-md-12 order rounded page-content">
            <c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_home'}">
                <h6>You haven't active orders. To create an order, click on the 'Make repair
                    order' tab.</h6>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq '/user_order_history'}">
                <h6>You haven't previous orders.</h6>
            </c:if>
        </div>
    </c:otherwise>
</c:choose>

