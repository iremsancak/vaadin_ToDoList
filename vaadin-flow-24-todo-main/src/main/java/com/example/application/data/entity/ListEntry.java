package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo_listentry_vaadin")
public class ListEntry {
    @Id
    @GeneratedValue
    private int Id;
    private String Description;
    private boolean isDone;

    public ListEntry(String Description) {
        this.Description = Description;
        isDone = false;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}
