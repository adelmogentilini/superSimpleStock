package com.jpm.supersimplestock.stock;

import com.jpm.supersimplestock.shared.BuyOrSell;
import com.jpm.supersimplestock.trade.Trade;

import java.math.BigDecimal;

/**
 * Root for all class stock.
 * Contains base attribute and shared code for all type of stock.
 * <p>
 * Created by adelmo gentilini on 20/08/2016.
 */
public abstract class SimpleStock {
    protected String symbol;
    protected BigDecimal lastDividend;
    protected BigDecimal parValue;

    // Using lastPrice i can call the function for "dividendYeld" and "peratio" also without price so i have the indicator at the last
    // price used. I can have a separate service for update the last price and the service for indicator will be updated to last price.

    protected BigDecimal lastPrice; // price of last operation with price on this stock (buy / sell / any calculation service)

    /**
     * Constructor
     *
     * @param symbol       @NotNull
     * @param lastDividend if is null default is ZERO
     * @param parValue     @NotNull
     */
    public SimpleStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
        this.symbol = symbol;
        if (lastDividend == null) {
            this.lastDividend = BigDecimal.ZERO;
        } else {

            this.lastDividend = lastDividend;
        }
        this.parValue = parValue;
        if (parValue == null || symbol == null) {
            throw new RuntimeException("invalid values");
        }
    }

    /**
     * When i do an operation an a stock remember the last price used for operation.
     * In some service where a price is a parameter if is null i use this price.
     *
     * @return
     */
    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getLastDividend() {
        if (lastDividend == null) {
            // If not setting i suppose is ZERO
            lastDividend = BigDecimal.ZERO;
        }
        return lastDividend;
    }

    public void setLastDividend(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public void setParValue(BigDecimal parValue) {
        this.parValue = parValue;
    }

    public BigDecimal peRatio(BigDecimal tickerPrice) {
        if (tickerPrice != null) {
            lastPrice = tickerPrice;
        }
        return lastPrice.divide(lastDividend);
    }

    /**
     * Build a trade that represent a buy of this stock.
     *
     * @param qty
     * @param price
     * @return
     */
    public Trade buy(BigDecimal qty, BigDecimal price) {
        lastPrice = price;
        return new Trade(this, qty, BuyOrSell.BUY, price);
    }

    /**
     * Build a trade that represent a sell of this stock.
     *
     * @param qty
     * @param price
     * @return
     */
    public Trade sell(BigDecimal qty, BigDecimal price) {
        lastPrice = price;
        return new Trade(this, qty, BuyOrSell.SELL, price);
    }

    public abstract BigDecimal dividendYeld(BigDecimal tickerPrice);

    /**
     * Used in report utility for describe stock.
     *
     * @return
     */
    public abstract String getDescription();

    /**
     * For use in list of stock we suppose to identify stock only with code of symbols (and implicity type).
     * A stock is the same of another stock only if has the same code (dividend and parvalue are not important).
     * This is true for the SAME class of object (SimpleStockCommon != SimpleStockPreferred also if symbol is the same).
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleStock that = (SimpleStock) o;

        return symbol != null ? symbol.equals(that.symbol) : that.symbol == null;

    }

    @Override
    public int hashCode() {
        return symbol != null ? symbol.hashCode() : 0;
    }
}
