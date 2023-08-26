package plus.axz.community.model.common.dtos;

import java.io.Serializable;
/*
 * 分页通用返回
 */
public class PageResponse extends Result implements Serializable {
    private Integer currentPage;
    private Integer size;
    private Integer total;
                                        /*当前页，每页显示条数，总条数*/
    public PageResponse(Integer currentPage, Integer size, Integer total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
    }

    public PageResponse() {

    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
