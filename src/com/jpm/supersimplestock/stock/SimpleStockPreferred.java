package com.jpm.supersimplestock.stock;

import com.jpm.supersimplestock.shared.Constants;

import java.math.BigDecimal;

/**
 * Stock of type Preferred.
 * 
 * Created by adelmo on 20/08/2016.
 */
public class SimpleStockPreferred extends SimpleStock {
    protected BigDecimal fixedDividend;

    public SimpleStockPreferred(String symbol, BigDecimal lastDividend, BigDecimal parValue, BigDecimal fixedDividend) {
        super(symbol, lastDividend, parValue);
        this.fixedDividend = fixedDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(BigDecimal fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    /**
     * @param tickerPrice
     * @return the correct dividen yeld or NULL if some error occour.
     */
    @Override
    public BigDecimal dividendYeld(BigDecimal tickerPrice) {
        if(tickerPrice != null){
            lastPrice = tickerPrice;
        }
        if (lastPrice != null && lastPrice.compareTo(BigDecimal.ZERO) > 0) {
            return (fixedDividend.multiply(parValue, Constants.MC)).divide(lastPrice,  Constants.MC);
        }else{
            return null;
        }
    }
    public  String getDescription(){
        return "Symbol: "+symbol+" Type: PREFERRED Last dividend: "+getLastDividend()+" Fixed dividend: "+getFixedDividend()+" Par Value: "+getParValue();
    }

}
