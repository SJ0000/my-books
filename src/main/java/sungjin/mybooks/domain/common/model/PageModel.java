package sungjin.mybooks.domain.common.model;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageModel<T> {

    private final List<T> data;
    private final PageInfo pageInfo;

    @Builder
    public PageModel(List<T> data, int currentPage, int totalPage) {
        this.data = data;
        this.pageInfo = new PageInfo(currentPage, totalPage);
    }

}
