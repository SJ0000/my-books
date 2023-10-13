package sungjin.mybooks.dto.response;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {

    private final List<T> data;
    private final PageInfo pageInfo;

    @Builder
    public PageResponse(List<T> data, int currentPage, int totalPage) {
        this.data = data;
        this.pageInfo = new PageInfo(currentPage, totalPage);
    }

}
