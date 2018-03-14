package com.stormwangxhu.bookstore.user.test;

import cn.itcast.commons.CommonUtils;
import com.stormwangxhu.bookstore.user.domain.User;
import com.stormwangxhu.bookstore.user.domain.UserException;
import com.stormwangxhu.bookstore.user.service.UserService;
import org.junit.Test;

/**
 * @Description:
 * @Author StormWangxhu
 * @Date Created in 2018/3/12
 */
public class testUser {
    UserService userService = new UserService();

    /**
     * 运行成功！
     */
    //    @Test
    public void testRegist(){
        User user = new User();
        user.setUid(CommonUtils.uuid());
        user.setCode(CommonUtils.uuid()+CommonUtils.uuid());
        user.setUsername("StormWangxhu");
        user.setPassword("123456");
        user.setEmail("StormWangxhu@163.com");
        user.setState(false);
        try {
            userService.regist(user);
        } catch (UserException e) {
            throw new RuntimeException("测试错误！");
        }
    }

    /**
     * 运行成功
     */
//    @Test
    public void testLogin(){
        User user = new User();
        user.setUsername("StormWangxhu");
        user.setPassword("123456");
        user.setState(true);
        try {
            userService.active("D3D48678543C492A8B35F62C79CE39012C6CBB846FD943E0A22B2E3AEA93B05E");
           User user1= userService.login(user);
           System.out.print(user1);
        } catch (UserException e) {
            throw new RuntimeException("用户登录失败！");
        }
    }

}
