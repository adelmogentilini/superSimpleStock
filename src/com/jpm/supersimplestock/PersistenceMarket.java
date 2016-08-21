package com.jpm.supersimplestock;

import com.jpm.supersimplestock.service.ServiceSimpleStock;
import com.jpm.supersimplestock.service.ServiceSimpleStockImpl;
import com.jpm.supersimplestock.stock.SimpleStock;
import com.jpm.supersimplestock.trade.CompareTradeTime;
import com.jpm.supersimplestock.trade.Trade;

import java.util.*;

/**
 * This class is the structure that simulate the persistence of the objects.
 * From this is possible construct the service for read/write object from real market,
 * obtain the implemented service for satisfy the requirements of exercise.
 *
 * In a real context this work will do from an infrastructure for persistence and service
 * exposing (Spring / grails  hibernate).
 *
 *
 * <p>
 * Implemnted with a Singleton in this only memory version.
 * <p>
 * Created by adelmo on 20/08/2016.
 */
public class PersistenceMarket {
    public static Object lockme = new Object();
    static volatile PersistenceMarket INSTANCE;

    public static PersistenceMarket getInstance() {
        PersistenceMarket sf = INSTANCE;
        if (sf == null) {
            synchronized (lockme) {
                sf = INSTANCE;
                if (sf == null) {
                    INSTANCE = new PersistenceMarket();
                }
            }
        }
        return INSTANCE;
    }

    private List<SimpleStock> stokmarket;
    private Hashtable<String, List<Trade>> trades;

    /**
     * Create List like ArrayList wrapping the real object with Collections.synchronizedList
     * to forcing the syncronized access to structure for use in multithreading enviroments.
     * <p>
     * trades are stored in hashtable with separate list for every stock symbol.
     */
    private PersistenceMarket() {
        this.stokmarket = Collections.synchronizedList(new ArrayList<SimpleStock>());
        this.trades = new Hashtable<String, List<Trade>>();
    }


    /**
     * Inizialize and return the service for SimpleStockImplementation
     *
     * @return
     */
    public ServiceSimpleStock getServiceStock(){
        return new ServiceSimpleStockImpl();
    }


    /**
     * Add stock to market.
     * stock must not be null.
     *
     * @param ss
     */
    public boolean addStock(SimpleStock ss) {
        if (ss != null) {
            if (!stokmarket.contains(ss)) {
                return stokmarket.add(ss);
            }else{
                // non posso aggiungere un elemento già presente
                System.out.println(ss.getSymbol()+" already added to market");
                return false;
            }
        }
        return false;
    }

    public boolean removeStock(SimpleStock ss) {

        if (ss != null) {
            return stokmarket.remove(ss);
        }
        return false;
    }

    public SimpleStock getStock(String symstock) {
        for (SimpleStock ss : stokmarket) {
            if (ss.getSymbol().equals(symstock)) {
                return ss;
            }
        }
        return null;
    }

    /**
     * Read a list of all stock in market.
     *
     * @return
     */
    public List<SimpleStock> getAllStock(){
        return stokmarket;
    }

    /**
     * Add and remove trade to market.
     *
     * @param trade
     */
    public void addTrade(Trade trade) {
        if (!trades.containsKey(trade.getSs().getSymbol())) {
            trades.put(trade.getSs().getSymbol(), Collections.synchronizedList(new ArrayList<Trade>()));
        }
        trades.get(trade.getSs().getSymbol()).add(trade);
    }

    public void removeTrade(Trade trade) {
        if (trades.containsKey(trade.getSs().getSymbol())) {
            trades.get(trade.getSs().getSymbol()).remove(trade);
        }
    }

    /**
     * Return all trade for indicated symbol that as timestamp after limit.
     *
     * @param symbol
     * @param limit
     * @return list of trade with tradeOfTime > limit for the stock idetify from symbol
     */
    public List<Trade> getTradeFrom(String symbol, Date limit) {
        List<Trade> tradeForSymbol, retList;
        retList = new ArrayList<Trade>();
        if (trades.containsKey(symbol)) {
            tradeForSymbol = trades.get(symbol);
            Collections.sort(tradeForSymbol, new CompareTradeTime());
            for (Trade eltr : tradeForSymbol) {
                if (limit.compareTo(eltr.getTimeOfTrade()) <= 0) {
                    retList.add(eltr);
                } else {
                    // Exit point: for performance i ordered list of trade for time stamp so
                    // the first time that condition is not satisfy i can return the actual list
                    return retList;
                }
            }
        }
        return retList;
    }

    /**
     * Return a list with ALL trades of market.
     *
     * @return
     */
    public List<Trade> getAllTrade(){
        List<Trade>  all = new ArrayList<Trade>();
        for(List<Trade> lista: trades.values()){
            all.addAll(lista);
        }

        return all;
    }

    /**
     * Print status of  market:
     *  every stock with attributes and all trade for that stocks.
     *
     * */
    public void statusMarket() {
        for (SimpleStock st : stokmarket) {
            System.out.println(st.getDescription());
            statusTrade(st.getSymbol());
        }
    }

    /**
     * Print all trade form symbol.
     *
     * @param symbol
     */
    public void statusTrade(String symbol){
        List<Trade> tradeForSymbol;
        if (trades.containsKey(symbol)) {
            tradeForSymbol = trades.get(symbol);
            if(tradeForSymbol.size()>0) System.out.println(" TRADE ___________");
            Collections.sort(tradeForSymbol, new CompareTradeTime());
            for (Trade eltr : tradeForSymbol) {
                System.out.println(eltr.getDescription());
            }
            if(tradeForSymbol.size()>0)  System.out.println("__________________");
        }
    }
}
