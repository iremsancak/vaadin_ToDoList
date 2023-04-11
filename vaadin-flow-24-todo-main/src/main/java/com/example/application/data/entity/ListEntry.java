package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

@Entity
@Table(name = "todo_listentry_vaadin")
public class ListEntry {
    @Id
    @GeneratedValue
    private int Id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListEntry listEntry = (ListEntry) o;
        return Id == listEntry.Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @NotEmpty
    private String Description ;
    private boolean Done;

    public ListEntry(String Description) {
        this.Description = Description;
        Done = false;
    }

    public ListEntry() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public boolean getDone() {
        return Done;
    }

    public void setDone(boolean Done) {
        this.Done = Done;
    }
}
