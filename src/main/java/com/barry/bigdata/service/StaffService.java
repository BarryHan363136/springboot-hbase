package com.barry.bigdata.service;

import com.barry.bigdata.entity.Staff;
import java.util.List;

public interface StaffService {

    public void save(Staff staff);

    public List<Staff> getById(Staff staff, String id);

}
