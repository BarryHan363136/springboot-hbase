package com.barry.bigdata.entity;

import com.barry.bigdata.hbase.HbaseColumn;
import com.barry.bigdata.hbase.HbaseTable;

/**
 * @Author:
 * @Descriptions:
 * @Date: Created in 2018/3/22
 */
@HbaseTable(tableName="t_staff")
public class Staff {

	@HbaseColumn(family="rowkey", qualifier="rowkey")
	private String id;

	@HbaseColumn(family="staff", qualifier="content")
	private String content;

	@HbaseColumn(family="staff", qualifier="avg")
	private String avg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}
}
