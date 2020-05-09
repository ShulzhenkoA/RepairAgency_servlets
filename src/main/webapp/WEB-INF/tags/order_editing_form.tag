<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>
<%@ attribute name="order_for_editing"
              type="java.lang.Object" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:if test="${order_for_editing ne null}">
    <div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] eq '/edit_order' ? '' : ' modal-dialog-centered'}">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/edit_order" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Editing order #${order_for_editing.id}</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <c:if test="${inconsistencies.contains('price')}">
                            <p class="formError">
                                You must specify a price.
                            </p>
                        </c:if>
                        <input type="text" class="form-control" required
                               placeholder="Specify the price" name="price"
                               value="${order_for_editing.price ne null ? order_for_editing.price : 0}">
                    </div>
                    <div class="form-group">
                        <c:if test="${inconsistencies.contains('master')}">
                            <p class="formError">
                                Choose master for this order.
                            </p>
                        </c:if>
                        <select name="masterID" class="custom-select mb-3">
                            <c:choose>
                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/edit_order'}">
                                    <c:if test="${order_for_editing.masterID eq 0}">
                                        <option selected value="0">Master</option>
                                        <c:forEach var="master" items="${masters}">
                                            <option value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${order_for_editing.masterID ne 0}">
                                        <option value="0">Master</option>
                                        <c:forEach var="master" items="${masters}">
                                            <option ${order_for_editing.masterID eq master.id ? 'selected' : ''}
                                                    value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${order_for_editing.master.id eq 0}">
                                        <option selected value="0">Master</option>
                                        <c:forEach var="master" items="${masters}">
                                            <option value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${order_for_editing.master.id ne 0}">
                                        <option value="0">Master</option>
                                        <c:forEach var="master" items="${masters}">
                                            <option ${order_for_editing.master.id eq master.id ? 'selected' : ''}
                                                    value="${master.id}">${master.firstName} ${master.lastName}</option>
                                        </c:forEach>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <div class="form-group reg_radio">
                        <c:if test="${order_for_editing.status eq OrderStatus.PENDING or
                                    order_for_editing.status eq OrderStatus.CAR_WAITING or
                                    curOrderStatus eq OrderStatus.PENDING or curOrderStatus eq OrderStatus.CAR_WAITING}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.CAR_WAITING}"
                                    ${order_for_editing.status eq OrderStatus.PENDING or
                                            order_for_editing.status eq OrderStatus.CAR_WAITING ? 'checked' : ''}>${OrderStatus.CAR_WAITING}
                            </div>
                        </c:if>
                        <c:if test="${order_for_editing.status eq OrderStatus.REPAIR_WORK
                                    or curOrderStatus eq OrderStatus.REPAIR_WORK}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.REPAIR_WORK}"
                                    ${order_for_editing.status eq OrderStatus.REPAIR_WORK ? 'checked' : ''}>${OrderStatus.REPAIR_WORK}
                            </div>
                        </c:if>
                        <c:if test="${order_for_editing.status eq OrderStatus.REPAIR_COMPLETED
                                    or curOrderStatus eq OrderStatus.REPAIR_COMPLETED}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.REPAIR_COMPLETED}"
                                    ${order_for_editing.status eq OrderStatus.REPAIR_COMPLETED ? 'checked' : ''}>${OrderStatus.REPAIR_COMPLETED}
                            </div>
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status"
                                       value="${OrderStatus.ORDER_COMPLETED}"
                                    ${order_for_editing.status eq OrderStatus.ORDER_COMPLETED ? 'checked' : ''}>${OrderStatus.ORDER_COMPLETED}
                            </div>
                        </c:if>
                        <c:if test="${order_for_editing.status ne OrderStatus.REPAIR_COMPLETED and
                                    order_for_editing.status ne OrderStatus.ORDER_COMPLETED}">
                            <div class="form-check-inline">
                                <input type="radio" class="form-check-input" name="status" value="${OrderStatus.REJECTED}"
                                    ${order_for_editing.status eq OrderStatus.REJECTED ? 'checked' : ''}>${OrderStatus.REJECTED}
                            </div>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <c:if test="${inconsistencies.contains('managerComment')}">
                            <p class="formError">
                                Leave your order`s comment.
                            </p>
                        </c:if>
                        <textarea class="form-control" rows="4" required name="managerComment"
                                  placeholder="Leave a comment to the order or indicate the reason for rejection.">${order_for_editing.managerComment}</textarea>
                    </div>
                    <input type="hidden" name="editing_order_id" value="${order_for_editing.id}">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn">Edit</button>
                    <c:choose>
                        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/edit_order'}">
                            <a href="${pageContext.request.contextPath}/manager_home" class="btn">Cancel</a>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn" data-dismiss="modal">Cancel</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
        </div>
    </div>
</c:if>