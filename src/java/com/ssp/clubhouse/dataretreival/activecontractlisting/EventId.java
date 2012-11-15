package com.ssp.clubhouse.dataretreival.activecontractlisting;

import org.w3c.dom.Node;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:29 AM
 */
public class EventId {
    public final String id;

    public EventId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EventId{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventId eventId = (EventId) o;

        if (id != null ? !id.equals(eventId.id) : eventId.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static EventId decode(Node eventNode) {
        return new EventId(eventNode.getAttributes().getNamedItem("id").getTextContent());
    }
}
