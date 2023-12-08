package sungjin.mybooks.domain.common.model;

import lombok.Getter;

@Getter
public class PageInfo {

    private final int currentPage;
    private final int totalPage;

    public PageInfo(int currentPage, int totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}