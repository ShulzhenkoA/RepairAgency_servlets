<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationConstants" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus" %>
<%@ attribute name="loop_num" required="true"
              type="java.lang.String" %>
<%@ attribute name="order_object" required="true"
              type="ua.javaexternal_shulzhenko.repair_service.models.order.Order" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="cust" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust_form" %>


<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:if test="${order_object ne null}">
    <div class="${(user.role eq Role.MANAGER and (requestScope['javax.servlet.forward.servlet_path'] eq '/manager_home'or
                    requestScope['javax.servlet.forward.servlet_path'] eq '/active_orders')) or
                        (user.role eq Role.MASTER and requestScope['javax.servlet.forward.servlet_path']
                        eq '/master_home') ? 'col-md-12' : 'col-md-10 offset-md-1'} order rounded page-content">
        <div class="row">
            <div class="col-md-1">
            <span>
                 <cust:entity-page-counter loop_count_num="${loop_num}"
                                           page_num="${param.page}"
                                           entities_page_amount="${PaginationConstants.ORDERS_FOR_PAGE.amount}"/>
            </span>

            </div>
            <div class="${(user.role eq Role.MANAGER and (requestScope['javax.servlet.forward.servlet_path'] eq '/manager_home'or
                    requestScope['javax.servlet.forward.servlet_path'] eq '/active_orders')) or
                        (user.role eq Role.MASTER and requestScope['javax.servlet.forward.servlet_path']
                        eq '/master_home') ? 'col-md-9' : 'col-md-11'}">
                <div class="row">
                    <div class="col-md-3 o"><h6>Order number</h6></div>
                    <div class="col-md-7"><p>#${order_object.id}</p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6>Customer name</h6></div>
                    <div class="col-md-7"><p>${order_object.customer.firstName} ${order_object.customer.firstName}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6>Date of ordering</h6></div>
                    <div class="col-md-7"><p>${order_object.date}</p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6>Car info</h6></div>
                    <div class="col-md-7">
                        <p>${order_object.carBrand}, ${order_object.carModel}, ${order_object.carYearManufacture}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6>Repair type</h6></div>
                    <div class="col-md-7"><p>${order_object.repairType}</p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-3"><h6>Repair description</h6></div>
                    <div class="col-md-7"><p>${order_object.repairDescription}</p></div>
                </div>
                <hr>
                <c:if test="${order_object.price ne 0}">
                    <div class="row">
                        <div class="col-md-3">
                            <h6><c:choose>
                                <c:when test="${order_object.status eq OrderStatus.REPAIR_COMPLETED or
                                                order_object.status eq OrderStatus.ORDER_COMPLETED}">
                                    Order price
                                </c:when>
                                <c:otherwise>Previous price</c:otherwise>
                            </c:choose></h6></div>
                        <div class="col-md-7"><p>${order_object.price}</p></div>
                    </div>
                    <hr>
                </c:if>
                <c:if test="${order_object.master.id ne 0}">
                    <div class="row">
                        <div class="col-md-3"><h6>Master info</h6></div>
                        <div class="col-md-7"><p>${order_object.master.firstName} ${order_object.master.lastName}</p>
                        </div>
                    </div>
                    <hr>
                </c:if>
                <c:if test="${order_object.repairCompletionDate ne null}">
                    <div class="row">
                        <div class="col-md-3"><h6>Repair completion date</h6></div>
                        <div class="col-md-7"><p>${order_object.repairCompletionDate}</p></div>
                    </div>
                    <hr>
                </c:if>
                <div class="row">
                    <div class="col-md-3"><h6>Order Status</h6></div>
                    <div class="col-md-7"><p>${order_object.status}</p></div>
                </div>
                <c:if test="${order_object.managerComment ne null}">
                    <hr>
                    <div class="row">
                        <div class="col-md-3"><h6>Manager comment</h6></div>
                        <div class="col-md-7"><p>${order_object.managerComment}</p></div>
                    </div>
                </c:if>
            </div>
            <c:if test="${(user.role eq Role.MANAGER and (requestScope['javax.servlet.forward.servlet_path'] eq '/manager_home'or
                            requestScope['javax.servlet.forward.servlet_path'] eq '/active_orders'))}">
                <div class="col-md-2" style="text-align: center">
                    <button type="button" class="btn" data-toggle="modal" data-target="#editModal${order_object.id}">
                        Edit order
                    </button>
                    <div class="modal fade" id="editModal${order_object.id}">
                        <cust_form:order_editing_form order_for_editing="${order_object}"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${user.role eq Role.MASTER and
            requestScope['javax.servlet.forward.servlet_path'] eq '/master_home' and
            order_object.status ne OrderStatus.REPAIR_WORK and order_object.status ne OrderStatus.REPAIR_COMPLETED}">
                <div class="col-md-2" style="text-align: center">
                    <button type="button" class="btn" data-toggle="modal" data-target="#rep_workStatus">
                        REPAIR_WORK
                    </button>
                    <div class="modal fade" id="rep_workStatus">
                        <div class="modal-dialog modal-dialog-centered modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        Setting REPAIR_WORK status
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body">
                                    Are you sure you want to set REPAIR_WORK status?
                                </div>
                                <div class="modal-footer">
                                    <form action="${pageContext.request.contextPath}/edit_status" method="post">
                                        <div class="col-sm-6 offset-sm-3 submit-button">
                                            <input type="hidden" name="status" value="${OrderStatus.REPAIR_WORK}">
                                            <input type="hidden" name="orderID" value="${order_object.id}">
                                            <button type="submit" class="btn">Yes</button>
                                        </div>
                                    </form>
                                    <button type="button" class="btn cancel-btn" data-dismiss="modal">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${user.role eq Role.MASTER and
            requestScope['javax.servlet.forward.servlet_path'] eq '/master_home' and
            order_object.status eq OrderStatus.REPAIR_WORK}">
                <div class="col-md-2" style="text-align: center">
                    <button type="button" class="btn" data-toggle="modal" data-target="#rep_compStatus">
                        COMPLETED
                    </button>
                    <div class="modal fade" id="rep_compStatus">
                        <div class="modal-dialog modal-dialog-centered modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        Setting REPAIR_COMPLETED status
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body">
                                    Are you sure you want to set REPAIR_COMPLETED status?
                                </div>
                                <div class="modal-footer">
                                    <form action="${pageContext.request.contextPath}/edit_status" method="post">
                                        <div class="col-sm-6 offset-sm-3 submit-button">
                                            <input type="hidden" name="status" value="${OrderStatus.REPAIR_COMPLETED}">
                                            <input type="hidden" name="orderID" value="${order_object.id}">
                                            <button type="submit" class="btn">Yes</button>
                                        </div>
                                    </form>
                                    <button type="button" class="btn cancel-btn" data-dismiss="modal">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</c:if>