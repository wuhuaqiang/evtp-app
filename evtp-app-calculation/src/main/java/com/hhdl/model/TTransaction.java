package com.hhdl.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2018-12-17
 */
@TableName("t_transaction")
public class TTransaction extends Model<TTransaction> {

    private static final long serialVersionUID = 1L;

    @TableId("tx_id")
    private String txId;
    @TableField("block_number")
    private Integer blockNumber;
    @TableField("tx_time")
    private Date txTime;
    @TableField("tx_from")
    private String txFrom;
    @TableField("tx_to")
    private String txTo;
    @TableField("tx_value")
    private Double txValue;
    @TableField("tx_power")
    private Double txPower;


    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Date getTxTime() {
        return txTime;
    }

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }

    public String getTxFrom() {
        return txFrom;
    }

    public void setTxFrom(String txFrom) {
        this.txFrom = txFrom;
    }

    public String getTxTo() {
        return txTo;
    }

    public void setTxTo(String txTo) {
        this.txTo = txTo;
    }

    public Double getTxValue() {
        return txValue;
    }

    public void setTxValue(Double txValue) {
        this.txValue = txValue;
    }

    public Double getTxPower() {
        return txPower;
    }

    public void setTxPower(Double txPower) {
        this.txPower = txPower;
    }

    @Override
    public String toString() {
        return "TTransaction{" +
                "txId=" + txId +
                ", blockNumber=" + blockNumber +
                ", txTime=" + txTime +
                ", txFrom=" + txFrom +
                ", txTo=" + txTo +
                ", txValue=" + txValue +
                ", txPower=" + txPower +
                "}";
    }

    @Override
    protected Serializable pkVal() {
        return this.txId;
    }
}
