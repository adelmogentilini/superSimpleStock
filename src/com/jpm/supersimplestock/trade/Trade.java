package com.jpm.supersimplestock.trade;

import com.jpm.supersimplestock.shared.BuyOrSell;
import com.jpm.supersimplestock.stock.SimpleStock;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A trade is an operation of buy/sell on a stock at predefined price of a defined quantity.
 * Is also important the timeStamp of the operation.
 *
 * Created by adelmo on 20/08/2016.
 */
public class Trade {
    SimpleStock ss;
    protected Date timeOfTrade;
    protected BigDecimal quantity;
    protected BuyOrSell sign; // for future implementation
    protected BigDecimal price;

    public Trade(SimpleStock ss, Date timeOfTrade, BigDecimal quantity, BuyOrSell sign) {
        this.ss = ss;
        this.timeOfTrade = timeOfTrade;
        this.quantity = quantity;
        this.sign = sign;
    }

    /**
     * Build a trade con NOW like time of trade.
     *
     * @param ss
     * @param quantity
     * @param sign
     * @param price
     */
    public Trade(SimpleStock ss, BigDecimal quantity, BuyOrSell sign, BigDecimal price) {
        this.ss = ss;
        this.quantity = quantity;
        this.sign = sign;
        this.price = price;
        this.timeOfTrade = new java.util.Date();
    }

    public SimpleStock getSs() {
        return ss;
    }

    public void setSs(SimpleStock ss) {
        this.ss = ss;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BuyOrSell getSign() {
        return sign;
    }

    public void setSign(BuyOrSell sign) {
        this.sign = sign;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getTimeOfTrade() {
        return timeOfTrade;
    }

    public void setTimeOfTrade(Date timeOfTrade) {
        this.timeOfTrade = timeOfTrade;
    }

    /**
     * To identify the Trade are need time and symbol.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (ss != null ? !ss.equals(trade.ss) : trade.ss != null) return false;
        return timeOfTrade != null ? timeOfTrade.equals(trade.timeOfTrade) : trade.timeOfTrade == null;

    }

    @Override
    public int hashCode() {
        int result = ss != null ? ss.hashCode() : 0;
        result = 31 * result + (timeOfTrade != null ? timeOfTrade.hashCode() : 0);
        return result;
    }

    public String getDescription(){
        String retval;

        switch (sign) {
            case BUY:
                retval = "BUY at: "+timeOfTrade.toString()+" with price: "+price;
                break;
            case SELL:
                retval = "SELL at: "+timeOfTrade.toString()+" with price: "+price;
                break;
            default:
                retval = "";
        }

        return retval;
    }
}
