<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>
<%@ attribute name="user_for_editing"
              type="java.lang.Object" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:if test="${user_for_editing ne null}">
    <div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] eq '/edit_user' ? '' : ' modal-dialog-centered'}">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/edit_user" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">User Editing</h4>
                </div>
                <div class="modal-body">
                    <c:if test="${inconsistencies.contains('firstName')}">
                        <p class="formError">
                            First name must conform
                        </p>
                    </c:if>
                    <div class="form-group">
                        <input type="text" class="form-control" required
                               placeholder="Enter first name" name="fName"
                               value="${user_for_editing.firstName}">
                    </div>
                    <c:if test="${inconsistencies.contains('lastName')}">
                        <p class="formError">
                            Last name must conform
                        </p>
                    </c:if>
                    <div class="form-group">
                        <input type="text" class="form-control" required
                               placeholder="Enter last name" name="lName"
                               value="${user_for_editing.lastName}">
                    </div>
                    <c:if test="${inconsistencies.contains('email')}">
                        <p class="formError">
                            Email must conform: 'example@mail.com'
                        </p>
                    </c:if>
                    <c:if test="${inconsistencies.contains('notFreeEmail')}">
                        <p class="formError">
                            Email must conform
                        </p>
                    </c:if>
                    <div class="form-group">
                        <input type="text" class="form-control" required placeholder="E-mail" name="email"
                               value="${user_for_editing.email}">
                    </div>
                    <div class="form-group reg_radio">
                        <div class="form-check-inline">
                            <input type="radio" class="form-check-input" name="role" value="${Role.MASTER}"
                                ${user_for_editing.role eq Role.MASTER ? 'checked' : ''}>Master
                        </div>
                        <div class="form-check-inline">
                            <input type="radio" class="form-check-input" name="role" value="${Role.MANAGER}"
                                ${user_for_editing.role eq Role.MANAGER ? 'checked' : ''}>Manager
                        </div>
                    </div>
                    <input type="hidden" name="editing_user_id" value="${user_for_editing.id}">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn">Edit</button>
                    <c:choose>
                        <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/edit_user'}">
                            <a href="${pageContext.request.contextPath}/admin_home" class="btn">Cancel</a>
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