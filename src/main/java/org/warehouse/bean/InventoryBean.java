package org.warehouse.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.warehouse.dao.EmployeeDao;
import org.warehouse.dao.InventoryDao;
import org.warehouse.dao.ItemDao;
import org.warehouse.model.Employee;
import org.warehouse.model.Inventory;
import org.warehouse.model.Item;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noel on 8/30/14.
 */
@Component("inventoryBean")
@RequestScoped
public class InventoryBean implements Serializable {

    private static final long serialVersionUID = -1134235230137194639L;

    private Logger log = LoggerFactory.getLogger(InventoryBean.class);

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ItemDao itemDao;

    private Inventory inventory = new Inventory();
    private Inventory selectedInventory;
    private List<Inventory> inventories;
    private String department;
    private Integer employeeId;
    private Integer itemId;

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getSelectedInventory() {
        return selectedInventory;
    }

    public void setSelectedInventory(Inventory selectedInventory) {
        this.selectedInventory = selectedInventory;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public List<Inventory> getAllInventories(){
        return inventoryDao.getAll();
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getByDepartment(String name) {
        List<Inventory> foundInventory = inventoryDao.findByDepartment(name);
        List<String> names = new ArrayList<String>();
        for (Inventory i : foundInventory) {
            names.add(i.getDepartment());
        }
        return names;
    }

    public List<Employee> getAllEmployee(){
        return employeeDao.getAll();
    }

    public List<Item> getAllItem(){
        return itemDao.getAll();
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void save(ActionEvent actionEvent) {
        FacesMessage facesMessage;
        try {
            if(employeeId!=null){
                inventory.setEmployee(employeeDao.getEmployee(employeeId));
            }
            if(itemId!=null){
                inventory.setItem(itemDao.getItem(itemId));
            }
            inventoryDao.add(inventory);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Employee " + inventory.toString() + " is saved");
            inventory = new Inventory();
            itemId = null;
            employeeId = null;
        } catch (Exception e) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred! ", e.toString());
            log.error(e.toString());
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public void search(ActionEvent actionEvent) {
        inventories = inventoryDao.findByDepartment(department);
    }

    public void update() {
        FacesMessage facesMessage;
        try {
            inventoryDao.update(selectedInventory);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", selectedInventory.toString() + " is updated successfully");
            selectedInventory = null;
        } catch (Exception e) {
            log.error(e.toString());
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public void remove() {
        FacesMessage facesMessage;
        try {
            inventoryDao.delete(selectedInventory);
            inventories.remove(selectedInventory);
            selectedInventory = new Inventory();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Inventory is deleted successfully");
        } catch (Exception e) {
            log.error(e.toString());
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
