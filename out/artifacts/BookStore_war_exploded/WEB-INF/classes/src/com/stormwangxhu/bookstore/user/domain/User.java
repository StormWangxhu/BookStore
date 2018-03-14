package com.stormwangxhu.bookstore.user.domain;

/**
 * @Description: User的领域对象
 * @Author StormWangxhu
 * @Date Created in 2018/3/11
 */
public class User {
    private String uid; //主键
    private String username;//用户名
    private String password;//密码
    private String email;//邮箱
    private String code;//状态码
    private boolean state;// 状态（已激活和未激活）

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", state=" + state +
                '}';
    }
}
