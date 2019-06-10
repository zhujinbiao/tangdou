package com.lgz.cars.service;

import com.lgz.cars.pojo.User;
import com.lgz.cars.util.ResBean;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {


    User getInfoById(Integer id);
    User login(User user);

    ResBean getPage(Integer page,Integer limit,User user);

    ResBean checkUname(String username);

    ResBean update(User user, HttpSession session);

    ResBean delete(Integer id);

    User getById(Integer id);

    ResBean checkPwd(User user);


}
