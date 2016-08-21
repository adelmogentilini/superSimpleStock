package com.jpm.supersimplestock.shared;

        import java.math.MathContext;

/**
 * Created by adelmo on 20/08/2016.
 */
public class Constants {

    public static final int SCALE = 5;
    public static long MILLIS_FOR_MINUTE = 60000;
    public static long DEF_FOR_STOCK = 15; // 15 minute in milliseconds (we could use DateUtils of apache for operation...)
    public static MathContext MC = new MathContext(3);
}
