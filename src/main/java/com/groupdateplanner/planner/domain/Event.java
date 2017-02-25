package com.groupdateplanner.planner.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "selected_start_date")
    private ZonedDateTime selectedStartDate;

    @Column(name = "selected_end_date")
    private ZonedDateTime selectedEndDate;

    @Column(name = "cost")
    private String cost;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_invited_users",
               joinColumns = @JoinColumn(name="events_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="invited_users_id", referencedColumnName="id"))
    private Set<User> invitedUsers = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_potential_event_date",
               joinColumns = @JoinColumn(name="events_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="potential_event_dates_id", referencedColumnName="id"))
    private Set<PotentialEventDate> potentialEventDates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Event title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getSelectedStartDate() {
        return selectedStartDate;
    }

    public Event selectedStartDate(ZonedDateTime selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
        return this;
    }

    public void setSelectedStartDate(ZonedDateTime selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
    }

    public ZonedDateTime getSelectedEndDate() {
        return selectedEndDate;
    }

    public Event selectedEndDate(ZonedDateTime selectedEndDate) {
        this.selectedEndDate = selectedEndDate;
        return this;
    }

    public void setSelectedEndDate(ZonedDateTime selectedEndDate) {
        this.selectedEndDate = selectedEndDate;
    }

    public String getCost() {
        return cost;
    }

    public Event cost(String cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Set<User> getInvitedUsers() {
        return invitedUsers;
    }

    public Event invitedUsers(Set<User> users) {
        this.invitedUsers = users;
        return this;
    }

    public Event addInvitedUsers(User user) {
        this.invitedUsers.add(user);
        user.getInvitedEvents().add(this);
        return this;
    }

    public Event removeInvitedUsers(User user) {
        this.invitedUsers.remove(user);
        user.getInvitedEvents().remove(this);
        return this;
    }

    public void setInvitedUsers(Set<User> users) {
        this.invitedUsers = users;
    }

    public Location getLocation() {
        return location;
    }

    public Event location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<PotentialEventDate> getPotentialEventDates() {
        return potentialEventDates;
    }

    public Event potentialEventDates(Set<PotentialEventDate> potentialEventDates) {
        this.potentialEventDates = potentialEventDates;
        return this;
    }

    public Event addPotentialEventDate(PotentialEventDate potentialEventDate) {
        this.potentialEventDates.add(potentialEventDate);
        potentialEventDate.getEvents().add(this);
        return this;
    }

    public Event removePotentialEventDate(PotentialEventDate potentialEventDate) {
        this.potentialEventDates.remove(potentialEventDate);
        potentialEventDate.getEvents().remove(this);
        return this;
    }

    public void setPotentialEventDates(Set<PotentialEventDate> potentialEventDates) {
        this.potentialEventDates = potentialEventDates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", selectedStartDate='" + selectedStartDate + "'" +
            ", selectedEndDate='" + selectedEndDate + "'" +
            ", cost='" + cost + "'" +
            '}';
    }
}
