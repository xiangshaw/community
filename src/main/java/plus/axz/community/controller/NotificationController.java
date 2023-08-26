package plus.axz.community.controller;

import plus.axz.community.enums.NotificationTypeEnum;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.model.pojo.Notification;
import plus.axz.community.model.pojo.User;
import plus.axz.community.service.Impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
// 通知
@Controller
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;

    @GetMapping("/notification/{id}")
    public String readNotify(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        if (id == 0) {
            this.notificationService.readAll(user.getId());
            return "redirect:/profile/replies";
        }
        Notification notification = this.notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType().equals(notification.getType()) ||
                NotificationTypeEnum.REPLY_QUESTION.getType().equals(notification.getType())) {
            return "redirect:/question/" + notification.getTargetId();
        } else {
            throw new CustomizeException(CustomizeErrorCode.IS_NOT_LEGAL);
        }
    }
}
