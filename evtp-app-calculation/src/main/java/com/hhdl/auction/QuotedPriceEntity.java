package com.hhdl.auction;

public class QuotedPriceEntity {
    private String id;
    private Integer amount;
    private Double price;
    private Double checkPrice;
    private String quotedPriceTime;
    private int type;
    private int mark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCheckPrice() {
        return checkPrice;
    }

    public void setCheckPrice(Double checkPrice) {
        this.checkPrice = checkPrice;
    }

    public String getQuotedPriceTime() {
        return quotedPriceTime;
    }

    public void setQuotedPriceTime(String quotedPriceTime) {
        this.quotedPriceTime = quotedPriceTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "QuotedPriceEntity{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", checkPrice=" + checkPrice +
                ", quotedPriceTime='" + quotedPriceTime + '\'' +
                ", type=" + type +
                ", mark=" + mark +
                '}';
    }
}
