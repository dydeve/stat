package org.slf4j.impl;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import statistics.monitor.log.TtlMDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 * Created by dy on 2018/4/13.
 */
public class StaticMDCBinder {

    /**
     * The unique instance of this class.
     */
    public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

    private StaticMDCBinder() {
    }

    /**
     * Currently this method always returns an instance of
     * {@link StaticMDCBinder}.
     */
    public MDCAdapter getMDCA() {
        return new TtlMDCAdapter();
    }

    public String  getMDCAdapterClassStr() {
        return LogbackMDCAdapter.class.getName();
    }

}
