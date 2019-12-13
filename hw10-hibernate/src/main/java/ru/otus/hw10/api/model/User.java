package ru.otus.hw10.api.model;


import javax.persistence.*;
import java.util.Set;

/** Пользователь. */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = AddressDataSet.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @OneToMany(targetEntity = PhoneDataSet.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private Set<PhoneDataSet> phones;

    public User() {
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, AddressDataSet address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public User(long id, String name, AddressDataSet address, Set<PhoneDataSet> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
