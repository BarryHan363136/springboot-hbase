package com.barry.bigdata.service;

import com.barry.bigdata.base.BaseTest;
import com.barry.bigdata.entity.Student;
import com.barry.bigdata.model.HBaseFamilyColumn;
import com.barry.bigdata.model.HQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: hants
 * @Date: 2018-05-26 11:26
 * @Description: HbaseTemplate API
 * 参考地址:
 * https://blog.csdn.net/dreamsigel/article/details/53835013?fps=1&locationNum=8
 *
 */
public class HbaseTemplateTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(HbaseTemplateTest.class);

    @Autowired
    private ObjectMapper objectMapper;

    private Configuration configuration;

    private HbaseTemplate hbaseTemplate;

    @Before
    public void initConfiguration() throws IOException {
        this.configuration = HBaseConfiguration.create();
        this.configuration.set(HConstants.ZOOKEEPER_QUORUM, "192.168.33.128");
        this.configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181");
        this.hbaseTemplate = new HbaseTemplate();
        this.hbaseTemplate.setConfiguration(this.configuration);
        this.hbaseTemplate.setAutoFlush(true);
    }

    @After
    public void close(){
        if (this.configuration!=null){
            this.configuration.clear();
        }
    }

    /**
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

    private HQuery initExecuteModel(){
        HQuery hQuery = new HQuery();
        hQuery.setTable("t_student");
        hQuery.setRow("0002");
        List<HBaseFamilyColumn> columns = Lists.newArrayList();
        HBaseFamilyColumn hBaseColumn = new HBaseFamilyColumn();
        hBaseColumn.setFamily("personal");
        hBaseColumn.setQualifier("username");
        hBaseColumn.setValue("username-0002");
        HBaseFamilyColumn hBaseColumn2 = new HBaseFamilyColumn();
        hBaseColumn2.setFamily("personal");
        hBaseColumn2.setQualifier("names");
        hBaseColumn2.setValue("names-0002");
        HBaseFamilyColumn hBaseColumn3 = new HBaseFamilyColumn();
        hBaseColumn3.setFamily("personal");
        hBaseColumn3.setQualifier("gender");
        hBaseColumn3.setValue("gender-0002");
        HBaseFamilyColumn hBaseColumn4 = new HBaseFamilyColumn();
        hBaseColumn4.setFamily("personal");
        hBaseColumn4.setQualifier("age");
        hBaseColumn4.setValue("age-0002");

        columns.add(hBaseColumn);
        columns.add(hBaseColumn2);
        columns.add(hBaseColumn3);
        columns.add(hBaseColumn4);
        hQuery.setColumns(columns);
        return hQuery;
    }

    /**
     * 新增数据
     */
    @Test
    public void testExecute() {
        HQuery hQuery = this.initExecuteModel();
        this.hbaseTemplate.execute(hQuery.getTable(), new TableCallback<Object>() {

            @Override
            public Object doInTable(HTableInterface hTableInterface) throws Throwable {
                Put put = new Put(Bytes.toBytes(hQuery.getRow()));
                for (HBaseFamilyColumn hbaseColumn : hQuery.getColumns()){
                    put.addColumn(Bytes.toBytes(hbaseColumn.getFamily()), Bytes.toBytes(hbaseColumn.getQualifier()), Bytes.toBytes(hbaseColumn.getValue()));
                }
                hTableInterface.put(put);
                logger.info("==================>新增数据完成!!!");
                return null;
            }
        });
    }

    /**
     * 通过表名和key获取一行数据
     *
     * @param
     * @param
     * @return
     */
    private HQuery initGetResultByKeyModel(){
        HQuery hQuery = new HQuery();
        hQuery.setTable("t_student");
        hQuery.setRow("0001");
        return hQuery;
    }

    @Test
    public void testGetResultByKey(){
        Student student = new Student();
        HQuery hQuery = this.initGetResultByKeyModel();
        this.hbaseTemplate.get(hQuery.getTable(), hQuery.getRow(), new RowMapper<Object>() {

            @Override
            public Object mapRow(Result result, int i) throws Exception {
                logger.info("========testGetResultByKey result.listCells()============>"+objectMapper.writeValueAsString(result.listCells()));
                logger.info("========testGetResultByKey result.getRow()============>"+objectMapper.writeValueAsString(result.getRow()));
                logger.info("========testGetResultByKey Bytes.toString(result.getRow()============>"+Bytes.toString(result.getRow()));
                List<Cell> ceList = result.listCells();
                if (ceList==null || ceList.isEmpty()){
                    logger.info("====================>根据row-key:0001未查询到结果");
                }
//                for (Cell cell : ceList){
//                }
                return null;
            }
        });
    }


}

