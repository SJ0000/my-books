package sungjin.mybooks.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo {

    private int currentPage;
    private int totalPage;

    public PageInfo(int currentPage, int totalPage) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

}