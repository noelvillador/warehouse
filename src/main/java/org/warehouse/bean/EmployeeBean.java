package org.warehouse.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.EmployeeDao;
import org.warehouse.model.Employee;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noel on 8/27/14.
 */
@Component("employeeBean")
@RequestScoped
public class EmployeeBean implements Serializable {

    private Logger log = LoggerFactory.getLogger(EmployeeBean.class);

    private static final long serialVersionUID = -8496490937368057233L;

    private Employee employee = new Employee();

    @Autowired
    private EmployeeDao employeeDao;

    private String employeeName;

    private List<Employee> employees;

    private Employee selectedEmployee;

    public Employee getEmployee() {
        return employee;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public List<String> getByEmployeeName(String name) {
        List<Employee> foundEmployee = employeeDao.findByFullName(name);
        List<String> names = new ArrayList<String>();
        for (Employee e : foundEmployee) {
            names.add(e.getFullName());
        }
        return names;
    }

    public List<Employee> getAllEmployees(){
        return employeeDao.getAll();
    }

    public void uniqueUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String username = (String) value;
        if (employeeDao.findByUsername(username).size() != 0) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "error: ", "username already exists!"));
        }
    }

    public void uniqueUsernameUpdate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String username = (String) value;
        if (employeeDao.findByUsernameUpdate(selectedEmployee, username).size()>0) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "error: ", "username already exists!"));
        }
    }

    public void save(ActionEvent actionEvent) {
        FacesMessage facesMessage;
        try {
            employeeDao.add(employee);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Employee " + employee.toString() + " is saved");
            employee = new Employee();
        } catch (Exception e) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred! ", e.toString());
            log.error(e.toString());
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public void search(ActionEvent actionEvent) {
        employees = employeeDao.findByFullName(employeeName);
    }

    public void update() {
        FacesMessage facesMessage;
        try {
            employeeDao.update(selectedEmployee);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", selectedEmployee.toString() + " is updated successfully");
            selectedEmployee = null;
        } catch (Exception e) {
            log.error(e.toString());
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public void remove() {
        FacesMessage facesMessage;
        try {
            employeeDao.delete(selectedEmployee);
            employees.remove(selectedEmployee);
            selectedEmployee = new Employee();
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "employee is deleted successfully");
        } catch (Exception e) {
            log.error(e.toString());
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error occurred", e.toString());
        }
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}