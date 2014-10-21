package org.warehouse.dao;

import org.warehouse.model.Employee;

import java.util.List;

/**
 * Created by Noel on 8/13/14.
 */
public interface EmployeeDao {

    public void add(Employee employee);

    public void update(Employee employee);

    public Employee getEmployee(Integer employeeId);

    public void delete(Employee employee);

    public List<Employee> getAll();

    public List<Employee> getByEmployeeName(String employeeName);

    public List<Employee> findByFullName(String fullname);

    public List<Employee> findByUsername(String username);

    public List<Employee> findByUsernameUpdate(Employee employee, String username);

}
