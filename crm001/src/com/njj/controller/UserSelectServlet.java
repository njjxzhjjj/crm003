package com.njj.controller;

import com.alibaba.fastjson.JSONObject;
import com.njj.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserSelectServlet",urlPatterns = "/UserSelectServlet")
public class UserSelectServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受登陆传过来的3个参数
        //1、修正编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //2、接收前端传过来的参数
        String page=req.getParameter("page");
        String limit=req.getParameter("limit");

        //3、调佣service
        UserService userService = new UserService();
        Map map=userService.selectAllByParam(Integer.parseInt(page),Integer.parseInt(limit));

        //4、把map变成json
        String jsonString = JSONObject.toJSONString(map);

        //5、使用流输出
        PrintWriter writer = resp.getWriter();
        writer.println(jsonString);
        writer.close();
    }
}
