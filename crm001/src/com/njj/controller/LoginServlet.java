package com.njj.controller;

import com.alibaba.fastjson.JSONObject;
import com.njj.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp);
        //接受登陆传过来的3个参数
        //1、修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //2、接收前端传过来的参数
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String code=req.getParameter("code");

        //3、登录的时候要验证验证码是否正确
        //3.1获取后台验证码
        HttpSession session = req.getSession();
        String codeFromSession = (String)session.getAttribute("code");
        System.out.println("codeFromSession = " + codeFromSession);
        if(!codeFromSession.equals(code)){
            //验证码错误，注意前面有了
            //向前断输入一段json，告知前端验证码错误了
            PrintWriter writer = resp.getWriter();
            Map map = new HashMap();
            map.put("code",400);
            map.put("msg","验证码不正确");
            //把map变成json
            String jsonString = JSONObject.toJSONString(map);
            writer.println(jsonString);
            writer.close();
        }else{
            //验证码正确，继续判断账号和密码
            System.out.println(" 验证码正确该判断账号和密码了");
            //可以直接 不用service层
            UserService service = new UserService();
            Map map = service.login(username, password, req);
            String jsonString = JSONObject.toJSONString(map);
            PrintWriter writer = resp.getWriter();
            writer.println(jsonString);
            writer.close();
        }
    }
}
