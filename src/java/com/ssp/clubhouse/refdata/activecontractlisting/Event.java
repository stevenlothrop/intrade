package com.ssp.clubhouse.refdata.activecontractlisting;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Map;

import static com.google.common.collect.Maps.newLinkedHashMap;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:25 AM
 */
public class Event {
    public final Long endDate;
    public final Long startDate;
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

    public static Event decode(Node eventNode) {
        Long endDate = Long.parseLong(eventNode.getAttributes().getNamedItem("EndDate").getTextContent());
        Long startDate = Long.parseLong(eventNode.getAttributes().getNamedItem("StartDate").getTextContent());
        EventGroupId eventGroupId = new EventGroupId(eventNode.getAttributes().getNamedItem("groupID").getTextContent());
        EventId eventId = EventId.decode(eventNode);
        String description = "";
        String name = "";
        int displayOrder = 0;
        Map<ContractId, Contract> contractByContractId = newLinkedHashMap();

        NodeList eventNodeChildNodes = eventNode.getChildNodes();
        for (int i = 0; i < eventNodeChildNodes.getLength(); i++) {
            Node eventChild = eventNodeChildNodes.item(i);
            String nodeName = eventChild.getNodeName();
            if (nodeName.equals("name")) {
                name = eventChild.getTextContent();
            } else if (nodeName.equals("Description")) {
                description = eventChild.getTextContent();
            } else if (nodeName.equals("displayOrder")) {
                displayOrder = Integer.parseInt(eventChild.getTextContent());
            } else if (nodeName.equals("contract")) {
                if (eventChild.hasAttributes()) {
                    ContractId contractId = ContractId.decode(eventChild);
                    Contract contract = Contract.decode(eventChild);
                    contractByContractId.put(contractId, contract);
                }
            }
        }

        return new Event(endDate, startDate, eventGroupId, eventId, description, name, displayOrder, contractByContractId);
    }

    @Override
    public String toString() {
        return "Event{" +
                "endDate=" + endDate +
                ", startDate=" + startDate +
                ", groupId=" + groupId +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                ", contractByContractId=" + contractByContractId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (displayOrder != event.displayOrder) return false;
        if (contractByContractId != null ? !contractByContractId.equals(event.contractByContractId) : event.contractByContractId != null)
            return false;
        if (description != null ? !description.equals(event.description) : event.description != null) return false;
        if (endDate != null ? !endDate.equals(event.endDate) : event.endDate != null) return false;
        if (groupId != null ? !groupId.equals(event.groupId) : event.groupId != null) return false;
        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = endDate != null ? endDate.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + displayOrder;
        result = 31 * result + (contractByContractId != null ? contractByContractId.hashCode() : 0);
        return result;
    }
}
