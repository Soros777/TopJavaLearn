package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealUtil.DEFAULT_CALORIES_PER_DAY;

@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.GET_BY_EMAIL, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u LEFT JOIN u.roles ORDER BY u.name, u.email")
})

@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity {
    public final static String DELETE = "User.delete";
    public final static String GET_BY_EMAIL = "User.getByEmail";
    public final static String ALL_SORTED = "User.getAllSorted";

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean enabled;

    @NotNull
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    private Date registered = new Date();

    @Column(name = "calories_per_day", nullable = false, columnDefinition = "integer default 2000")
    @Range(min = 10, max = 10000)
    private int caloriesPerDay;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
                        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "unique_user_role")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {}

    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.registered, u.enabled, u.caloriesPerDay, u.roles);
    }

    public User(Integer id, String name, String email, String password, Role role, Role ... roles) {
        this(id, name, email, password, new Date(), true, DEFAULT_CALORIES_PER_DAY, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, Date registered, boolean enabled, int caloriesPerDay, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.enabled = enabled;
        this.caloriesPerDay = caloriesPerDay;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name +
                ", email='" + email +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", caloriesPerDay=" + caloriesPerDay +
                ", roles=" + roles +
                '}';
    }
}
