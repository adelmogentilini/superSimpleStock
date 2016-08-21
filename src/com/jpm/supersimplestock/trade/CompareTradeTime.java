package com.jpm.supersimplestock.trade;

import java.util.Comparator;

/**
 * In this comparator we use only time of Trade for timing sorting.
 * Default order is DESCENDING, first is the most recent.
 *
 * Created by adelmo on 20/08/2016.
 */
public class CompareTradeTime implements Comparator<Trade> {
    @Override
    public int compare(Trade o1, Trade o2) {
        return (-1)*o1.getTimeOfTrade().compareTo(o2.getTimeOfTrade());
    }
}
