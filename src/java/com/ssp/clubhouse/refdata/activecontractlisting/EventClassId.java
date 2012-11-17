package com.ssp.clubhouse.refdata.activecontractlisting;

import org.w3c.dom.Node;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:27 AM
 */
public class EventClassId {
    public final String id;

    public EventClassId(String id) {
        this.id = id;
    }

    public static EventClassId decode(Node eventClassNode) {
        return new EventClassId(eventClassNode.getAttributes().getNamedItem("id").getTextContent());
    }

    @Override
    public String toString() {
        return "EventClassId{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventClassId that = (EventClassId) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
