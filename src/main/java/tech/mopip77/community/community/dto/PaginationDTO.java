package tech.mopip77.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    /**
     * @param totalCount total question count
     * @param page current page
     * @param size amount of question per page
     */

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        int totalPage = (totalCount - 1) / size + 1;
        // 处理error page
        page = page > 0 && page <= totalPage ? page : (page <= 0 ? 1 : totalPage);
        this.page = page;

        this.totalPage = totalPage;
        showPrevious = page != 1;
        showNext = page != totalPage;


        // 插入以curPage为中心的邻域为3的page
        for (int i = page - 3; i <= page + 3; i++) {
            if (i > 0 && i <= totalPage) {
                pages.add(i);
            }
        }

        showFirstPage = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);

    }
}
