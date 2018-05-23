package com.barry.bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author:
 * @Descriptions: 加载Hbase连接
 * @Date: Created in 2018/3/21
 */
@Component
public class HconnectionFactory implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(HconnectionFactory.class);

    @Value("${hbase.zookeeper.quorum}")
    private String zkQuorum;

    @Value("${hbase.master}")
    private String hBaseMaster;

    @Value("${hbase.zookeeper.property.clientPort}")
    private String zkPort;

    @Value("${zookeeper.znode.parent}")
    private String znode;

    private static Configuration conf = HBaseConfiguration.create();

    public static Connection connection;

    /**
     * @Bean
     *    public HbaseTemplate hbaseTemplate(@Value("${hbase.zookeeper.quorum}") String quorum,
     *                                       @Value("${hbase.zookeeper.port}") String port) {
     *        HbaseTemplate hbaseTemplate = new HbaseTemplate();
     *        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
     *        conf.set("hbase.zookeeper.quorum", quorum);
     *        conf.set("hbase.zookeeper.port", port);
     *        hbaseTemplate.setConfiguration(conf);
     *        hbaseTemplate.setAutoFlush(true);
     *        return hbaseTemplate;
     *    }
     * */

    @Override
    public void afterPropertiesSet(){
        try {
            conf.set("hbase.zookeeper.quorum", zkQuorum);
            conf.set("hbase.zookeeper.port", zkPort);
            //conf.set("zookeeper.znode.parent", znode);
            //conf.set("hbase.master", hBaseMaster);
            connection = ConnectionFactory.createConnection(conf);
            logger.info("获取connectiont连接成功!");
        } catch (IOException e) {
            logger.error("获取connectiont连接失败! {} ", e);
        }
    }
}
