package org.warehouse.dao;

import org.warehouse.model.Inventory;

import java.util.List;

/**
 * Created by Noel on 8/14/14.
 */
public interface InventoryDao {
    
    public void add(Inventory inventory);

    public void update(Inventory inventory);

    public Inventory getInventory(Integer inventoryId);

    public void delete(Inventory inventory);

    public List<Inventory> getAll();

    List<Inventory> findByDepartment(String name);
}
