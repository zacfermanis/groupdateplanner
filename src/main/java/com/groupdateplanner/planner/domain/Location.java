package com.groupdateplanner.planner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.groupdateplanner.planner.domain.enumeration.States;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "street_address_1", nullable = false)
    private String streetAddress1;

    @Column(name = "street_address_2")
    private String streetAddress2;

    @NotNull
    @Size(min = 1)
    @Column(name = "city", nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private States state;

    @NotNull
    @Size(min = 5)
    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private Event event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public Location streetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
        return this;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public Location streetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
        return this;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public States getState() {
        return state;
    }

    public Location state(States state) {
        this.state = state;
        return this;
    }

    public void setState(States state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Location zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getName() {
        return name;
    }

    public Location name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public Location event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + id +
            ", streetAddress1='" + streetAddress1 + "'" +
            ", streetAddress2='" + streetAddress2 + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipcode='" + zipcode + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
