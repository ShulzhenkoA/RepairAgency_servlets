package ua.javaexternal_shulzhenko.repair_service.utils.pagination;

public final class Page {

    private String pageUri;
    private int pageNum;

    public Page(String pageUri, int pageNum) {
        this.pageUri = pageUri;
        this.pageNum = pageNum;
    }

    public String getPageUri() {
        return pageUri;
    }

    public int getPageNum() {
        return pageNum;
    }

    public boolean isEllipsis() {
        return pageUri.contains("ellipsis");
    }

    public boolean isCurrent() {
        return pageUri.contains("current");
    }
}
