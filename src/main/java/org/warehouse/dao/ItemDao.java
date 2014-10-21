package org.warehouse.dao;

import org.warehouse.model.Item;

import java.util.List;

/**
 * Created by Noel on 8/13/14.
 */
public interface ItemDao {

    public void add(Item item);

    public void update(Item item);

    public Item getItem(Integer itemId);

    public void delete(Item item);

    public List<Item> getAll();

    public List<Item> getByBarcode(String itemName);

    List<Item> findByBarcode(String barcode);
}
