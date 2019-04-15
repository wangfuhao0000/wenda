package cug.wfh.wenda.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    //针对于每个线程都有自己的一个拷贝
    private static ThreadLocal<User> users = new ThreadLocal<>();

    //会针对那个线程返回对应的User
    //Map<ThreadID, User>的概念
    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
