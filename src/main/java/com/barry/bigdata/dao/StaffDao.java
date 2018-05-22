package com.barry.bigdata.dao;

import com.barry.bigdata.entity.Staff;
import com.barry.bigdata.hbase.HBaseDaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:
 * @Descriptions:
 * @Date: Created in 2018/3/21
 */
@Component("staffDao")
public class StaffDao {

    @Autowired
    private HBaseDaoUtil hBaseDaoUtil;

    /**
     * @Descripton:
     * @param staff
     * @Date: 2018/3/22
     */
    public void save(Staff staff) {
        hBaseDaoUtil.save(staff);
    }

    /**
     * @Descripton:
     * @param staff
     * @param id
     * @Date: 2018/3/22
     */
    public List<Staff> getById(Staff staff, String id) {
        return hBaseDaoUtil.get(staff, id);
    }

}
