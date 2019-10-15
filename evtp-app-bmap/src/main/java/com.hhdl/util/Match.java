package com.hhdl.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Match {
    private static final Queue<QuotedPriceEntity> buyPricesQueue = new LinkedList<>();
    private static final Queue<QuotedPriceEntity> sellPricesQueue = new LinkedList<>();
    private static final List<QuotedPriceEntity> buyPriceEntitys = new ArrayList<>();
    private static final List<QuotedPriceEntity> sellPriceEntitys = new ArrayList<>();
    private static final List<Double> buyPrices = new ArrayList<>();
    private static final List<Double> sellPrices = new ArrayList<>();
    private static List<List<QuotedPriceEntity>> buySelEntitys = new ArrayList<>();
    private static Double currElectricityPrice;
    private static String currTime;
    private static final Double MIN = 0.5;
    private static final Double MAX = 1.5;
    private static final DecimalFormat df = new DecimalFormat("#.00");

    public static List<List<QuotedPriceEntity>> getBuySelEntitys() {
        return buySelEntitys;
    }

    public static void setBuySelEntitys(List<List<QuotedPriceEntity>> buySelEntitys) {
        Match.buySelEntitys = buySelEntitys;
    }

    public static void main(String[] args) {
        charge(0);
    }

    /**
     * 获取当前电价
     */
    public static void getCurrElectricityPrice() { //保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        if (MIN == MAX) {
            currElectricityPrice = Double.parseDouble(df.format(MIN));
        }
        currElectricityPrice = Double.parseDouble(df.format(MIN + ((MAX - MIN) * new Random().nextDouble())));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currTime = sDateFormat.format(new Date());
    }

    /**
     * 获取一定范围的随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static double getRandomNumber(final double min, final double max) { //保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        if (min == max) {
            return Double.parseDouble(df.format(min));
        }
        return Double.parseDouble(df.format(min + ((max - min) * new Random().nextDouble())));
    }

    public static void initPrices() {
        int buyNum = new Random().nextInt(30) + 20;
        int sellNum = new Random().nextInt(30) + 20;
        for (int i = 0; i < buyNum; i++) {
            createBuySel(0, "");
        }
        for (int i = 0; i < sellNum; i++) {
            createBuySel(1, "");
        }

    }

    /**
     * 创建一个报价对象
     *
     * @param num  区分买卖标识变量
     * @param evId 电动汽车id
     */
    public static void createBuySel(final int num, final String evId) {
        QuotedPriceEntity quotedPriceEntity = new QuotedPriceEntity();
        quotedPriceEntity.setId(evId);
        quotedPriceEntity.setType(num);
        quotedPriceEntity.setAmount((int) getRandomNumber(2.0, 25.0));
        quotedPriceEntity.setQuotedPriceTime(currTime);
        quotedPriceEntity.setMark(0);
        if (num == 0) {
            quotedPriceEntity.setPrice(getRandomNumber(Double.parseDouble(df.format(currElectricityPrice * 0.5)), Double.parseDouble(df.format(currElectricityPrice * 0.95))));
            quotedPriceEntity.setCheckPrice(currElectricityPrice);
            buyPriceEntitys.add(quotedPriceEntity);
            buyPrices.add(quotedPriceEntity.getPrice());
        } else {
            quotedPriceEntity.setPrice(getRandomNumber(Double.parseDouble(df.format(currElectricityPrice * 0.45)), Double.parseDouble(df.format(currElectricityPrice * 0.90))));
            quotedPriceEntity.setCheckPrice(currElectricityPrice);
            sellPriceEntitys.add(quotedPriceEntity);
            sellPrices.add(quotedPriceEntity.getPrice());
        }


    }

    private static void utilCollectionsSort() {
        Collections.sort(sellPrices);
        Collections.sort(buyPrices);
        Collections.reverse(buyPrices);
    }

    private static void sort(final int num) {
        if (num == 0) {
//            initPrices();
        }
        utilCollectionsSort();
        System.out.println("\t卖家ID\t卖家放电量\t价格\t\t报价时间");
        addQueue(sellPrices, sellPriceEntitys, sellPricesQueue);
        System.out.println("\t---------------------------------------------------");
        System.out.println("\t买家ID\t买家购电量\t价格\t\t报价时间");
        addQueue(buyPrices, buyPriceEntitys, buyPricesQueue);
    }

    private static void addQueue(List<Double> prices, List<QuotedPriceEntity> priceEntitys, Queue<QuotedPriceEntity> pricesQueue) {
        prices.forEach(e -> {
            priceEntitys.forEach(m -> {
                if (e == m.getPrice()) {
                    pricesQueue.add(m);
                    System.out.println("\t" + m.getId().substring(0, 5) + "\t" + ((m.getAmount().toString().length() == 2) ? m.getAmount() : 0 + m.getAmount().toString()) + "\t\t\t" + (m.getPrice().toString().length() == 4 ? m.getPrice() : m.getPrice().toString() + 0) + "\t" + m.getQuotedPriceTime());
                }
            });
        });
    }

    private static void collect() {
        QuotedPriceEntity sell = sellPricesQueue.remove();
        QuotedPriceEntity buy = buyPricesQueue.remove();
        if (sell.getPrice() < buy.getPrice()) {
            List<QuotedPriceEntity> buyselMap = new ArrayList<>();
            buyselMap.add(buy);
            buyselMap.add(sell);
            buySelEntitys.add(buyselMap);
            System.out.printf("\t" + buy.getId().substring(0, 5) + "->" + sell.getId().substring(0, 5) + "\t%02d" +
                    "\t\t%02d\t\t%.2f" +
                    "\t%.2f" + "\t%.2f" + "\n", buy.getAmount(), sell.getAmount(), buy.getPrice(), sell.getPrice(), (buy.getPrice() + sell.getPrice()) / 2);
            if (sell.getAmount() > buy.getAmount()) {
                Double price = sell.getPrice();
                price += 0.1;

                if (price < currElectricityPrice * 0.45) {
                    sell.setPrice(Double.parseDouble(df.format(price)));
                } else {
                    sell.setPrice(Double.parseDouble(df.format(currElectricityPrice * 0.45)));
                }
                sell.setAmount(sell.getAmount() - buy.getAmount());
                sellPriceEntitys.add(sell);
                sellPrices.add(sell.getPrice());
            }
            if (buy.getAmount() > sell.getAmount()) {
                Double price = buy.getPrice();
                price -= 0.1;
                if (price > 0) {
                    buy.setPrice(Double.parseDouble(df.format(price)));
                } else {
                    buy.setPrice(0.01);
                }
                buy.setAmount(buy.getAmount() - sell.getAmount());
                buyPriceEntitys.add(buy);
                buyPrices.add(buy.getPrice());
            }
        } else {
            sell.setPrice(Double.parseDouble(df.format(sell.getPrice() - 0.01)));
            sellPriceEntitys.add(sell);
            sellPrices.add(sell.getPrice());
            buy.setPrice(Double.parseDouble(df.format(buy.getPrice() + 0.01)));
            buyPriceEntitys.add(buy);
            buyPrices.add(buy.getPrice());
        }

    }

    public static void charge(final int num) {
        System.out.println("\n\n\n\n");
        System.out.printf("\t====================第%03d轮匹配===================\n", (num + 1));
//        if (num == 0) {
//            getCurrElectricityPrice();
//        }
        sort(num);
        buyPriceEntitys.clear();
        sellPriceEntitys.clear();
        buyPrices.clear();
        sellPrices.clear();
        System.out.println("\t---------------------------------------------------");
        System.out.println("\t充电ID->放电ID\t充电电量\t放电电量\t充电电价\t放电电价\t成交电价");
        if (buyPricesQueue.size() >= sellPricesQueue.size()) {
            while (sellPricesQueue.size() > 0) {
                collect();
            }
            while (buyPricesQueue.size() > 0) {
                QuotedPriceEntity buy = buyPricesQueue.remove();
                buyPriceEntitys.add(buy);
                buyPrices.add(buy.getPrice());
            }

        } else {
            while (buyPricesQueue.size() > 0) {
                collect();
            }
            while (sellPricesQueue.size() > 0) {
                QuotedPriceEntity sell = sellPricesQueue.remove();
                sellPriceEntitys.add(sell);
                sellPrices.add(sell.getPrice());
            }
        }
        if (sellPriceEntitys.size() > 0 && buyPriceEntitys.size() > 0) {
            charge(num + 1);
        }

    }
}
