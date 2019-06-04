package tech.mopip77.community.community.dto;

import lombok.Data;
import tech.mopip77.community.community.model.User;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
