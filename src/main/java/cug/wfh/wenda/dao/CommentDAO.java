package cug.wfh.wenda.dao;

import cug.wfh.wenda.model.Comment;
import cug.wfh.wenda.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FILEDS = " content, user_id, created_date, entity_id, entity_type, status ";
    String SELECT_FILEDS = " id, " + INSERT_FILEDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FILEDS,
            ") values(#{content},#{userId},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select", SELECT_FILEDS, " from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
                                        @Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("id") int entityId, @Param("type") int entityType);

    @Update({" update ", TABLE_NAME, " set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
