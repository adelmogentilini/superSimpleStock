package com.jpm.supersimplestock.service;

import com.jpm.supersimplestock.PersistenceMarket;
import com.jpm.supersimplestock.shared.BigFunctions;
import com.jpm.supersimplestock.shared.Constants;
import com.jpm.supersimplestock.stock.SimpleStock;
import com.jpm.supersimplestock.trade.Trade;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This class is the 'default implementation for interface' for respond to requirements of
 * example project.
 * <p>
 * Created by adelmo on 20/08/2016.
 */
public class ServiceSimpleStockImpl implements ServiceSimpleStock {



    /**
     * Calculate dividend yeld for the stock wth symbol at price indicated.
     *
     * @param symbol
     * @param price
     */
    @Override
    public BigDecimal dividendYeldFor(String symbol, double price) {
        return dividendYeldFor(symbol, new BigDecimal(price, Constants.MC));
    }
    @Override
    public BigDecimal dividendYeldFor(String symbol, BigDecimal price) {
        SimpleStock stock = PersistenceMarket.getInstance().getStock(symbol);
        return stock.dividendYeld(price);
    }

    /**
     * Calculate pe ratio for the stock with symbol at price indicated.
     *
     * @param symbol
     * @param price
     */
    @Override
    public BigDecimal peRatioFor(String symbol, BigDecimal price) {
        SimpleStock stock = PersistenceMarket.getInstance().getStock(symbol);
        return stock.peRatio(price);
    }

    /**
     * Record a buy of a quantity of qty for Stock identify by simple at price.
     *
     * @param symbol
     * @param qty
     * @param price
     */
    @Override
    public void tradeBuy(String symbol, BigDecimal qty, BigDecimal price) {
        SimpleStock stock = PersistenceMarket.getInstance().getStock(symbol);
        Trade td = stock.buy(qty, price);
        PersistenceMarket.getInstance().addTrade(td);
    }

    /**
     * Record a sell of a quantity of qty for Stock identify by simple at price.
     *
     * @param symbol
     * @param qty
     * @param price
     */
    @Override
    public void tradeSell(String symbol, BigDecimal qty, BigDecimal price) {
        SimpleStock stock = PersistenceMarket.getInstance().getStock(symbol);
        Trade td = stock.sell(qty, price);
        PersistenceMarket.getInstance().addTrade(td);
    }

    /**
     * For default consider only the trade in the last CONSTANTS.DEF_FOR_STOCK minutes
     * (15 is default like requirements).
     *
     * @param symbol
     * @return
     */
    @Override
    public BigDecimal stockPrice(String symbol) {
        return stockPrice(symbol, Constants.DEF_FOR_STOCK);
    }

    @Override
    public BigDecimal stockPrice(String symbol, long minutes) {
        Date limit = new Date();
        limit.setTime(limit.getTime() - Constants.MILLIS_FOR_MINUTE * minutes); // tot minutes before

        BigDecimal sumtq, sumq;
        sumtq = new BigDecimal(0, Constants.MC);
        sumq = new BigDecimal(0, Constants.MC);
        for (Trade trade : PersistenceMarket.getInstance().getTradeFrom(symbol, limit)) {
            sumtq = sumtq.add(trade.getPrice().multiply(trade.getQuantity(), Constants.MC), Constants.MC);
            sumq = sumq.add(trade.getQuantity(), Constants.MC);
        }


        if (sumq.compareTo(BigDecimal.ZERO) == 0){
            // Not possible divide by zero
            return BigDecimal.ZERO;
        }else{
            return sumtq.divide(sumq, Constants.MC);
        }
    }

    /**
     * Use a precision of SCALE decimal for exponential function.
     * SCALE is definde in constants.
     *
     * @return
     */
    @Override
    public BigDecimal gbce() {
        BigDecimal geometric = new BigDecimal(1, Constants.MC), total = new BigDecimal(0);

        for(SimpleStock stk: PersistenceMarket.getInstance().getAllStock()){
            geometric = geometric.multiply(stockPrice(stk.getSymbol()), Constants.MC);
            total = total.add(BigDecimal.ONE);
        }
        BigDecimal exponent = BigDecimal.ONE.divide(total, Constants.MC);

        //  z = x^y --> z = exp ( ln(x) * y )
        BigDecimal gbce = BigFunctions.exp( BigFunctions.ln(geometric, Constants.SCALE).multiply(exponent), Constants.SCALE );

        return gbce;
    }

}
