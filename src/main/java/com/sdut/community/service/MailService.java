package com.sdut.community.service;

import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.domain.User;
import com.sdut.community.model.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserMapper userMapper;

    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 根据前端输入的邮箱，发送验证码
     * @param email
     * @param session
     * @return
     */
    public boolean sendMimeMail( String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            //主题
            mailMessage.setSubject("验证码邮件");
            //生成随机数
            String code = randomCode();

            //将随机数放置到session中
            session.setAttribute("email",email);
            session.setAttribute("code",code);
            //内容
            mailMessage.setText("您收到的验证码是："+code);
            //发给谁
            mailMessage.setTo(email);
            //你自己的邮箱
            mailMessage.setFrom(from);
            //发送
            mailSender.send(mailMessage);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 随机生成6位数的验证码
     * @return String code
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 检验验证码是否一致
     * @param userVo
     * @param session
     * @return
     */
    public boolean registered(UserVo userVo, HttpSession session){
        //获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");
        //获取表单中的提交的验证信息
        String voCode = userVo.getCode();
        //如果email数据为空，或者不一致，注册失败
        if (email == null || email.isEmpty()){
            return false;
        }else if (!code.equals(voCode)){
            return false;
        }
        //保存数据
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setEmail(userVo.getEmail());
        user.setPassword(userVo.getPassword());
        //将数据写入数据库
        userMapper.insertUser(user);
        //跳转成功页面
        return true;
    }

    /**
     * 通过输入email查询password，然后比较两个password，如果一样，登录成功
     * @param email
     * @param password
     * @return
     */
    public boolean loginIn(String email, String password){

        User user = userMapper.selectUserByEmail(email);

        if(!user.getPassword().equals(password)){
            return false;
        }
        return true;
    }


}
