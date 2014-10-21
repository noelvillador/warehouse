package org.warehouse.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

/**
 * @author dev11
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
        @NamedQuery(name = "Employee.findById", query = "SELECT e FROM Employee e WHERE e.employeeId = :employeeId"),
        @NamedQuery(name = "Employee.findByCpr", query = "SELECT e FROM Employee e WHERE e.cpr = :cpr"),
        @NamedQuery(name = "Employee.findByDateOfBirth", query = "SELECT e FROM Employee e WHERE e.birthday = :birthday"),
        @NamedQuery(name = "Employee.findByFullName", query = "SELECT e FROM Employee e WHERE e.fullName = :fullName"),
        @NamedQuery(name = "Employee.findLikeFullName", query = "SELECT e FROM Employee e WHERE e.fullName like :fullName"),
        @NamedQuery(name = "Employee.findByGrade", query = "SELECT e FROM Employee e WHERE e.grade = :grade"),
        @NamedQuery(name = "Employee.findByUsername", query = "SELECT e FROM Employee e WHERE e.username = :username"),
        @NamedQuery(name = "Employee.findByGender", query = "SELECT e FROM Employee e WHERE e.gender = :gender")})
public class Employee implements Serializable {

    private static final long serialVersionUID = -6556198820660676462L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employeeId", nullable = false)
    private Integer employeeId;

    @Column(name = "cpr", nullable = false)
    private BigInteger cpr;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Size(max = 255)
    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(name = "grade")
    private Integer grade;

    @Basic
    @Column(name = "gender", length = 6)
    private String gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private Collection<Inventory> inventories;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Basic
    @Column(name = "role", length = 5)
    private String role;


    public Employee() {
    }

    public Employee(BigInteger cpr, Date birthday, String fullName, Integer grade, String gender, String username, String password, String role) {
        this.cpr = cpr;
        this.birthday = birthday;
        this.fullName = fullName;
        this.grade = grade;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public BigInteger getCpr() {
        return cpr;
    }

    public void setCpr(BigInteger cpr) {
        this.cpr = cpr;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date dateOfBirth) {
        this.birthday = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<Inventory> getInventories() {
        return inventories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (birthday != null ? !birthday.equals(employee.birthday) : employee.birthday != null) return false;
        if (cpr != null ? !cpr.equals(employee.cpr) : employee.cpr != null) return false;
        if (employeeId != null ? !employeeId.equals(employee.employeeId) : employee.employeeId != null) return false;
        if (fullName != null ? !fullName.equals(employee.fullName) : employee.fullName != null) return false;
        if (gender != null ? !gender.equals(employee.gender) : employee.gender != null) return false;
        if (grade != null ? !grade.equals(employee.grade) : employee.grade != null) return false;
        if (password != null ? !password.equals(employee.password) : employee.password != null) return false;
        if (role != null ? !role.equals(employee.role) : employee.role != null) return false;
        if (username != null ? !username.equals(employee.username) : employee.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (cpr != null ? cpr.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", cpr=" + cpr +
                ", birthday=" + birthday +
                ", fullName='" + fullName + '\'' +
                ", grade=" + grade +
                ", gender=" + gender +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
