package cug.wfh.wenda.service;

import cug.wfh.wenda.dao.userDAO;
import cug.wfh.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    cug.wfh.wenda.dao.userDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
