package com.barry.bigdata.model;

import com.google.common.collect.Lists;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hants
 * @Date: 2018-05-26 12:17
 * @Description: HQuery Model
 */
public class HQuery implements Serializable {

    private static final long serialVersionUID = -8290288809675927797L;

    private String table;
    private String family;
    private String qualifier;
    private String qualifierValue;
    private String row;
    private String startRow;
    private String stopRow;
    private Filter filter;
    private PageFilter pageFilter;
    private Scan scan;
    private List<HBaseFamilyColumn> columns = Lists.newArrayList();

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getQualifierValue() {
        return qualifierValue;
    }

    public void setQualifierValue(String qualifierValue) {
        this.qualifierValue = qualifierValue;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getStopRow() {
        return stopRow;
    }

    public void setStopRow(String stopRow) {
        this.stopRow = stopRow;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public PageFilter getPageFilter() {
        return pageFilter;
    }

    public void setPageFilter(PageFilter pageFilter) {
        this.pageFilter = pageFilter;
    }

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    public List<HBaseFamilyColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<HBaseFamilyColumn> columns) {
        this.columns = columns;
    }

}

