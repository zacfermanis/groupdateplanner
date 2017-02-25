package com.groupdateplanner.planner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A PotentialEventDate.
 */
@Entity
@Table(name = "potential_event_date")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PotentialEventDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @Column(name = "total_accepted")
    private Integer totalAccepted;

    @Column(name = "total_invited")
    private Integer totalInvited;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "potential_event_date_accepted_user",
               joinColumns = @JoinColumn(name="potential_event_dates_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="accepted_users_id", referencedColumnName="id"))
    private Set<User> acceptedUsers = new HashSet<>();

    @ManyToMany(mappedBy = "potentialEventDates")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Event> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public PotentialEventDate startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public PotentialEventDate endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalAccepted() {
        return totalAccepted;
    }

    public PotentialEventDate totalAccepted(Integer totalAccepted) {
        this.totalAccepted = totalAccepted;
        return this;
    }

    public void setTotalAccepted(Integer totalAccepted) {
        this.totalAccepted = totalAccepted;
    }

    public Integer getTotalInvited() {
        return totalInvited;
    }

    public PotentialEventDate totalInvited(Integer totalInvited) {
        this.totalInvited = totalInvited;
        return this;
    }

    public void setTotalInvited(Integer totalInvited) {
        this.totalInvited = totalInvited;
    }

    public Set<User> getAcceptedUsers() {
        return acceptedUsers;
    }

    public PotentialEventDate acceptedUsers(Set<User> users) {
        this.acceptedUsers = users;
        return this;
    }

    public PotentialEventDate addAcceptedUser(User user) {
        this.acceptedUsers.add(user);
        user.getPotentialEventDates().add(this);
        return this;
    }

    public PotentialEventDate removeAcceptedUser(User user) {
        this.acceptedUsers.remove(user);
        user.getPotentialEventDates().remove(this);
        return this;
    }

    public void setAcceptedUsers(Set<User> users) {
        this.acceptedUsers = users;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public PotentialEventDate events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public PotentialEventDate addEvent(Event event) {
        this.events.add(event);
        event.getPotentialEventDates().add(this);
        return this;
    }

    public PotentialEventDate removeEvent(Event event) {
        this.events.remove(event);
        event.getPotentialEventDates().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PotentialEventDate potentialEventDate = (PotentialEventDate) o;
        if (potentialEventDate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, potentialEventDate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PotentialEventDate{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", totalAccepted='" + totalAccepted + "'" +
            ", totalInvited='" + totalInvited + "'" +
            '}';
    }
}
