package com.barry.bigdata.service.impl;

import com.barry.bigdata.dao.StaffDao;
import com.barry.bigdata.entity.Staff;
import com.barry.bigdata.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: hants
 * @Date: 2018-05-22 17:47
 * @Description: hbase数据存储服务实现
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDao staffDao;

    @Override
    public void save(Staff staff) {
        staffDao.save(staff);
    }

    @Override
    public List<Staff> getById(Staff staff, String id) {
        return staffDao.getById(staff, id);
    }


}

