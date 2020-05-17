package ua.javaexternal_shulzhenko.repair_service.services.pagination;

import ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationModel;

public interface Pagination {
    PaginationModel createPaginationModel(String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit);
}
