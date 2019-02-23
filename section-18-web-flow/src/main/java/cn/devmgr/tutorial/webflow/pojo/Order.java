package cn.devmgr.tutorial.webflow.pojo;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private String id;
    private Date payDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "; payDate=" + payDate + "}";
    }
}
