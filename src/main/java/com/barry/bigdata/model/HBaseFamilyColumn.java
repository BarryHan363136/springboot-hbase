package com.barry.bigdata.model;

import java.io.Serializable;

/**
 * @Auther: hants
 * @Date: 2018-05-26 12:15
 * @Description: HBaseColumn model
 */
public class HBaseFamilyColumn implements Serializable {

    private static final long serialVersionUID = -7772460792190619805L;

    private String family;
    private String qualifier;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

