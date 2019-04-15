package cug.wfh.wenda.dao;

import cug.wfh.wenda.model.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME = " login_ticket ";
    String INSERT_FILEDS = " user_id, expired, status, ticket ";
    String SELECT_FILEDS = " id, " + INSERT_FILEDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FILEDS,
            ") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ", SELECT_FILEDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status); //为什么这个地方要有@Param注解
}
