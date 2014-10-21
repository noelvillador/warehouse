package org.warehouse.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.InventoryDao;
import org.warehouse.model.Inventory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Noel on 9/4/14.
 */
@Repository
@Transactional
public class InventoryJpaDao implements InventoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Inventory inventory) {
        em.persist(inventory);
    }

    @Override
    public void update(Inventory inventory) {
        em.merge(inventory);
    }

    @Override
    public Inventory getInventory(Integer inventoryId) {
        return em.find(Inventory.class, inventoryId);
    }

    @Override
    public void delete(Inventory inventory) {
        Query q = em.createQuery("delete from Inventory where inventoryId = :inventoryId");
        q.setParameter("inventoryId", inventory.getInventoryId());
        q.executeUpdate();
    }

    @Override
    public List<Inventory> getAll() {
        return em.createNamedQuery("Inventory.findAll").getResultList();
    }

    @Override
    public List<Inventory> findByDepartment(String name) {
        return em.createNamedQuery("Inventory.findByDepartment").setParameter("department", name + "%").getResultList();
    }

}
