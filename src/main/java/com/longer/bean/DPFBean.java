package com.longer.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@JsonAutoDetect
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

    @Override
    public String toString() {
        return "DPFBean{" +
                "filename='" + filename + '\'' +
                ", htmlpath='" + htmlpath + '\'' +
                ", isecology='" + isecology + '\'' +
                ", loginVeri='" + loginVeri + '\'' +
                ", loginid='" + loginid + '\'' +
                ", password='" + password + '\'' +
                ", ecologyurl='" + ecologyurl + '\'' +
                '}';
    }
}
