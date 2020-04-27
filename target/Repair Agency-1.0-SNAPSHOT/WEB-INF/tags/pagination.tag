<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="pagination_model" required="true"
              type="ua.javaexternal_shulzhenko.repair_service.utils.pagination.PaginationModel" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${pagination_model !=null}">
    <ul class="pagination">
        <li class="pagination-previous${pagination_model.previous ? '' : ' disabled'}">
            <c:choose>
                <c:when test="${pagination_model.previous}">
                    <a href="${pagination_model.previousUri}">Previous</a>
                </c:when>
                <c:otherwise><a>Previous</a></c:otherwise>
            </c:choose>
        </li>
        <c:forEach var="page" items="${pagination_model.pages}">
            <c:choose>
                <c:when test="${page.current}">
                    <li class="current">${page.pageNum}</li>
                </c:when>
                <c:when test="${page.ellipsis}">
                    <li class="ellipsis"></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${page.pageUri}">${page.pageNum}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <li class="pagination-next${pagination_model.next ? '' : ' disabled'}">
            <c:choose>
                <c:when test="${pagination_model.next}">
                    <a href="${pagination_model.nextUri}">Next</a>
                </c:when>
                <c:otherwise><a>Next</a></c:otherwise>
            </c:choose>
        </li>
    </ul>
</c:if>

