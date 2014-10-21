package org.warehouse.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.EmployeeDao;
import org.warehouse.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Noel on 8/14/14.
 */

@Repository
@Transactional
public class EmployeeJpaDao implements EmployeeDao {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void add(Employee employee) {
        em.persist(employee);
    }

    @Override
    public void update(Employee employee) {
        em.merge(employee);
    }

    @Override
    public Employee getEmployee(Integer employeeId) {
        return em.find(Employee.class, employeeId);
    }

    @Override
    public void delete(Employee employee) {
        Query q = em.createQuery("delete from Employee where employeeId = :employeeId");
        q.setParameter("employeeId", employee.getEmployeeId());
        q.executeUpdate();
    }

    @Override
    public List<Employee> getAll() {
        return em.createNamedQuery("Employee.findAll").getResultList();
    }

    @Override
    public List<Employee> getByEmployeeName(String employeeName) {
        return em.createNamedQuery("Employee.findByFullName").setParameter("fullName", employeeName).getResultList();
    }

    @Override
    public List<Employee> findByFullName(String fullname) {
        return em.createNamedQuery("Employee.findLikeFullName").setParameter("fullName", fullname + "%").getResultList();
    }

    @Override
    public List<Employee> findByUsername(String username) {
        return em.createNamedQuery("Employee.findByUsername").setParameter("username", username).getResultList();
    }

    @Override
    public List<Employee> findByUsernameUpdate(Employee employee, String username) {
        Query q = em.createQuery("SELECT e FROM Employee e WHERE e.employeeId <> :employeeId AND e.username=:username");
        q.setParameter("employeeId", employee.getEmployeeId());
        q.setParameter("username", username);
        return q.getResultList();
    }
}
