package com.ssp.clubhouse.dataretreival.activecontractlisting;

import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:25 AM
 */
public class Event {
    public final long endDate;
    public final long startDate;
    public final EventGroupId groupId;
    public final EventId id;
    public final String description;
    public final String name;
    public final int displayOrder;
    public final Map<ContractId, Contract> contractByContractId = newLinkedHashMap();

    public Event(long endDate, long startDate, EventGroupId groupId, EventId id, String description, String name, int displayOrder, Map<ContractId, Contract> contractByContractId) {
        this.endDate = endDate;
        this.startDate = startDate;
        this.groupId = groupId;
        this.id = id;
        this.description = description;
        this.name = name;
        this.displayOrder = displayOrder;
        this.contractByContractId.putAll(contractByContractId);
    }
}
