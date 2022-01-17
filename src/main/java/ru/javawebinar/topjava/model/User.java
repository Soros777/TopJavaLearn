package ru.javawebinar.topjava.model;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealUtil.DEFAULT_CALORIES_PER_DAY;

public class User extends AbstractNamedEntity {
    private String email;
    private String password;
    private boolean enabled;
    private Date registered = new Date();
    private int caloriesPerDay;
    private Set<Role> roles;

    public User(Integer id, String name, String email, String password, Role role, Role ... roles) {
        this(id, name, email, password, true, DEFAULT_CALORIES_PER_DAY, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, boolean enabled, int caloriesPerDay, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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