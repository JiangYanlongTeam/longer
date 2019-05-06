package com.longer.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@JsonAutoDetect
public class RecordBean implements Serializable {
    private String id;
    private String requrl;
    private String reqAddress;
    private String reqMethod;
    private String reqClass;
    private String reqClassMethod;
    private String reqClassMethodColumn;
    private String resContent;
    private String totaltime;
    private String operator;
    private String operatedatetime;

    @Override
    public String toString() {
        return "RecordBean{" +
                "id='" + id + '\'' +
                ", requrl='" + requrl + '\'' +
                ", reqAddress='" + reqAddress + '\'' +
                ", reqMethod='" + reqMethod + '\'' +
                ", reqClass='" + reqClass + '\'' +
                ", reqClassMethod='" + reqClassMethod + '\'' +
                ", reqClassMethodColumn='" + reqClassMethodColumn + '\'' +
                ", resContent='" + resContent + '\'' +
                ", totaltime='" + totaltime + '\'' +
                ", operator='" + operator + '\'' +
                ", operatedatetime='" + operatedatetime + '\'' +
                '}';
    }
}
