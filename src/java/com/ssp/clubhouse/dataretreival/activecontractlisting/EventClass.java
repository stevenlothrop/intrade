package com.ssp.clubhouse.dataretreival.activecontractlisting;

import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:14 AM
 */
public class EventClass {
    public final EventClassId id;
    public final String name;
    public final int displayOrder;
    public final Map<EventGroupId, EventGroup> eventGroupByEventGroupId = newLinkedHashMap();

    public EventClass(EventClassId id, String name, int displayOrder, Map<EventGroupId, EventGroup> eventGroupByEventGroupId) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.eventGroupByEventGroupId.putAll(eventGroupByEventGroupId);
    }
}
