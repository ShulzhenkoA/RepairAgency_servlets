package ua.javaexternal_shulzhenko.car_repair_agency.services.pagination;

import ua.javaexternal_shulzhenko.car_repair_agency.models.pagination.PaginationModel;

public interface Pagination {
    PaginationModel createPaginationModel(String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit);
}
