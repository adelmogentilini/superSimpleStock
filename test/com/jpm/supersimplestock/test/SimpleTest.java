package com.jpm.supersimplestock.test;

import com.jpm.supersimplestock.PersistenceMarket;
import com.jpm.supersimplestock.service.ServiceSimpleStock;
import com.jpm.supersimplestock.shared.BigFunctions;
import com.jpm.supersimplestock.shared.Constants;
import com.jpm.supersimplestock.shared.TypeStock;
import com.jpm.supersimplestock.stock.SimpleStock;
import com.jpm.supersimplestock.stock.StockFactory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Example class with fixed RANDOM value.
 *
 * Created by adelmo on 20/08/2016.
 */
public class SimpleTest {
    public static void main(String[] argv) {
        ServiceSimpleStock stock;

        /* create a set of sample data: a set of stock and some fake trades for everyone.*/
        createSampledata();
        createSampleTrading();
        PersistenceMarket.getInstance().statusMarket();
        stock = PersistenceMarket.getInstance().getServiceStock();

        /* print dividend yeld with random price for some symbol */
        System.out.println("TEA dividend Yeld: "+stock.dividendYeldFor("TEA", 3.12));
        System.out.println("POP dividend Yeld: "+stock.dividendYeldFor("POP", new BigDecimal(6.21, Constants.MC)));
        System.out.println("ALE dividend Yeld: "+stock.dividendYeldFor("ALE", 3.45));

        /* print the stock price for every stock in the market */
        for(SimpleStock st: PersistenceMarket.getInstance().getAllStock()){
            System.out.println("STOCK PRICE FOR "+st.getSymbol()+"= "+stock.stockPrice(st.getSymbol()));
        }

        /* print the GBCE index relative to the market */
        System.out.println("GBCE: "+stock.gbce());

    }

    public static boolean createSampledata() {
        SimpleStock ss;

        try {
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "TEA", new BigDecimal(0, Constants.MC), null, new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "POP", new BigDecimal(8, Constants.MC), null, new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.PREFERRED, "GIN", new BigDecimal(8, Constants.MC),
                    new BigDecimal(0.02, Constants.MC), new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "JOE", new BigDecimal(13, Constants.MC), null, new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "ALE", new BigDecimal(23, Constants.MC), null, new BigDecimal(60, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
        } catch (RuntimeException re) {
            return false;
        }

        return true;
    }

    public static boolean createSampleTrading() {

        SimpleStock ss = PersistenceMarket.getInstance().getStock("TEA");
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(3.1, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(3.5, Constants.MC)));

        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(2.8, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.sell(BigDecimal.TEN, new BigDecimal(3.1, Constants.MC)));

        ss = PersistenceMarket.getInstance().getStock("POP");
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(4.1, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(5.5, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(1.8, Constants.MC)));

        PersistenceMarket.getInstance().addTrade(ss.sell(BigDecimal.TEN, new BigDecimal(6.1, Constants.MC)));

        ss = PersistenceMarket.getInstance().getStock("JOE");
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(3.3, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(3.2, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(1.8, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.sell(BigDecimal.TEN, new BigDecimal(4.1, Constants.MC)));

        ss = PersistenceMarket.getInstance().getStock("GIN");
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(4.1, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(4.5, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(5.8, Constants.MC)));

        PersistenceMarket.getInstance().addTrade(ss.sell(BigDecimal.TEN, new BigDecimal(6.1, Constants.MC)));

        ss = PersistenceMarket.getInstance().getStock("ALE");
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(3.4, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(3.67, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.buy(BigDecimal.TEN, new BigDecimal(2.23, Constants.MC)));
        PersistenceMarket.getInstance().addTrade(ss.sell(BigDecimal.TEN, new BigDecimal(3.34, Constants.MC)));

        return true;
    }
}
