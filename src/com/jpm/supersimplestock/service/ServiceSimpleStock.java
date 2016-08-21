package com.jpm.supersimplestock.service;

import java.math.BigDecimal;

/**
 * Created by adelmo on 20/08/2016.
 */
public interface ServiceSimpleStock {
    BigDecimal dividendYeldFor(String symbol, double price);
    BigDecimal dividendYeldFor(String symbol, BigDecimal price);

    BigDecimal peRatioFor(String symbol, BigDecimal price);

    void tradeBuy(String symbol, BigDecimal qty, BigDecimal price);

    void tradeSell(String symbol, BigDecimal qty, BigDecimal price);

    BigDecimal stockPrice(String symbol);

    BigDecimal stockPrice(String symbol, long minutes);

    BigDecimal gbce();
}
