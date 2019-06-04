package tech.mopip77.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import tech.mopip77.community.community.dto.QuestionDTO;
import tech.mopip77.community.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param("offset") Integer offset,
                        @Param("size") Integer size);

    @Select("select count(*) from question")
    Integer count();

    @Select("select count(*) from question where creator = #{userId}")
    int countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> listById(@Param("userId") Integer userId,
                        @Param("offset") Integer offset,
                        @Param("size") Integer size);

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") Integer id);
}
