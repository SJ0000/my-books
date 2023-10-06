package sungjin.mybooks.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {

    private List<T> data;
    private PageInfo pageInfo;

    @Builder
    public PageResponse(List<T> data, int currentPage, int totalPage) {
        this.data = data;
        this.pageInfo = new PageInfo(currentPage, totalPage);
    }

}
