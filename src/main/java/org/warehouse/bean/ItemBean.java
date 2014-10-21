package org.warehouse.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.warehouse.dao.ItemDao;
import org.warehouse.model.Item;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noel on 8/30/14.
 */
@Component("itemBean")
@RequestScoped
public class ItemBean implements Serializable {

    private Logger log = LoggerFactory.getLogger(ItemBean.class);
    private static final long serialVersionUID = -4572047698309242269L;
    private Item item = new Item();
    private String barcode;
    private List<Item> items;

    @Autowired
    private ItemDao itemDao;
    private Item selectedItem;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void save(ActionEvent actionEvent) {
        FacesMessage facesMessage;
        try {
            itemDao.add(item);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Item " + item.toString() + " is saved");
            item = new Item();
        } catch (Exception e) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred! ", e.toString());
        }

        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public List<String> getByBarcode(String barcode) {
        List<Item> foundItem = itemDao.findByBarcode(barcode);
        List<String> barcodes = new ArrayList<String>();
        for (Item i : foundItem) {
            barcodes.add(i.getBarcode());
        }
        return barcodes;
    }

    public List<Item> getAllItems(){
        return itemDao.getAll();
    }

    public void search(ActionEvent actionEvent) {
        items = itemDao.findByBarcode(barcode);
    }

    public void update() {
        FacesMessage facesMessage;
        try {
            itemDao.update(selectedItem);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", selectedItem.toString() + " is updated successfully");
            selectedItem = null;
        } catch (Exception e) {
            log.error(e.toString());
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public void remove() {
        FacesMessage facesMessage;
        try {
            itemDao.delete(selectedItem);
            items.remove(selectedItem);
            selectedItem = new Item();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "item is deleted successfully");
        } catch (Exception e) {
            log.error(e.toString());
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

}
