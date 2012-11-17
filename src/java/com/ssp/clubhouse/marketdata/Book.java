package com.ssp.clubhouse.marketdata;

import com.ssp.clubhouse.refdata.activecontractlisting.ContractId;

import javax.xml.bind.PrintConversionEvent;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: stevenlothrop
 * Date: 11/17/12
 * Time: 3:32 PM
 */
public class Book {
    public final ContractId contractId;
    public final long lastUpdateTime;
    public final List<BookEntry> bids;
    public final List<BookEntry> offers;

    public Book(ContractId contractId, long lastUpdateTime, List<BookEntry> bids, List<BookEntry> offers) {
        this.contractId = contractId;
        this.lastUpdateTime = lastUpdateTime;
        this.bids = bids;
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "Book{" +
                "contractId=" + contractId +
                ", lastUpdateTime=" + lastUpdateTime +
                ", bids=" + bids +
                ", offers=" + offers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (lastUpdateTime != book.lastUpdateTime) return false;
        if (bids != null ? !bids.equals(book.bids) : book.bids != null) return false;
        if (contractId != null ? !contractId.equals(book.contractId) : book.contractId != null) return false;
        if (offers != null ? !offers.equals(book.offers) : book.offers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contractId != null ? contractId.hashCode() : 0;
        result = 31 * result + (int) (lastUpdateTime ^ (lastUpdateTime >>> 32));
        result = 31 * result + (bids != null ? bids.hashCode() : 0);
        result = 31 * result + (offers != null ? offers.hashCode() : 0);
        return result;
    }

    public static class BookEntry {
        public final String price;
        public final int quantity;
        public BookEntry(String price, int quantity) {
            this.price = price;
            this.quantity = quantity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BookEntry bookEntry = (BookEntry) o;

            if (quantity != bookEntry.quantity) return false;
            if (price != null ? !price.equals(bookEntry.price) : bookEntry.price != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = price != null ? price.hashCode() : 0;
            result = 31 * result + quantity;
            return result;
        }

        @Override
        public String toString() {
            return "BookEntry{" +
                    "price='" + price + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}
