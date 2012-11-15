package com.ssp.clubhouse.dataretreival.activecontractlisting;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:36 AM
 */
public class Contract {
    public final String ccy;
    public final ContractId id;
    public final boolean isRunning;
    public final String state;
    public final String tickSize;
    public final String tickValue;
    public final String type;
    public final String name;
    public final int displayOrder;
    public final String symbol;
    public final String antiname;
    public final int totalVolume;

    public Contract(String ccy, ContractId id, boolean isRunning, String state, String tickSize, String tickValue, String type, String name, int displayOrder, String symbol, String antiname, int totalVolume) {
        this.ccy = ccy;
        this.id = id;
        this.isRunning = isRunning;
        this.state = state;
        this.tickSize = tickSize;
        this.tickValue = tickValue;
        this.type = type;
        this.name = name;
        this.displayOrder = displayOrder;
        this.symbol = symbol;
        this.antiname = antiname;
        this.totalVolume = totalVolume;
    }
}
