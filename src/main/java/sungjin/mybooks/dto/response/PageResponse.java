package sungjin.mybooks.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {

    private List<T> data;
    private PageInfo pageInfo;

    @Builder
    public PageResponse(List<T> data, long totalElements, int totalPage, boolean isLast) {
        this.data = data;
        this.pageInfo = new PageInfo(totalElements, totalPage, isLast);
    }

    @Data
    static class PageInfo {
        private long totalElements;
        private int totalPage;
        private boolean isLast;

        public PageInfo(long totalElements, int totalPage, boolean isLast) {
            this.totalElements = totalElements;
            this.totalPage = totalPage;
            this.isLast = isLast;
        }
    }
}
