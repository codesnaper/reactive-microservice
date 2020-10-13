package org.example.product.modal;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ex_product_user")
@SQLDelete(sql = "update ex_product_user set is_deleted=TRUE where id = ?")
//@Where(clause = "is_deleted = FALSE")
@NamedQueries(value = {
        @NamedQuery(name = "findUser",query = "select user from User user where userName=:userName")
})
public class User {

    @Id
    @GeneratedValue
    @Column( name = "id",nullable = false)
    private int id;

    @Column( name = "username",nullable = false)
    private String userName;

    @Column( name = "password",nullable = false)
    private String password;

    @Column( name = "accountNonExpired",nullable = false)
    private Boolean accountNonExpired;

    @Column( name = "accountNonLocked",nullable = false)
    private Boolean accountNonLocked;

    @Column( name = "credentialsNonExpired",nullable = false)
    private Boolean credentialsNonExpired;

    @Column( name = "enabled",nullable = false)
    private Boolean enabled;

    @Column(name = "is_deleted")
    private boolean deletedFalg = false;

    @Column(name = "user_creationdate",nullable = false)
    @CreationTimestamp
    private Date userCreationDate;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<UserRole> userRoleList  = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDeletedFalg() {
        return deletedFalg;
    }

    public void setDeletedFalg(boolean deletedFalg) {
        this.deletedFalg = deletedFalg;
    }

    public Date getUserCreationDate() {
        return userCreationDate;
    }

    public void setUserCreationDate(Date userCreationDate) {
        this.userCreationDate = userCreationDate;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }
}
