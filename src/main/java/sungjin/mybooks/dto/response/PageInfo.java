package sungjin.mybooks.dto.response;

import lombok.Data;

@Data
public class PageInfo {

    private long totalElements;
    private int totalPage;
    private boolean isLast;

    public PageInfo(long totalElements, int totalPage, boolean isLast) {
        this.totalElements = totalElements;
        this.totalPage = totalPage;
        this.isLast = isLast;
    }
}