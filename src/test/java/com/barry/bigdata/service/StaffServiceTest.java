package com.barry.bigdata.service;

import com.barry.bigdata.base.BaseTest;
import com.barry.bigdata.entity.Staff;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: hants
 * @Date: 2018-05-22 18:19
 * @Description: Staff测试服务实现
 */
public class StaffServiceTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceTest.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializableStaff() throws Exception {
        Staff staff = new Staff();
        staff.setId("0001");
        staff.setContent("this is test hbase message operate!!!");
        staff.setAvg("0001-avg");
        logger.info("=======>序列化为:"+objectMapper.writeValueAsString(staff));
    }

}

