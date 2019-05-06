package com.longer.service;

import com.longer.bean.RecordBean;

import java.util.List;

public interface RecordService {
    List<RecordBean> showAllRecord();
    public void addRecord(RecordBean recordBean);
    RecordBean selectMaxId();
    public void updateRecordById(RecordBean recordBean);
}
