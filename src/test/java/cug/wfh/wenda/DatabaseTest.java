package cug.wfh.wenda;

import cug.wfh.wenda.dao.QuestionDAO;
import cug.wfh.wenda.dao.userDAO;
import cug.wfh.wenda.model.Question;
import cug.wfh.wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class DatabaseTest {

    @Autowired
    userDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Test
    public void testUerDao() {
        Random random = new Random();

        for(int i = 0; i < 11; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
        }
    }

    @Test
    public void testQuestionDao() {

        for (int i = 0; i < 11; i++) {
            Question question = new Question();
            question.setCommentCount(1);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600);
            question.setCreateDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("User%d said ....",i));
            questionDAO.addQuestion(question);
        }
        System.out.println(questionDAO.selectLatestQuestions(0, 0, 10));
    }

}
