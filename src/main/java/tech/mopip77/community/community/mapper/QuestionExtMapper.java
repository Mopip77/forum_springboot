package tech.mopip77.community.community.mapper;

import org.apache.ibatis.annotations.Param;
import tech.mopip77.community.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
}