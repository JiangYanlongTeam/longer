package com.longer.service;


import com.longer.bean.RecordBean;
import com.longer.dao.RecordDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("RecordService")
public class RecordServiceImp implements RecordService {
    private static final Logger LOGGER= LoggerFactory.getLogger(RecordServiceImp.class);

    @Resource
    private RecordDao recordDao;

    @Override
    public List<RecordBean> showAllRecord() {
        return recordDao.showAllRecord();
    }

    @Override
    public void addRecord(RecordBean recordBean) {
        recordDao.addRecord(recordBean);
    }

    @Override
    public RecordBean selectMaxId() {
        return recordDao.selectMaxID();
    }

    @Override
    public void updateRecordById(RecordBean recordBean) {
        recordDao.updateRecordById(recordBean);
    }
}
