package com.jpm.supersimplestock.stock;

import com.jpm.supersimplestock.shared.TypeStock;

import java.math.BigDecimal;

/**
 * Singleton for StockFactory.
 *
 * Created by adelmo on 20/08/2016.
 */
public class StockFactory {
    public static  Object lockme = new Object();
    static volatile StockFactory INSTANCE;

    private StockFactory(){}

    public static StockFactory getInstance(){
        StockFactory sf = INSTANCE;
        if (sf == null) {
            synchronized(lockme) {
                sf = INSTANCE;
                if (sf == null) {
                    INSTANCE = new StockFactory();
                }
            }
        }
        return INSTANCE;
    }

    public SimpleStockCommon buildStockCommon(String symbol,
            BigDecimal lastDividend,
            BigDecimal parValue){
        return new SimpleStockCommon(symbol,lastDividend,parValue);
    }

    public SimpleStockPreferred buildStockPreferred(String symbol,
                                                 BigDecimal lastDividend,
                                                 BigDecimal fixedDividend,
                                                 BigDecimal parValue){
        SimpleStockPreferred ret =  new SimpleStockPreferred(symbol,lastDividend,parValue,fixedDividend);
        ret.getLastDividend(); // force recalculate lastDividend
        return ret;
    }

    /**
     * Build proper stock with type.
     * If type is not recognized try to build with data: if fixedDividend is null build a common stock
     * else build e preferred stock.
     *
     * @param typeS
     * @param symbol
     * @param lastDividend
     * @param fixedDividend
     * @param parValue
     *
     * @return an instance of SimpleStockPreferred or SimpleStockCommon
     */
    public SimpleStock buildStock(TypeStock typeS,
                                  String symbol,
                                  BigDecimal lastDividend,
                                  BigDecimal fixedDividend,
                                  BigDecimal parValue){
        switch (typeS) {
            case COMMON:
                System.out.println("build common stock");
                return buildStockCommon(symbol,lastDividend, parValue);
            case PREFERRED:
                System.out.println("build preferred stock");
                return buildStockPreferred(symbol, lastDividend, fixedDividend, parValue);
            default:
                System.out.println("Not recognized type: build from data");
                if (fixedDividend == null){
                    // fixed dividend is null so i suppose is a COMMON stock
                    return buildStockCommon(symbol,lastDividend, parValue);
                }else{
                    return buildStockPreferred(symbol, lastDividend, fixedDividend, parValue);
                }
        }
    }

}
