<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationConstants" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>
<%@ attribute name="loop_num" required="true"
              type="java.lang.String" %>
<%@ attribute name="order_obejct" required="true"
              type="ua.javaexternal_shulzhenko.repair_service.models.order.Order" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="cust" %>


<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:if test="${order_obejct ne null}">
    <div ${user.role eq Role.MASTER or user.role eq Role.MANAGER ? 'class="col-md-12' : 'class="col-md-10 offset-md-1'}
            order rounded page-content">
    <div class="row">
        <div class="col-md-1">
            <span>
                 <cust:entity-page-counter loop_count_num="${loop_num}"
                                           page_num="${param.page}"
                                           entities_page_amount="${PaginationConstants.ORDERS_FOR_PAGE.amount}"/>
            </span>

        </div>
        <div ${user.role eq Role.MASTER or user.role eq Role.MANAGER ? 'class="col-md-9"' : 'class="col-md-11"'}>
            <div class="row">
                <div class="col-md-3 o"><h6>Order number</h6></div>
                <div class="col-md-7"><p>#${order_obejct.id}</p></div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-3"><h6>Customer name</h6></div>
                <div class="col-md-7"><p>${order_obejct.customer.firstName} ${order_obejct.customer.firstName}</p></div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-3"><h6>Date of ordering</h6></div>
                <div class="col-md-7"><p>${order_obejct.date}</p></div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-3"><h6>Car info</h6></div>
                <div class="col-md-7">
                    <p>${order_obejct.carBrand}, ${order_obejct.carModel}, ${order_obejct.carYearManufacture}</p></div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-3"><h6>Repair type</h6></div>
                <div class="col-md-7"><p>${order_obejct.repairType}</p></div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-3"><h6>Repair description</h6></div>
                <div class="col-md-7"><p>${order_obejct.repairDescription}</p></div>
            </div>
            <hr>
            <c:if test="${order_obejct.price ne 0}">
                <div class="row">
                    <div class="col-md-3"><h6>Order price</h6></div>
                    <div class="col-md-7"><p>${order_obejct.price}</p></div>
                </div>
                <hr>
            </c:if>
            <c:if test="${order_obejct.master.firstName ne null}">
                <div class="row">
                    <div class="col-md-3"><h6>Master info</h6></div>
                    <div class="col-md-7"><p>${order_obejct.master.firstName} ${order_obejct.master.lastName}</p></div>
                </div>
                <hr>
            </c:if>
            <c:if test="${order_obejct.repairCompletionDate ne null}">
                <div class="row">
                    <div class="col-md-3"><h6>Repair completion date</h6></div>
                    <div class="col-md-7"><p>${order_obejct.repairCompletionDate}</p></div>
                </div>
                <hr>
            </c:if>
            <div class="row">
                <div class="col-md-3"><h6>Order Status</h6></div>
                <div class="col-md-7"><p>${order_obejct.status}</p></div>
            </div>
            <c:if test="${order_obejct.managerComment}">
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6>Manager comment</h6></div>
                    <div class="col-md-7"><p>${order_obejct.managerComment}</p></div>
                </div>
            </c:if>
        </div>

        <c:if test="${user.role eq Role.MASTER}">
            <div class="col-md-2">

            </div>
        </c:if>
        <c:if test="${user.role eq Role.MANAGER}">

        </c:if>

    </div>
    </div>
</c:if>