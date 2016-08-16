package com.cpsh.model;

import java.util.Calendar;

public class Person {
    protected long userID;
    protected String accountID;
    protected String password;
    protected int belong;
    protected boolean permission;
    protected String suspendReason;
    protected int status;
    protected Calendar createTime;
    protected Calendar loginTime;
    /* 绑定手机 */
    protected String mobilephone;
    /* 绑定邮箱 */
    protected String email;
    
    
    
    private int id;
    private String name;
    private int age;
    private String address; 
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nname : " + this.name);
        sb.append("\nage : " + this.age);
        sb.append("\naddress : " + this.address);
        return sb.toString();
    } 
    
    
    
    public long getUserID() {
        return userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public String getAccountID() {
        return accountID;
    }
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getBelong() {
        return belong;
    }
    public void setBelong(int belong) {
        this.belong = belong;
    }
    public boolean isPermission() {
        return permission;
    }
    public void setPermission(boolean permission) {
        this.permission = permission;
    }
    public String getSuspendReason() {
        return suspendReason;
    }
    public void setSuspendReason(String suspendReason) {
        this.suspendReason = suspendReason;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Calendar getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
    public Calendar getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(Calendar loginTime) {
        this.loginTime = loginTime;
    }
    public String getMobilephone() {
        return mobilephone;
    }
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
