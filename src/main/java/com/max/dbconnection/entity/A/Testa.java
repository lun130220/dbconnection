package com.max.dbconnection.entity.A;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
//@Table(schema = "testa")
public class Testa{
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name ;
    @Column
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
