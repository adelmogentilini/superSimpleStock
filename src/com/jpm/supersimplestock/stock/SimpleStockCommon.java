package com.jpm.supersimplestock.stock;

import com.jpm.supersimplestock.shared.Constants;

import java.math.BigDecimal;

/**
 * Stock of type Common.
 *
 * Created by adelmo on 20/08/2016.
 */
public class SimpleStockCommon extends SimpleStock
{
    @Override
    public BigDecimal dividendYeld(BigDecimal tickerPrice) {
        if(tickerPrice != null){
            lastPrice = tickerPrice;
        }

        return lastDividend.divide(lastPrice, Constants.MC);
    }

    public SimpleStockCommon(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
        super(symbol, lastDividend, parValue);
    }
    public  String getDescription(){
        return "Symbol: "+symbol+" Type: COMMON  Last dividend: "+getLastDividend()+" Par Value: "+getParValue();
    }

}
