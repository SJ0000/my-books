package sungjin.mybooks.dto.response;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class PageInfo {

    private final int currentPage;
    private final int totalPage;

    public PageInfo(int currentPage, int totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

}