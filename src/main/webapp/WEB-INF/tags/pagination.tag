<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="pagination_model" required="true"
              type="ua.javaexternal_shulzhenko.repair_service.utils.pagination.PaginationModel" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="ra_language"/>

<c:if test="${pagination_model !=null}">
    <ul class="pagination">
        <li class="pagination-previous${pagination_model.previous ? '' : ' disabled'}">
            <a <c:choose>
                <c:when test="${pagination_model.previous}">
                    href="${pagination_model.previousUri}"
                </c:when>
            </c:choose>>« <fmt:message key="ra.pagination.prev"/></a>
        </li>
        <c:forEach var="page" items="${pagination_model.pages}">
            <c:choose>
                <c:when test="${page.current}">
                    <li class="current">${page.pageNum}</li>
                </c:when>
                <c:when test="${page.ellipsis}">
                    <li> ...</li>
                </c:when>
                <c:otherwise>
                    <li><a href="${page.pageUri}">${page.pageNum}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <li class="pagination-next${pagination_model.next ? '' : ' disabled'}">
            <a <c:choose>
                <c:when test="${pagination_model.next}">
                    href="${pagination_model.nextUri}"
                </c:when>
            </c:choose>><fmt:message key="ra.pagination.next"/> »</a>
        </li>
    </ul>
</c:if>

