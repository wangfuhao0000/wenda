package cug.wfh.wenda.dao;

import cug.wfh.wenda.model.Comment;
import cug.wfh.wenda.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FILEDS = " from_id, to_id, content, created_date, has_read, conversation_id ";
    String SELECT_FILEDS = " id, " + INSERT_FILEDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FILEDS,
            ") values(#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FILEDS, " from ", TABLE_NAME,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    //select *, count(id) as cnt from (select * from message order by created_date desc) tt group by conversation_id order by created_date desc limit 2, 2;
    @Select({"select ", INSERT_FILEDS, " , count(id) as id from ( select * from ", TABLE_NAME,
            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt ",
            " group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({" select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);
}
