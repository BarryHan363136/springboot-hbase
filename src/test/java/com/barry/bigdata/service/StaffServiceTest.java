package com.barry.bigdata.service;

import com.barry.bigdata.base.BaseTest;
import com.barry.bigdata.entity.Staff;
import com.barry.bigdata.hbase.HbaseTable;
import com.barry.bigdata.hbase.HconnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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

    @Test
    public void testSave() throws Exception {
        Staff staff = new Staff();
        staff.setId("0001");
        staff.setContent("this is test hbase message operate!!!");
        staff.setAvg("0001-avg");
        Admin admin = HconnectionFactory.connection.getAdmin();
        boolean status = admin.isTableAvailable(TableName.valueOf("t_staff"));
        if (status){
            logger.info("========================>表已存在");
        }else {
            logger.warn("========================>表未创建");
        }
    }

    /**
     * 创建表
     */
    @Test
    public void testCreateTable() {
        try{
            String tableName = "blog";
            String[] familyNames = new String[]{"author", "contents"};
            Admin admin = HconnectionFactory.connection.getAdmin();
            TableName table = TableName.valueOf(tableName);
            if (admin.tableExists(table)) {
                logger.info(tableName + " already exists");
            } else {
                HTableDescriptor hTableDescriptor = new HTableDescriptor(table);
                for (String family : familyNames) {
                    hTableDescriptor.addFamily(new HColumnDescriptor(family));
                }
                admin.createTable(hTableDescriptor);
            }
        }catch (Exception e){
            logger.error("testCreateTable exception {} ", e);
        }
    }

    /**
     * 创建表
     */
    @Test
    public void testCreateTable2() {
        try{
            String tableName = "blog";
            String[] familyNames = new String[]{"author", "contents"};
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "192.168.33.128");
            conf.set("hbase.zookeeper.port", "2181");
            conf.set("zookeeper.znode.parent", "/hbase");
            conf.set("hbase.master", "192.168.33.128");
            Connection connection = ConnectionFactory.createConnection(conf);
            Admin admin = connection.getAdmin();
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String colName : familyNames) {
                hTableDescriptor.addFamily(new HColumnDescriptor(colName));
            }
            admin.createTable(hTableDescriptor);
        }catch (Exception e){
            logger.error("testCreateTable exception {} ", e);
        }
    }

    /**
     * 创建表
     */
    @Test
    public void testCreateTable3() {
        try{
            String tableName = "blog";
            String[] familyNames = new String[]{"author", "contents"};
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "192.168.33.128");
            conf.set("hbase.zookeeper.port", "2181");
            conf.set("zookeeper.znode.parent", "/hbase");
            conf.set("hbase.master", "192.168.33.128");
            Connection connection = ConnectionFactory.createConnection(conf);
            Admin admin = connection.getAdmin();
            boolean avail = admin.isTableAvailable(TableName.valueOf(Bytes.toBytes("tableName")));
            boolean ifexists = admin.tableExists(TableName.valueOf(tableName));
            if (ifexists){
                boolean status = admin.isTableAvailable(TableName.valueOf(tableName));
                if (status) {
                    logger.info("<============表blog可用=================>");
                } else {
                    logger.info("<============表blog不可用=================>");
                }
            }else {
                logger.info("<============表blog未创建=================>");
            }
        }catch (Exception e){
            logger.error("testCreateTable exception {} ", e);
        }
    }



}

