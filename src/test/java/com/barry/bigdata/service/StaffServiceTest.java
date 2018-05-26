package com.barry.bigdata.service;

import com.barry.bigdata.base.BaseTest;
import com.barry.bigdata.entity.Staff;
import com.barry.bigdata.hbase.HconnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hants
 * @Date: 2018-05-22 18:19
 * @Description: Staff测试服务实现
 */
public class StaffServiceTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceTest.class);

    @Autowired
    private ObjectMapper objectMapper;

    private Configuration configuration;
    private Admin admin;

    @Before
    public void initConfiguration() throws IOException {
        this.configuration = HBaseConfiguration.create();
        this.configuration.set(HConstants.ZOOKEEPER_QUORUM, "192.168.33.128");
        this.configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181");
        //this.admin = new HBaseAdmin(configuration);
        Connection connection = ConnectionFactory.createConnection(this.configuration);
        this.admin = connection.getAdmin();
    }

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
        Admin admin = null;
        try {
            Staff staff = new Staff();
            staff.setId("0001");
            staff.setContent("this is test hbase message operate!!!");
            staff.setAvg("0001-avg");
            admin = HconnectionFactory.connection.getAdmin();
            boolean status = admin.isTableAvailable(TableName.valueOf("t_staff"));
            if (status){
                logger.info("========================>表已存在");
            }else {
                logger.warn("========================>表未创建");
            }
        } catch (IOException e) {
            logger.error("", e);
        }finally {
            if (admin!=null){
                admin.close();
            }
        }
    }

    /**
     * 创建表
     */
    @Test
    public void testCreateTable() throws IOException {
        Admin admin = null;
        try{
            String tableName = "blog";
            String[] familyNames = new String[]{"author", "contents"};
            admin = HconnectionFactory.connection.getAdmin();
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
        }finally {
            if (admin!=null){
                admin.close();
            }
        }
    }

    /**
     * 创建表
     */
    @Test
    public void testCreateTable2() throws IOException {
        Admin admin = null;
        try{
            String tableName = "blog";
            String[] familyNames = new String[]{"author", "contents"};
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "192.168.33.128");
            conf.set("hbase.zookeeper.port", "2181");
            conf.set("zookeeper.znode.parent", "/hbase");
            conf.set("hbase.master", "192.168.33.128");
            Connection connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String colName : familyNames) {
                hTableDescriptor.addFamily(new HColumnDescriptor(colName));
            }
            admin.createTable(hTableDescriptor);
        }catch (Exception e){
            logger.error("testCreateTable exception {} ", e);
        }finally {
            if (admin!=null){
                admin.close();
            }
        }
    }

    /**
     * 创建表
     */
    @Test
    public void testCreateTable3() throws IOException {
        Admin admin = null;
        try{
            String tableName = "blog";
            String[] familyNames = new String[]{"author", "contents"};
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "192.168.33.128");
            conf.set("hbase.zookeeper.port", "2181");
            conf.set("zookeeper.znode.parent", "/hbase");
            conf.set("hbase.master", "192.168.33.128");
            Connection connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
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
        }finally {
            if (admin!=null){
                admin.close();
            }
        }
    }

    /**
     * 新增数据,此种方式不是最好的,尽量使用testPutData2的方式
     */
    @Test
    public void testPutData() throws IOException {
        Table table = null;
        try {
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "192.168.33.128");
            conf.set("hbase.zookeeper.port", "2181");
            conf.set("zookeeper.znode.parent", "/hbase");
            conf.set("hbase.master", "192.168.33.128");
            Connection connection = ConnectionFactory.createConnection(conf);
            table = connection.getTable(TableName.valueOf("t_staff"));
            Put put = new Put(Bytes.toBytes("0002"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("content"), Bytes.toBytes("content-0002"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("avg"), Bytes.toBytes("avg-0002"));
            table.put(put);
            logger.info("======================>新增数据完成...");
        } catch (IOException e) {
            logger.error("testPutData2 exception {} ", e);
        }finally {
            if (table!=null){
                table.close();
            }
        }
    }

    /**
     * 新增数据,较好的操作方式
     */
    @Test
    public void testPutData2() throws IOException {
        HTable htable = null;
        try {
            String tableName = "t_staff";

            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "192.168.33.128");
            config.set("hbase.zookeeper.port", "2181");
            htable = new HTable(config, tableName);
            Put put = new Put(Bytes.toBytes("0003"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("content"), Bytes.toBytes("content-0003"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("avg"), Bytes.toBytes("avg-0003"));
            htable.put(put);
            logger.info("======================>新增数据完成...");
        } catch (IOException e) {
            logger.error("testPutData2 exception {} ", e);
        }finally {
            if (htable!=null){
                htable.close();
            }
        }
    }

    /**
     * 新增数据,较好的操作方式
     */
    @Test
    public void testPutData3() throws IOException {
        HTable hTable = null;
        try {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "192.168.33.128");
            config.set("hbase.zookeeper.port", "2181");
            hTable = new HTable(config, "t_staff");
            Put put = new Put(Bytes.toBytes("0004"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("content"), Bytes.toBytes("content-0004"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("avg"), Bytes.toBytes("avg-0004"));
            hTable.put(put);
            logger.info("======================>新增数据完成...");
        } catch (IOException e) {
            logger.error("testPutData2 exception {} ", e);
        }finally {
            if (hTable!=null){
                hTable.close();
            }
        }
    }

    /**
     * 批量新增数据,较好的操作方式
     */
    @Test
    public void testBatchPutData() throws IOException {
        HTable hTable = null;
        try {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "192.168.33.128");
            config.set("hbase.zookeeper.port", "2181");
            hTable = new HTable(config, "t_staff");
            Put put = new Put(Bytes.toBytes("0005"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("content"), Bytes.toBytes("content-0005"));
            put.add(Bytes.toBytes("staff"), Bytes.toBytes("avg"), Bytes.toBytes("avg-0005"));

            Put put2 = new Put(Bytes.toBytes("0006"));
            put2.add(Bytes.toBytes("staff"), Bytes.toBytes("content"), Bytes.toBytes("content-0006"));
            put2.add(Bytes.toBytes("staff"), Bytes.toBytes("avg"), Bytes.toBytes("avg-0006"));

            Put put3 = new Put(Bytes.toBytes("0007"));
            put3.add(Bytes.toBytes("staff"), Bytes.toBytes("content"), Bytes.toBytes("content-0007"));
            put3.add(Bytes.toBytes("staff"), Bytes.toBytes("avg"), Bytes.toBytes("avg-0007"));

            List<Put> puts = new ArrayList<Put>();
            puts.add(put);
            puts.add(put2);
            puts.add(put3);
            hTable.put(puts);
            logger.info("===========testBatchPutData===========>新增数据完成...");
        } catch (IOException e) {
            logger.error("testPutData2 exception {} ", e);
        }finally {
            if (hTable!=null){
                hTable.close();
            }
        }
    }

    /**
     * 删除数据
     */
    @Test
    public void testDeleteData() throws IOException {
        HTable hTable = null;
        try {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "192.168.33.128");
            config.set("hbase.zookeeper.port", "2181");
            hTable = new HTable(config, "t_staff");
            Delete delete = new Delete(Bytes.toBytes("0007"));
            //delete.deleteColumn(Bytes.toBytes("staff"), Bytes.toBytes("avg"));//删除列族中的avg列
            //delete.deleteFamily(Bytes.toBytes("staff"));//删除列族staff
            logger.info("======================>数据删除完成...");
            hTable.delete(delete);

        } catch (IOException e) {
            logger.error("testPutData2 exception {} ", e);
        }finally {
            if (hTable!=null){
                hTable.close();
            }
        }
    }

    /**
     * 创建表&列族&新增列&新增列数据
     * 表名: student
     * 列族<1>: family
     * 列: family:telphone
     * 列: family:address
     * 列: family:father
     * 列: family:monther
     * 列族<2>: cousor
     * 列: cousor:chinese
     * 列: cousor:mathematics
     * 列: cousor:physicalEducation
     * 列: cousor:physical
     * 列: cousor:chemistry
     * */
    @Test
    public void createTable() throws IOException {
        HTable hTable = null;
        Admin admin = null;
        try {
            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.quorum", "192.168.33.128");
            config.set("hbase.zookeeper.port", "2181");
            Connection connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();

            String tableName = "t_student";
            String[] familyNames = {"family", "cousor"};
            if (admin.tableExists(TableName.valueOf(tableName))) {
                logger.warn("===================>"+tableName+"表已存在,不能添加重复的表");
            }else {
                //通过HTableDescriptor类来描述一个表，HColumnDescriptor描述一个列族
                HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
                for (String familyName : familyNames) {
                    tableDescriptor.addFamily(new HColumnDescriptor(familyName));
                }
                admin.createTable(tableDescriptor);
                logger.info("<==========表&列族创建完成!!!>");
                /**
                 * 新增列&数据
                 * */
                Put put = new Put(Bytes.toBytes("0001"));
                put.add(Bytes.toBytes("family"), Bytes.toBytes("telphone"), Bytes.toBytes("telphone-0001"));
                put.add(Bytes.toBytes("family"), Bytes.toBytes("address"), Bytes.toBytes("address-0001"));
                put.add(Bytes.toBytes("family"), Bytes.toBytes("father"), Bytes.toBytes("father-0001"));
                put.add(Bytes.toBytes("family"), Bytes.toBytes("monther"), Bytes.toBytes("monther-0001"));
                put.add(Bytes.toBytes("cousor"), Bytes.toBytes("chinese"), Bytes.toBytes("chinese-0001"));
                put.add(Bytes.toBytes("cousor"), Bytes.toBytes("mathematics"), Bytes.toBytes("mathematics-0001"));
                put.add(Bytes.toBytes("cousor"), Bytes.toBytes("physicalEducation"), Bytes.toBytes("physicalEducation-0001"));
                put.add(Bytes.toBytes("cousor"), Bytes.toBytes("physical"), Bytes.toBytes("physical-0001"));
                put.add(Bytes.toBytes("cousor"), Bytes.toBytes("chemistry"), Bytes.toBytes("chemistry-0001"));
                List<Put> puts = new ArrayList<Put>();
                puts.add(put);
                hTable = new HTable(config, tableName);
                hTable.put(puts);
                logger.info("<==========列&列数据创建完成!!!>");
            }
        } catch (IOException e) {
            logger.error("createTable error {} ", e);
        }finally {
            if (admin!=null){
                admin.close();
            }
            if (hTable!=null){
                hTable.close();
            }
        }
    }

    /**
     * 给已存在的表增加新的列族
     * 表名: student
     * 列族<1>: family
     * 列: family:telphone
     * 列: family:address
     * 列: family:father
     * 列: family:monther
     * 列族<2>: cousor
     * 列: cousor:chinese
     * 列: cousor:mathematics
     * 列: cousor:physicalEducation
     * 列: cousor:physical
     * 列: cousor:chemistry
     * 列族<3>:personal
     * 列: personal:username
     * 列: personal:names
     * 列: personal:gender
     * 列: personal:age
     * */
    @Test
    public void addColumnFamily() throws IOException {
        HBaseAdmin admin = null;
        HTable table = null;
        try {
            String tableName = "t_student";
            Configuration conf = HBaseConfiguration.create();
            conf.set(HConstants.ZOOKEEPER_QUORUM, "192.168.33.128");
            conf.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181");
            admin = new HBaseAdmin(conf);
            table = new HTable(conf, tableName);
            HTableDescriptor descriptor = new HTableDescriptor(table.getTableDescriptor());
            descriptor.addFamily(new HColumnDescriptor(Bytes.toBytes("personal")));
            admin.disableTable(tableName);
            admin.modifyTable(Bytes.toBytes(tableName), descriptor);
            admin.enableTable(tableName);
            logger.info("<============addColumnFamily=====新增列族完成!!!>");
            Put put = new Put(Bytes.toBytes("0001"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes("username-0001"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("names"), Bytes.toBytes("names-0001"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("gender"), Bytes.toBytes("gender-0001"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("age"), Bytes.toBytes("age-0001"));
            table.put(put);
            logger.info("<============addColumnFamily=====新增列数据完成!!!>");
        } catch (Exception e) {
            logger.error("addColumnFamily error {} ", e);
        } finally {
            if (table!=null){
                table.close();
            }
            if (admin!=null){
                admin.close();
            }
        }
    }

    /**
     * 删除表
     * */
    @Test
    public void dropTable() throws IOException {
        Admin admin = null;
        try {
            String tableName = "t_student";

            //删除之前要将表disable
            if (!this.admin.isTableDisabled(TableName.valueOf(tableName))) {
                this.admin.disableTable(TableName.valueOf(tableName));
            }
            this.admin.deleteTable(TableName.valueOf(tableName));
            logger.info("<========================================表"+tableName+"删除完成");
        } catch (IOException e) {
            logger.error("dropTable error {} ", e);
        }
    }

    /**
     * 更新表中数据
     */
    @Test
    public void testModifyData() throws IOException {
        HTable hTable = null;
        try {
            hTable = new HTable(this.configuration, "t_student");
            Put put = new Put(Bytes.toBytes("0001"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes("username-0001-modify"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("names"), Bytes.toBytes("names-0001-modify"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("gender"), Bytes.toBytes("gender-0001-modify"));
            put.add(Bytes.toBytes("personal"), Bytes.toBytes("age"), Bytes.toBytes("age-0001-modify"));
            hTable.put(put);
            logger.info("======================>数据更新完成...");
        } catch (IOException e) {
            logger.error("testModifyData error {} ", e);
        }finally {
            if (hTable!=null){
                hTable.close();
            }
        }
    }

    /**
     * 表查询,根据主key来查询
     */
    @Test
    public void testQueryDataByKey() throws IOException {
        HTable hTable = null;
        try {
            hTable = new HTable(this.configuration, "t_student");

            String id = "0001";
            Get get = new Get(Bytes.toBytes(id));
            //get.addFamily(Bytes.toBytes("personal"));//指定列族
            //get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("age"));//指定列族&列名
            Result result = hTable.get(get);

            byte [] ageArr = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("age"));
            byte [] genderArr = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("gender"));
            byte [] namesArr = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("names"));
            byte [] usernameArr = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));

            String age = Bytes.toString(ageArr);
            String gender = Bytes.toString(genderArr);
            String names = Bytes.toString(namesArr);
            String username = Bytes.toString(usernameArr);

            logger.info("======================>t_student表根据id查询返回值,age:"+age+",gender:"+gender+",names:"+names+",username:"+username);
        } catch (IOException e) {
            logger.error("testQueryDataByKey {} ", e);
        }finally {
            if (hTable!=null){
                hTable.close();
            }
        }
    }

    /**
     * 查询出所有的表
     */
    @Test
    public void testQueryTablesList() throws IOException {
        try {
            HTableDescriptor[] hTableDescriptors = this.admin.listTables();
            for (HTableDescriptor hTableDescriptor : hTableDescriptors){
                String tableName = hTableDescriptor.getNameAsString();
                logger.info("=====================>tableName:"+tableName);
            }
        } catch (IOException e) {
            logger.error("testQueryTablesList error {} ", e);
        }
    }

    /**
     * hbaseTemplate工具类,执行查询、修改、新增、删除
     */
    @Test
    public void testCreateTable4(){
        HbaseTemplate hbaseTemplate = new HbaseTemplate();
        hbaseTemplate.setConfiguration(this.configuration);
        hbaseTemplate.setAutoFlush(true);
    }

    @After
    public void closeResource() throws IOException {
        if (admin!=null){
            admin.close();
        }
    }

}

