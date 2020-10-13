package org.example.product.modal;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "ex_product_role")
@SQLDelete(sql = "update ex_product_role set is_deleted=TRUE where id = ?")
//@Where(clause = "is_deleted = FALSE")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    @Column( name = "id",nullable = false)
    private int id;

    @Column( name = "roleName",nullable = false)
    private String roleName;

    @Column(name = "is_deleted")
    private boolean deletedFalg = false;

    @OneToOne(mappedBy = "role",cascade = {CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.LAZY)
    private UserRole userRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isDeletedFalg() {
        return deletedFalg;
    }

    public void setDeletedFalg(boolean deletedFalg) {
        this.deletedFalg = deletedFalg;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return "ROLE_".concat(this.getRoleName());
    }
}
