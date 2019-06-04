package tech.mopip77.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tech.mopip77.community.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question limit 10")
    List<Question> list();
}
