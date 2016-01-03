/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.modell;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author jd
 */
@Entity
public class DataProxy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "bin_data")
    private double data;

    @Basic
    @Column(name = "data_time")
    private long timeStamp;

    public DataProxy() {

    }
    public DataProxy(double data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public double getData() {
        return data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setData(double data) {
        this.data = data;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
