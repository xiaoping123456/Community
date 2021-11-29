package com.sdut.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.sdut.community.annotation.UserLoginToken;
import com.sdut.community.model.domain.User;
import com.sdut.community.model.vo.UserVo;
import com.sdut.community.service.MailService;
import com.sdut.community.service.UserService;
import com.sdut.community.utils.FromTokenGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    /**
     * 发送邮件验证码
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession){
        mailService.sendMimeMail(email, httpSession);
        return "sucess";
    }

    /**
     * 注册
     * @param userVo
     * @param httpSession
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public boolean register(UserVo userVo,HttpSession httpSession){
        if(mailService.registered(userVo,httpSession)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 登录
     * @param text
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userLogin")
    public String login(@RequestParam("text")String text,
                        @RequestParam("password")String password,
                        HttpServletRequest request,
                        HttpServletResponse response){
        JSONObject jsonObject = (JSONObject) mailService.loginIn(text, password);
        String token = jsonObject.getString("token");
        if (token!=null){
            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");
            cookie.setMaxAge(3600*24*7);
            cookie.setDomain("localhost");
            response.addCookie(cookie);
            request.getSession().setAttribute("token",token);
        }
        return "redirect:/";
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //移除session
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("token");
        //移除cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setDomain("localhost");
                response.addCookie(cookie);
                break;
            }
        }
        return "redirect:/";
    }

    /**
     * 查询用户信息
     * @param request
     * @return
     */
    @RequestMapping("/findUser")
    @ResponseBody
    public User findUserById(HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        return userService.selectUserById(uid);
    }

    /**
     * 修改用户信息
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(User user,
                              HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);
        user.setId(uid);
        return userService.updateUser(user);
    }

    /**
     * 上传头像
     * @param
     * @return
     */
    @RequestMapping(value = "/multifileUpload",method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(@RequestParam("files")MultipartFile file,
                             HttpServletRequest request){
        int uid = FromTokenGet.getUidFromCookie(request);

        if(file.isEmpty()){
            return "false";
        }
        //得到上传时的文件名
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName+"-->"+size);

        //上传的文件要存储的地址
        String storagePathPrefix = "E:/FileTest/community/";
        Long time = System.currentTimeMillis();
        System.out.println(time);
        System.out.println(uid);
        //重命名
        String newName = uid + "" + time + fileName;
        System.out.println(newName);
        String path = storagePathPrefix + uid + "/headpic/";
        File dest = new File(path + newName);
        //判断父目录是否存在
        if(!dest.getParentFile().exists()){  //getParentFile() : 获得父目录
            dest.getParentFile().mkdirs();
        }
        try{
            //transferTo(dest)方法将上传文件写到服务器上指定的文件
            file.transferTo(dest);

            //文件的映射地址
            String urlPath = null;

            String urlPrefix = "http://localhost:8888/community/file/";
            urlPath = urlPrefix + uid+ "/headpic/" + newName;
            System.out.println(urlPath);
            //把文件映射地址保存到用户信息表
            User user = new User();
            user.setId(FromTokenGet.getUidFromCookie(request));
            user.setPic(urlPath);
            userService.updateUserHead(user);

            return urlPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

//    @UserLoginToken
//    @GetMapping("/getMessage")
//    @ResponseBody
//    public String getMessage() {
//        return "你已通过验证";
//    }

}
