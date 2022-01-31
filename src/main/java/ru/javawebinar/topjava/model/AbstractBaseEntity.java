package ru.javawebinar.topjava.model;

import org.springframework.util.Assert;

public abstract class AbstractBaseEntity {
    public static int START_SEQUENCE = 100000;

    protected Integer id;

    protected AbstractBaseEntity() {}

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer id() {
        Assert.notNull(id, "id must not be null");
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " : " + id;
    }
}
