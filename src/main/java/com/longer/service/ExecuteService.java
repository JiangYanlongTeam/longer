package com.longer.service;

import com.longer.bean.DPFBean;
import com.longer.bean.DPFResponse;
import org.springframework.validation.BindingResult;

public interface ExecuteService {
    public DPFResponse execute(DPFBean dpfBean,BindingResult bindingResult);
}
