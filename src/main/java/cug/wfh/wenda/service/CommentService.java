package cug.wfh.wenda.service;

import cug.wfh.wenda.dao.CommentDAO;
import cug.wfh.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentDAO commentDAO;

    @Autowired
    SensitiveService sensitiveService;

    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return commentDAO.selectCommentByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        //过滤一些不法内容
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDAO.addComment(comment) >0 ? comment.getId() : 0;
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }

    public boolean deleteComment(int commentId) {
        return commentDAO.updateStatus(commentId, 1) > 0;   //将状态设置为1即为删除
    }
}
