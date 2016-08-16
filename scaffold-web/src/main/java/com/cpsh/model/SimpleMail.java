package com.cpsh.model;

import java.io.Serializable;

public class SimpleMail implements Serializable {
    // 定义发件人、收件人、主题等
    private String to ;
    private String cc;
    private String bcc ;
    private String from ;
    private String host ;
    private String subject;
    private String content;
    private String filename ;
    
    
    // 2011-05-30 txq : 
    private String hostName;  //主机名
    private String name;  //用户名
    private String pwd;//密码
    
    public String getHostName() {
        return hostName;
    }





    public void setHostName(String hostName) {
        this.hostName = hostName;
    }





    public String getName() {
        return name;
    }





    public void setName(String name) {
        this.name = name;
    }





    public String getPwd() {
        return pwd;
    }





    public void setPwd(String pwd) {
        this.pwd = pwd;
    }





    /**
     * @return Returns the bcc.
     */
    public String getBcc() {
        return bcc;
    }





    /**
     * @param bcc The bcc to set.
     */
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }





    /**
     * @return Returns the cc.
     */
    public String getCc() {
        return cc;
    }





    /**
     * @param cc The cc to set.
     */
    public void setCc(String cc) {
        this.cc = cc;
    }





    /**
     * @return Returns the content.
     */
    public String getContent() {
        return content;
    }





    /**
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }





    /**
     * @return Returns the filename.
     */
    public String getFilename() {
        return filename;
    }





    /**
     * @param filename The filename to set.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }





    /**
     * @return Returns the from.
     */
    public String getFrom() {
        return from;
    }





    /**
     * @param from The from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }





    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }





    /**
     * @param host The host to set.
     */
    public void setHost(String host) {
        this.host = host;
    }





    /**
     * @return Returns the subject.
     */
    public String getSubject() {
        return subject;
    }





    /**
     * @param subject The subject to set.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }





    /**
     * @return Returns the to.
     */
    public String getTo() {
        return to;
    }





    /**
     * @param to The to to set.
     */
    public void setTo(String to) {
        this.to = to;
    }





    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
