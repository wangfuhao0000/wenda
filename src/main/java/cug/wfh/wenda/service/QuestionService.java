package cug.wfh.wenda.service;

import cug.wfh.wenda.dao.QuestionDAO;
import cug.wfh.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    @Autowired SensitiveService sensitiveService;

    public int addQuestion(Question question) {
        //防止出现html标签
        question.setContent((HtmlUtils.htmlEscape(question.getContent())));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));

        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public Question selectById(int id) {
        return questionDAO.selectById(id);
    }

    public List<Question> getLatestQuestion(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
}
