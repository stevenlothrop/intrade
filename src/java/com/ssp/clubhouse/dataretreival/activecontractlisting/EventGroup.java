package com.ssp.clubhouse.dataretreival.activecontractlisting;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

    public static EventGroup decode(Node eventGroupNode) {
        EventGroupId eventGroupId = EventGroupId.decode(eventGroupNode);
        String name = "";
        int displayOrder = 0;
        Map<EventId, Event> eventByEventId = newLinkedHashMap();
        NodeList eventGroupNodeChildNodes = eventGroupNode.getChildNodes();
        for (int i = 0; i < eventGroupNodeChildNodes.getLength(); i++) {
            Node eventGroupChild = eventGroupNodeChildNodes.item(i);
            String nodeName = eventGroupChild.getNodeName();
            if (nodeName.equals("name")) {
                name = eventGroupChild.getTextContent();
            } else if (nodeName.equals("displayOrder")) {
                displayOrder = Integer.parseInt(eventGroupChild.getTextContent());
            } else if (nodeName.equals("Event")) {
                if (eventGroupChild.hasAttributes()) {
                    EventId eventId = EventId.decode(eventGroupChild);
                    Event event = Event.decode(eventGroupChild);
                    eventByEventId.put(eventId, event);
                }
            }
        }


        return new EventGroup(eventGroupId, name, displayOrder, eventByEventId);
    }

    @Override
    public String toString() {
        return "EventGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                ", eventByEventId=" + eventByEventId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventGroup that = (EventGroup) o;

        if (displayOrder != that.displayOrder) return false;
        if (eventByEventId != null ? !eventByEventId.equals(that.eventByEventId) : that.eventByEventId != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + displayOrder;
        result = 31 * result + (eventByEventId != null ? eventByEventId.hashCode() : 0);
        return result;
    }
}
