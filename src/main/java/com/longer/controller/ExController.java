package com.longer.controller;

import com.longer.bean.DPFBean;
import com.longer.bean.DPFResponse;
import com.longer.service.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ExController {

    @Autowired
    ExecuteService executeService;

    @ResponseBody
    @RequestMapping(value ="/ex.pdf", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public DPFResponse execute2PDF(@Valid @RequestBody DPFBean dpfBean, BindingResult bindingResult) {
        return executeService.execute(dpfBean,bindingResult);
    }
}
