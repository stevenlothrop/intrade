package com.ssp.clubhouse.refdata.activecontractlisting;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

    public static EventClass decode(Node eventClassNode) {
        EventClassId eventClassId = EventClassId.decode(eventClassNode);
        String name = "";
        int displayOrder = 0;
        Map<EventGroupId, EventGroup> eventGroupByEventGroupId = newLinkedHashMap();
        NodeList eventClassNodeChildNodes = eventClassNode.getChildNodes();
        for (int i = 0; i < eventClassNodeChildNodes.getLength(); i++) {
            Node eventClassChild = eventClassNodeChildNodes.item(i);
            String nodeName = eventClassChild.getNodeName();
            if (nodeName.equals("name")) {
                name = eventClassChild.getTextContent();
            } else if (nodeName.equals("displayOrder")) {
                displayOrder = Integer.parseInt(eventClassChild.getTextContent());
            } else if (nodeName.equals("EventGroup")) {
                if (eventClassChild.hasAttributes()) {
                    EventGroupId eventGroupId = EventGroupId.decode(eventClassChild);
                    EventGroup eventGroup = EventGroup.decode(eventClassChild);
                    eventGroupByEventGroupId.put(eventGroupId, eventGroup);
                }
            }
        }
        return new EventClass(eventClassId, name, displayOrder, eventGroupByEventGroupId);
    }

    @Override
    public String toString() {
        return "EventClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                ", eventGroupByEventGroupId=" + eventGroupByEventGroupId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventClass that = (EventClass) o;

        if (displayOrder != that.displayOrder) return false;
        if (eventGroupByEventGroupId != null ? !eventGroupByEventGroupId.equals(that.eventGroupByEventGroupId) : that.eventGroupByEventGroupId != null)
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
        result = 31 * result + (eventGroupByEventGroupId != null ? eventGroupByEventGroupId.hashCode() : 0);
        return result;
    }
}
