package ru.javawebinar.topjava.model;

import org.hibernate.Hibernate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {
    public final static int START_SEQUENCE = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    protected AbstractBaseEntity() {}

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    // doesn`t work for hibernate lazy proxy
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        AbstractBaseEntity that = (AbstractBaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " : " + id;
    }
}
