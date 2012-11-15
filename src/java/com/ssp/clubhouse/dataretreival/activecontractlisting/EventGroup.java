package com.ssp.clubhouse.dataretreival.activecontractlisting;

import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:20 AM
 */
public class EventGroup {
    public final EventGroupId id;
    public final String name;
    public final int displayOrder;
    public final Map<EventId, Event> eventByEventId = newLinkedHashMap();

    public EventGroup(EventGroupId id, String name, int displayOrder, Map<EventId, Event> eventByEventId) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.eventByEventId.putAll(eventByEventId);
    }
}
