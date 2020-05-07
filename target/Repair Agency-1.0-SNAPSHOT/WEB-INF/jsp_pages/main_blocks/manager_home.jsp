<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>


<c:choose>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/manager_home'}">
        <h4 class="up_h">New pending orders need to be processed:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/active_orders'}">
        <h4 class="up_h">CRA active orders info:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/completed_orders'}">
        <h4 class="up_h">CRA completed orders info:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/order_history'}">
        <h4 class="up_h">CRA order history:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/customers'}">
        <h4 class="up_h">List of customers registered in CRA:</h4>
    </c:when>
    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/masters'}">
        <h4 class="up_h">List of masters registered in CRA:</h4>
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
    <c:when test="${not empty customers}">
        <c:forEach var="customer" items="${customers}" varStatus="status">
            <cust:user_info loop_num="${status.count}" user_for_mapping="${customer}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:when test="${not empty masters}">
        <c:forEach var="master" items="${masters}" varStatus="status">
            <cust:user_info loop_num="${status.count}" user_for_mapping="${master}"/>
            <br>
        </c:forEach>
        <cust:pagination pagination_model="${pgModel}"/>
    </c:when>
    <c:otherwise>
        <div class="col-md-12 order rounded page-content">
            <c:choose>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/manager_home'}">
                    <h6>There aren't new pending orders.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/active_orders'}">
                    <h6>There are no active orders in CRA.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/completerd_orders'}">
                    <h6>There are no completed orders in CRA.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/order_history'}">
                    <h6>CRA order history is empty.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/customers'}">
                    <h6>List of customers registered in CRA is empty.</h6>
                </c:when>
                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/masters'}">
                    <h6>List of masters registered in CRA is empty.</h6>
                </c:when>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>