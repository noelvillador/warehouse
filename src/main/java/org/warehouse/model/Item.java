package org.warehouse.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author dev11
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
        @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.itemId = :itemId"),
        @NamedQuery(name = "Item.getByBarcode", query = "SELECT i FROM Item i WHERE i.barcode = :barcode"),
        @NamedQuery(name = "Item.findByBarcode", query = "SELECT i FROM Item i WHERE i.barcode LIKE :barcode"),
        @NamedQuery(name = "Item.findByDescription", query = "SELECT i FROM Item i WHERE i.description = :description"),
        @NamedQuery(name = "Item.findByDateOfPurchase", query = "SELECT i FROM Item i WHERE i.dateOfPurchase = :dateOfPurchase"),
        @NamedQuery(name = "Item.findByRemarks", query = "SELECT i FROM Item i WHERE i.remarks = :remarks")})
public class Item implements Serializable {

    private static final long serialVersionUID = -1339871412198825230L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemId", nullable = false)
    private Integer itemId;

    @Column(name = "barcode", nullable = false)
    private String barcode;

    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Basic(optional = false)
    @Column(name = "date_of_purchase", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfPurchase;

    @Column(name = "remarks", length = 300)
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private Collection<Inventory> inventories;

    public Item() {
    }

    public Item(String barcode) {
        this.barcode = barcode;
    }

    public Item(String barcode, String description, Date dateOfPurchase) {
        this.barcode = barcode;
        this.description = description;
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Collection<Inventory> getInventories() {
        return inventories;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (barcode != null ? !barcode.equals(item.barcode) : item.barcode != null) return false;
        if (dateOfPurchase != null ? !dateOfPurchase.equals(item.dateOfPurchase) : item.dateOfPurchase != null)
            return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (itemId != null ? !itemId.equals(item.itemId) : item.itemId != null) return false;
        if (remarks != null ? !remarks.equals(item.remarks) : item.remarks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateOfPurchase != null ? dateOfPurchase.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", barcode=" + barcode +
                ", description='" + description + '\'' +
                ", dateOfPurchase=" + dateOfPurchase +
                ", remarks='" + remarks + '\'' +
                '}';
    }

}
