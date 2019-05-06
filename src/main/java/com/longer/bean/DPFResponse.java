package com.longer.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonAutoDetect
public class DPFResponse {
    private String msg;
    private String filepath;
    private String status;

    @Override
    public String toString() {
        return "DPFResponse{" +
                "msg='" + msg + '\'' +
                ", filepath='" + filepath + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
