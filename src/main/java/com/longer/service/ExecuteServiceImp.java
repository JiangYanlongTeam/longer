package com.longer.service;

import com.longer.bean.DPFBean;
import com.longer.bean.DPFResponse;
import com.longer.core.Core;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ExecuteServiceImp implements ExecuteService{

    @Override
    public DPFResponse execute(DPFBean dpfBean,BindingResult bindingResult){
        return new Core().execute(dpfBean,bindingResult);
    }
}
