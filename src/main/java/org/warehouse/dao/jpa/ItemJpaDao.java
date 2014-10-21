package org.warehouse.dao.jpa;

import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.ItemDao;
import org.warehouse.model.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Noel on 9/4/14.
 */
@Repository
@Transactional
public class ItemJpaDao implements ItemDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void add(Item item) {
        em.persist(item);
    }

    @Override
    public void update(Item item) {
        em.merge(item);
    }

    @Override
    public Item getItem(Integer itemId) {
        return em.find(Item.class, itemId);
    }

    @Override
    public void delete(Item item) {
        Query q = em.createQuery("delete from Item where itemId = :itemId");
        q.setParameter("itemId", item.getItemId());
        q.executeUpdate();
    }

    @Override
    public List<Item> getAll() {
        return em.createNamedQuery("Item.findAll").getResultList();
    }

    @Override
    public List<Item> getByBarcode(String barcode) {
        return em.createNamedQuery("Item.getByBarcode").setParameter("barcode", barcode).getResultList();
    }

    @Override
    public List<Item> findByBarcode(String barcode) {
        return em.createNamedQuery("Item.findByBarcode").setParameter("barcode", barcode + "%").getResultList();
    }
}
