package com.barry.bigdata.utils;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * @Auther: hants
 * @Date: 2018-05-22 15:14
 * @Description: hbase api test
 */
public class HbaseClientTest {

    private static final Logger logger = LoggerFactory.getLogger(HbaseClientTest.class);

    /**
     * 第1步：实例化HBaseAdmin
     * 第2步：创建TableDescriptor
     * 第3步：通过执行管理
     */
    @Test
    public void testInitConfigure() {
        try {
            //System.setProperty("hadoop.home.dir ", "/opt/software/hadoop");
            //1.获得Configuration实例并进行相关设置
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum", "192.168.33.128:2181");
            configuration.set("hbase.master", "192.168.33.128:16010");
            //2.获得Connection实例
            Connection connection = ConnectionFactory.createConnection(configuration);
            //3.1获得Admin接口
            Admin admin = connection.getAdmin();
            //3.2获得Table接口,需要传入表名
            Table table = connection.getTable(TableName.valueOf("emp"));
        } catch (Exception e) {
            logger.error("testInitConfigure exception {} ", e);
        }
    }

}

