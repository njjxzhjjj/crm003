package com.njj.service;

import com.njj.bean.User;
import com.njj.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    //登录
public Map  login(String username, String password, HttpServletRequest request){
    Map map=new HashMap();
    //service 层要调用dao层
    UserDao dao = new UserDao();
    User userFromDB = dao.login(username, password);
    if(null==userFromDB){
        map.put("code",4001);
        map.put("msg","账户名或者密码不正确");
        return map;
    }else {
        HttpSession session=request.getSession();
        session.setAttribute("user",userFromDB);
        map.put("code",0);
        map.put("msg","登录成功");
        return map;
    }
  }

  //带参数的分页查询
    public  Map selectAllByParam(int page,int limit){
        UserDao userDao = new UserDao();
        List<User> users = userDao.selectAllByParam(page, limit);
        int i = userDao.selectCount();
        Map map = new HashMap();
        map.put("code",0);//必须和layui的JSON返回的格式一样
        map.put("msg","查询成功");
        map.put("count",i);//把数据写活
        map.put("data",users);

        //根据layui的返回的JSON 格式去封装给你的数据 如果不一样需要layui解析
        /*{
            code:0,
            msg:"",
            count:1000,
            data:[每一条数据]

        }*/

        Map map2 = new HashMap();
        map2.put("number",2001);//必须和layui的JSON返回的格式一样
        map2.put("message","数据查询成功");
        map2.put("object",map);//把数据写活


        return map2;
    }
}
