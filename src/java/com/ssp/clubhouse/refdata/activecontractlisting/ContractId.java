package com.ssp.clubhouse.refdata.activecontractlisting;

import org.w3c.dom.Node;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:36 AM
 */
public class ContractId {
    public final String id;

    public ContractId(String id) {
        this.id = id;
    }

    public static ContractId decode(Node contractNode) {
        return new ContractId(contractNode.getAttributes().getNamedItem("id").getTextContent());
    }

    @Override
    public String toString() {
        return "ContractId{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractId that = (ContractId) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
