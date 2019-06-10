package com.lgz.cars.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgz.cars.mapper.UserMapper;
import com.lgz.cars.pojo.User;
import com.lgz.cars.service.UserService;
import com.lgz.cars.util.MD5Util;
import com.lgz.cars.util.MyUtils;
import com.lgz.cars.util.ResBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getInfoById(Integer id) {

        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public ResBean getPage(Integer page, Integer limit, User user) {
        PageHelper.startPage(page,limit);
        List<User> list=userMapper.getPage(user);
        PageInfo<User> pageInfo=new PageInfo<>(list);

        return new ResBean(0,"暂无数据",pageInfo.getTotal(),list);
    }

    @Override
    public ResBean checkUname(String username) {
        User user=userMapper.checkUname(username);
        if (user==null){
            return new ResBean(0);
        }
        return new ResBean(1);
    }

    @Override
    public ResBean update(User user, HttpSession session) {
        Integer result=0;
        User loginUser= (User) session.getAttribute("loginUser");
        if (StringUtils.isNotEmpty(user.getPassword())){
            user.setPassword(MD5Util.MD5(user.getPassword()));
        }
        if (user.getId()==null){
            user.setCreateAdmin(loginUser.getRealname());
            user.setCreateDate(MyUtils.getNowTime());
            result=userMapper.insertSelective(user);
        }else{
            user.setUpdateAdmin(loginUser.getRealname());
            user.setUpdateDate(MyUtils.getNowTime());
            result=userMapper.updateByPrimaryKeySelective(user);
        }
        if (result>0){
            return new ResBean(1,"更新成功");
        }else{
            return new ResBean(0,"更新失败");
        }
    }

    @Override
    public ResBean delete(Integer id) {
        if (userMapper.deleteByPrimaryKey(id)>0){
            return new ResBean(1,"删除成功");
        }
        return new ResBean(0,"删除失败");
    }

    @Override
    public User getById(Integer id) {

        return userMapper.selectByPrimaryKey(id);
    }
}
