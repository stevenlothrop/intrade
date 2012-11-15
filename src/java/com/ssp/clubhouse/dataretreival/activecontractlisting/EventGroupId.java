package com.ssp.clubhouse.dataretreival.activecontractlisting;

import org.w3c.dom.Node;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:26 AM
 */
public class EventGroupId {
    public final String id;

    public EventGroupId(String id) {
        this.id = id;
    }

    public static EventGroupId decode(Node eventGroupNode) {
        return new EventGroupId(eventGroupNode.getAttributes().getNamedItem("id").getTextContent());
    }

    @Override
    public String toString() {
        return "EventGroupId{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventGroupId that = (EventGroupId) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
