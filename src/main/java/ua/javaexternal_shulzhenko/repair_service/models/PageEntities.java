package ua.javaexternal_shulzhenko.repair_service.models;

import java.util.List;

public class PageEntities<T> {
    private List<T> entities;
    private int entitiesTotalCount;

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getEntitiesTotalCount() {
        return entitiesTotalCount;
    }

    public void setEntitiesTotalCount(int entitiesTotalCount) {
        this.entitiesTotalCount = entitiesTotalCount;
    }
}
