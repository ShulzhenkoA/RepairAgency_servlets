<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="ra_language"/>

<div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] == '/registration' ? '' : ' modal-dialog-centered'}">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title"><fmt:message key="ra.reg_form.header"/></h4>
        </div>
        <div class="modal-body">
            <form action="${pageContext.request.contextPath}/registration" method="post">
                <p class="formError">
                    <c:choose>
                        <c:when test="${inconsistencies.firstName !=null}">
                            <fmt:message key="ra.reg_form.inc.f_name"/>
                        </c:when>
                    </c:choose></p>
                <div class="form-group">
                    <input type="text" class="form-control" required
                           placeholder="<fmt:message key="ra.reg_form.f_name_pl"/>" name="fName"
                           value="${prevFName}">
                </div>
                <p class="formError">
                    <c:choose>
                        <c:when test="${inconsistencies.lastName !=null}">
                            <fmt:message key="ra.reg_form.inc.l_name"/>
                        </c:when>
                    </c:choose></p>
                <div class="form-group">
                    <input type="text" class="form-control" required
                           placeholder="<fmt:message key="ra.reg_form.l_name_pl"/>" name="lName"
                           value="${prevLName}">
                </div>
                <p class="formError">
                    <c:choose>
                        <c:when test="${inconsistencies.email != null}">
                            <fmt:message key="ra.reg_form.inc.email"/>
                        </c:when>
                        <c:when test="${inconsistencies.notFreeEmail != null}">
                            <fmt:message key="ra.reg_form.inc.not_free_email"/>
                        </c:when>
                    </c:choose></p>
                <div class="form-group">
                    <input type="text" class="form-control" required placeholder="E-mail" name="email"
                           value="${prevEmail}">
                </div>
                <p class="formError">
                    <c:choose>
                        <c:when test="${inconsistencies.password !=null}">
                            <fmt:message key="ra.reg_form.inc.pass"/>
                        </c:when>
                    </c:choose></p>
                <div class="form-group">
                    <input type="password" class="form-control" required
                           placeholder="<fmt:message key="ra.reg_form.pass_pl"/>" name="pass">
                </div>
                <p class="formError">
                    <c:choose>
                        <c:when test="${inconsistencies.passwordConfirmation != null}">
                            <fmt:message key="ra.reg_form.inc.pass_conf"/>
                        </c:when>
                    </c:choose></p>
                <div class="form-group">
                    <input type="password" class="form-control" required
                           placeholder="<fmt:message key="ra.reg_form.pass_conf_pl"/>" name="passConf">
                </div>
                <input type="hidden" value="REGISTERED_USER" name="role">
                <div class="col-sm-6 offset-sm-3 submit-button">
                    <button type="submit" class="btn btn-block"><fmt:message key="ra.reg_form.reg_btn"/></button>
                </div>
            </form>
        </div>
        <div class=" modal-footer">
            <div class="col-sm-12">
                <a <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/registration'}">
                        href="${pageContext.request.contextPath}/login"
                    </c:when>
                    <c:otherwise>
                        href="" data-toggle="modal" data-target="#loginForm" data-dismiss="modal"
                    </c:otherwise>
                </c:choose>><fmt:message key="ra.reg_form.alr_reg_btn"/></a>
            </div>
        </div>
    </div>
</div>