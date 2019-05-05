package com.longer.bean;

import javax.validation.constraints.NotBlank;

public class DPFBean {

    @NotBlank(message = "filename不能为空")
    private String filename;
    @NotBlank(message = "htmlpath不能为空")
    private String htmlpath;

    private String isecology;
    private String loginVeri;
    private String loginid;
    private String password;
    private String ecologyurl;

    public String getEcologyurl() {
        return ecologyurl;
    }

    public void setEcologyurl(String ecologyurl) {
        this.ecologyurl = ecologyurl;
    }

    public String getIsecology() {
        return isecology;
    }

    public void setIsecology(String isecology) {
        this.isecology = isecology;
    }

    public String getLoginVeri() {
        return loginVeri;
    }

    public void setLoginVeri(String loginVeri) {
        this.loginVeri = loginVeri;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHtmlpath() {
        return htmlpath;
    }

    public void setHtmlpath(String htmlpath) {
        this.htmlpath = htmlpath;
    }
}
