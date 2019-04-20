package cug.wfh.wenda.dao;

import cug.wfh.wenda.model.Question;
import cug.wfh.wenda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper         //一定不要忘记mapper这个注解
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FILEDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FILEDS = " id, " + INSERT_FILEDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FILEDS,
            ") values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ", SELECT_FILEDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question selectById(int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                               @Param("offset") int offset,
                               @Param("limit") int limit);
}
