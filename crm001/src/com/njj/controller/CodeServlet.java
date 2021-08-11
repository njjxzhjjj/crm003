package com.njj.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CodeServlet",urlPatterns = "/CodeServlet")
public class CodeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp);
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        //圆圈
        //CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        //线性
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(300, 200);
        //图形验证码写出，可以写出到文件，也可以写出到流
        String code=captcha.getCode();
        HttpSession session = req.getSession();
        session.setAttribute("code",code);
       //验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
        ServletOutputStream outputStream = resp.getOutputStream();
        captcha.write(outputStream);
        outputStream.close();
    }
}
