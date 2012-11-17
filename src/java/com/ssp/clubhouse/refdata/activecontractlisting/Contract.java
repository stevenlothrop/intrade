package com.ssp.clubhouse.refdata.activecontractlisting;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * User: stevenlothrop
 * Date: 11/15/12
 * Time: 12:36 AM
 */
public class Contract {
    public final String ccy;
    public final ContractId id;
    public final boolean inRunning;
    public final String state;
    public final String tickSize;
    public final String tickValue;
    public final String type;
    public final String name;
    public final int displayOrder;
    public final String symbol;
    public final String antiname;
    public final int totalVolume;

    public Contract(String ccy, ContractId id, boolean inRunning, String state, String tickSize, String tickValue, String type, String name, int displayOrder, String symbol, String antiname, int totalVolume) {
        this.ccy = ccy;
        this.id = id;
        this.inRunning = inRunning;
        this.state = state;
        this.tickSize = tickSize;
        this.tickValue = tickValue;
        this.type = type;
        this.name = name;
        this.displayOrder = displayOrder;
        this.symbol = symbol;
        this.antiname = antiname;
        this.totalVolume = totalVolume;
    }

    public static Contract decode(Node contractNode) {
        String ccy = contractNode.getAttributes().getNamedItem("ccy").getTextContent();
        ContractId id = ContractId.decode(contractNode);
        boolean inRunning = Boolean.parseBoolean(contractNode.getAttributes().getNamedItem("inRunning").getTextContent());
        String state = contractNode.getAttributes().getNamedItem("state").getTextContent();
        String tickSize = contractNode.getAttributes().getNamedItem("tickSize").getTextContent();
        String tickValue = contractNode.getAttributes().getNamedItem("tickValue").getTextContent();
        String type = contractNode.getAttributes().getNamedItem("type").getTextContent();
        String name = "";
        int displayOrder = 0;
        String symbol = "";
        String antiname = "";
        int totalVolume = 0;

        NodeList contractNodeChildNodes = contractNode.getChildNodes();
        for (int i = 0; i< contractNodeChildNodes.getLength(); i++){
            Node contractChild = contractNodeChildNodes.item(i);
            String nodeName = contractChild.getNodeName();
            if(nodeName.equals("name")){
                name = contractChild.getTextContent();
            } else if(nodeName.equals("displayOrder")){
                displayOrder = Integer.parseInt(contractChild.getTextContent());
            } else if(nodeName.equals("symbol")){
                symbol = contractChild.getTextContent();
            }  else if(nodeName.equals("totalVolume")){
                totalVolume = Integer.parseInt(contractChild.getTextContent());
            }
        }
        
        return new Contract(ccy, id, inRunning, state, tickSize, tickValue, type, name, displayOrder, symbol, antiname, totalVolume);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (displayOrder != contract.displayOrder) return false;
        if (inRunning != contract.inRunning) return false;
        if (totalVolume != contract.totalVolume) return false;
        if (antiname != null ? !antiname.equals(contract.antiname) : contract.antiname != null) return false;
        if (ccy != null ? !ccy.equals(contract.ccy) : contract.ccy != null) return false;
        if (id != null ? !id.equals(contract.id) : contract.id != null) return false;
        if (name != null ? !name.equals(contract.name) : contract.name != null) return false;
        if (state != null ? !state.equals(contract.state) : contract.state != null) return false;
        if (symbol != null ? !symbol.equals(contract.symbol) : contract.symbol != null) return false;
        if (tickSize != null ? !tickSize.equals(contract.tickSize) : contract.tickSize != null) return false;
        if (tickValue != null ? !tickValue.equals(contract.tickValue) : contract.tickValue != null) return false;
        if (type != null ? !type.equals(contract.type) : contract.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ccy != null ? ccy.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (inRunning ? 1 : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (tickSize != null ? tickSize.hashCode() : 0);
        result = 31 * result + (tickValue != null ? tickValue.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + displayOrder;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (antiname != null ? antiname.hashCode() : 0);
        result = 31 * result + totalVolume;
        return result;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "ccy='" + ccy + '\'' +
                ", id=" + id +
                ", inRunning=" + inRunning +
                ", state='" + state + '\'' +
                ", tickSize='" + tickSize + '\'' +
                ", tickValue='" + tickValue + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                ", symbol='" + symbol + '\'' +
                ", antiname='" + antiname + '\'' +
                ", totalVolume=" + totalVolume +
                '}';
    }

}
