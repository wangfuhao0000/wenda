package cug.wfh.wenda.controller;

import cug.wfh.wenda.model.HostHolder;
import cug.wfh.wenda.model.Message;
import cug.wfh.wenda.model.User;
import cug.wfh.wenda.model.ViewObject;
import cug.wfh.wenda.service.MessageService;
import cug.wfh.wenda.service.UserService;
import cug.wfh.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    //@ResponseBody
    public String getConversationList(Model model) {
        if (hostHolder.getUser() == null) {
            return "redirect:reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList(localUserId, 0,2);
        List<ViewObject> conversations = new ArrayList<>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            int targetID = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            vo.set("user", userService.getUser(targetID));
            vo.set("unread", messageService.getConversationUnreadCount(localUserId, message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", userService.getUser(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取消息失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "未登录");
            }
            User user = userService.selectByName(toName);       //根据用户名获得这个用户
            if (user == null) {
                return WendaUtil.getJSONString(1, "用户不存在");
            }

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送消息失败 " + e.getMessage());
            return WendaUtil.getJSONString(1, "发信失败");
        }

    }
}
