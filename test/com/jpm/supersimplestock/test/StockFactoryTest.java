package com.jpm.supersimplestock.test;

import com.jpm.supersimplestock.PersistenceMarket;
import com.jpm.supersimplestock.shared.Constants;
import com.jpm.supersimplestock.shared.TypeStock;
import com.jpm.supersimplestock.stock.SimpleStock;
import com.jpm.supersimplestock.stock.StockFactory;
import junit.framework.TestCase;

import java.math.BigDecimal;

/**
 * Example of JUnit3 test class.
 * Writing for verify StockFactory.
 * <p>
 * Created by adelmo on 20/08/2016.
 */
public class StockFactoryTest extends TestCase {

    public void testGetInstance() throws Exception {
        // 2 getInstance MUST be the same
        assertEquals(StockFactory.getInstance(), StockFactory.getInstance());
    }

    public void testBuildStock() throws Exception {
        assertEquals(true, createSampledata1());
        assertEquals(false, createSampledata2());
        assertEquals(false, createSampledata3());
        assertEquals(true, createSampledata4());
    }

    /**
     * Create sample data
     */
    public boolean createSampledata1() {
        SimpleStock ss;

        try {
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "TEA", new BigDecimal(0, Constants.MC), null, new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "POP", new BigDecimal(8, Constants.MC), null, new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.PREFERRED, "GIN", new BigDecimal(0, Constants.MC), new BigDecimal(0.02, Constants.MC), new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "JOE", new BigDecimal(13, Constants.MC), null, new BigDecimal(100, Constants.MC));
            PersistenceMarket.getInstance().addStock(ss);
        } catch (RuntimeException re) {
            return false;
        }

        return true;
    }

    /**
     * Must fail (false) because parvalue null is not admitted
     *
     * @return
     */
    public boolean createSampledata2() {
        SimpleStock ss;

        try {
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "TEA", new BigDecimal(0), null, new BigDecimal(100));
            PersistenceMarket.getInstance().addStock(ss);
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "xxx", new BigDecimal(0), null, null);
            PersistenceMarket.getInstance().addStock(ss);
        } catch (RuntimeException re) {
            return false;
        }

        return true;
    }

    /**
     * Must fail (false) if used after create1 because you cannot add an existent stock.
     *
     * @return
     */
    public boolean createSampledata3() {
        SimpleStock ss;

        try {
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "TEA", new BigDecimal(0), null, new BigDecimal(100));
            return PersistenceMarket.getInstance().addStock(ss);
        } catch (RuntimeException re) {
            return false;
        }
    }

    /**
     * Must return true because ALE is not exist in market and value are correct
     *
     * @return
     */
    public boolean createSampledata4() {
        SimpleStock ss;

        try {
            ss = StockFactory.getInstance().buildStock(TypeStock.COMMON, "ALE", new BigDecimal(23), null, new BigDecimal(60));
            return PersistenceMarket.getInstance().addStock(ss);
        } catch (RuntimeException re) {
            return false;
        }
    }


}