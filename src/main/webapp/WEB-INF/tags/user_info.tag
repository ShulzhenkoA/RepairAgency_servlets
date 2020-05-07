<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="sequence_number" required="true"
              type="java.lang.Integer" %>
<%@ attribute name="user_for_mapping" required="true"
              type="ua.javaexternal_shulzhenko.repair_service.models.user.User" %>


<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:if test="${user_for_mapping ne null and sequence_number ne 0}">
    <div class="col-md-12 rounded page-content">
        <div class="row">
            <div class="col-md-1">
                <span>${sequence_number}</span>
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
            <c:if test="${user.role eq 'ADMIN'}">
                <div class="col-md-3">
                    <button type="button" class="btn" data-toggle="modal" data-target="#delModal">
                        Delete user
                    </button>
                    <div class="modal fade" id="delModal">
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
                                    <form action="${pageContext.request.contextPath}/user_deleting" method="post">
                                        <div class="col-sm-6 offset-sm-3 submit-button">
                                            <input type="hidden" name="userId" value="${user_for_mapping.id}">
                                            <button type="submit" class="btn">Yes</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button type="button" class="btn" data-toggle="modal" data-target="#delModal${user_for_mapping.id}${user_for_mapping.role}">
                        Delete user
                    </button>
                    <div class="modal fade" id="delModal${user_for_mapping.id}${user_for_mapping.role}">
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
                                    <form action="${pageContext.request.contextPath}/user_deleting" method="post">
                                        <div class="col-sm-6 offset-sm-3 submit-button">
                                            <input type="hidden" name="userId" value="${user_for_mapping.id}">
                                            <button type="submit" class="btn">Yes</button>
                                        </div>
                                    </form>
                                    <button id="cancel-btn" type="button" class="btn" data-dismiss="modal">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</c:if>