package plus.axz.community.service.Impl;

import plus.axz.community.enums.SendEmailEnum;
import plus.axz.community.exception.CustomizeErrorCode;
import plus.axz.community.exception.CustomizeException;
import plus.axz.community.mapper.UserMapper;
import plus.axz.community.model.pojo.User;
import plus.axz.community.model.UserExample;
import plus.axz.community.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
@Slf4j
public class RegisterServiceImpl {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;

    private static final String CODE_PRE = "code";

    @Value("${xiao.senderEmail}")
    private String senderEmail;
    @Value("${xiao.defaultAvatar}")
    private String defaultAvatar;

    /**
     * 发送邮件
     */
    public void sendEmail(String email, int type) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        // 使用Math函数随机生成6位数
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        if (type == SendEmailEnum.REGISTER.getCode()) {
            simpleMailMessage.setSubject("注册程序员论坛");
            simpleMailMessage.setText("【程序员论坛】验证码" + code + " 欢迎加入程序员论坛,这是一个充满活力和希望的群体！ 请在5分钟内完成注册。");
        } else if(type == SendEmailEnum.UPDATE_PWD.getCode()) {
            simpleMailMessage.setSubject("程序员论坛密码找回");
            simpleMailMessage.setText("【程序员论坛】验证码" + code + " 感谢使用程序员论坛，验证码有效期5分钟，打死也不要告诉别人。");
        }
        simpleMailMessage.setFrom(senderEmail);
        try {
            //存入redis，过期时间5分钟
            redisTemplate.opsForValue().set(CODE_PRE + email, code, 5, TimeUnit.MINUTES);
        } catch (MailException e) {
            log.error("redis存入数据出错" + e);
            throw new CustomizeException(CustomizeErrorCode.SYSTEM_ERROR);
        }
        try {
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            log.error("邮件发送出错" + e);
            throw new CustomizeException(CustomizeErrorCode.SEND_EMAIL_FAIL);
        }
    }

    /**
     * 判断验证码是否正确
     *
     * @param email
     * @param code
     * @return
     */
    public boolean checkCode(String email, Integer code) {
        Integer redisCode;
        try {
            redisCode = (Integer) redisTemplate.opsForValue().get(CODE_PRE + email);
        } catch (Exception e) {
            log.error("从redis中获取验证码失败，异常信息：" + e);
            throw new CustomizeException(CustomizeErrorCode.SEND_EMAIL_FAIL);
        }
        return redisCode != null && redisCode.equals(code);
    }

    /**
     * 删除验证码
     *
     * @param email
     */
    public void deleteCode(String email) {
        redisTemplate.delete(CODE_PRE + email);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    public Boolean checkEmail(String email) {
        String eRegEx = "^([A-Za-z0-9_\\-.])+@([A-Za-z0-9_\\-.])+\\.([A-Za-z]{2,4})$";
        return Pattern.matches(eRegEx, email);
    }

    /**
     * 校验注册信息是否合法
     *
     * @param email
     * @param password
     * @param code
     * @return
     */
    public Boolean checkInfo(String email, String password, Integer code) {
        String eRegEx = "^([A-Za-z0-9_\\-.])+@([A-Za-z0-9_\\-.])+\\.([A-Za-z]{2,4})$";
        String pRegEx = "^[a-zA-Z0-9_.-]{6,16}$";
        String cRegEx = "^[0-9]{6}$";
        return Pattern.matches(eRegEx, email)
                && Pattern.matches(pRegEx, password)
                && Pattern.matches(cRegEx, String.valueOf(code));
    }

    /**
     * 邮箱是否已注册
     *
     * @param email
     * @return
     */
    public Boolean registered(String email) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(email);
        List<User> users = userMapper.selectByExample(example);
        return users.size() > 0;
    }

    /**
     * 邮箱注册
     *
     * @param email
     * @param password
     * @return
     */
    public Boolean register(String email, String password) {
        //注册
        if (!registered(email)) {
            User record = new User();
            record.setAccountId(email);
            record.setToken(UUID.randomUUID().toString());
            record.setName(email);
            record.setGmtCreate(System.currentTimeMillis());
            record.setGmtModified(record.getGmtCreate());
            record.setAvatarUrl(defaultAvatar);
            record.setCertification(false);
            //生成盐
            String salt = CodecUtils.generateSalt();
            record.setSalt(salt);
            //对密码加密
            record.setPassword(CodecUtils.md5Hex(password, salt));
            boolean b = this.userMapper.insertSelective(record) == 1;
            if (b) {
                //删除验证码
                deleteCode(email);
                return true;
            }
        }
        return false;
    }
}
