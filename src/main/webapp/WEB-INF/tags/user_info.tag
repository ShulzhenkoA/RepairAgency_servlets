<%@ tag pageEncoding="UTF-8"%>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationConstants" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>
<%@ attribute name="loop_num" required="true"
              type="java.lang.Integer" %>
<%@ attribute name="user_for_mapping" required="true"
              type="ua.javaexternal_shulzhenko.repair_service.models.user.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/cust_tags.tld" prefix="cust"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust_form"%>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:if test="${user_for_mapping ne null}">
    <div class="col-md-12 rounded page-content">
        <div class="row">
            <div class="col-md-1">
                <cust:entity-page-counter loop_count_num="${loop_num}"
                                          page_num="${param.page}"
                                          entities_page_amount="${PaginationConstants.USERS_FOR_PAGE.amount}"/>
            </div>
            <div class="col-md-2">
                    ${user_for_mapping.firstName}
            </div>
            <div class="col-md-2">
                    ${user_for_mapping.lastName}
            </div>
            <div class="col-md-2">
                    ${user_for_mapping.email}
            </div>
            <div class="col-md-2">
                    ${user_for_mapping.role}
            </div>
            <c:if test="${user.role eq Role.ADMIN}">
                <div class="col-md-3">

                    <button type="button" class="btn" data-toggle="modal" data-target="#editModal${user_for_mapping.id}">
                        Edit user
                    </button>
                    <div class="modal fade" id="editModal${user_for_mapping.id}">
                        <cust_form:user_editing_form user_for_editing="${user_for_mapping}"/>
                    </div>

                    <button type="button" class="btn" data-toggle="modal" data-target="#delModal${user_for_mapping.id}">
                        Delete user
                    </button>
                    <div class="modal fade" id="delModal${user_for_mapping.id}">
                        <div class="modal-dialog modal-dialog-centered modal-sm">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        User Deleting
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </div>
                                <div class="modal-body">
                                    Are you sure you want to delete
                                    the ${fn:substring(user_for_mapping.firstName,0,1)}. ${user_for_mapping.lastName} ?
                                </div>
                                <div class="modal-footer">
                                    <form action="${pageContext.request.contextPath}/delete_user" method="post">
                                        <div class="col-sm-6 offset-sm-3 submit-button">
                                            <input type="hidden" name="deleting_user_id" value="${user_for_mapping.id}">
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