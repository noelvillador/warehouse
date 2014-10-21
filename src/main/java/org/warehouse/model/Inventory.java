package org.warehouse.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Noel on 8/14/14.
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i"),
        @NamedQuery(name = "Inventory.findByDepartment", query = "SELECT i FROM Inventory i WHERE i.department like :department"),
        @NamedQuery(name = "Inventory.getByDepartment", query = "SELECT i FROM Inventory i WHERE i.department =:department"),
        @NamedQuery(name = "Inventory.findById", query = "SELECT i FROM Inventory i WHERE i.inventoryId = :inventoryId")})

public class Inventory implements Serializable {

    private static final long serialVersionUID = -7014213716560470025L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "inventoryId", nullable = false)
    private Integer inventoryId;

    @Column(name = "department")
    private String department;

    @Column(name = "port")
    private String port;

    @Column(name = "itemStatus")
    private String itemStatus;

//    @Column(name = "remarks")
//    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId",nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId",nullable = false)
    private Item item;

    public Integer getInventoryId() {
        return inventoryId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", department='" + department + '\'' +
                ", port='" + port + '\'' +
                ", itemStatus='" + itemStatus + '\'' +
//                ", remarks='" + remarks + '\'' +
                ", employee=" + employee +
                ", item=" + item +
                '}';
    }
}
