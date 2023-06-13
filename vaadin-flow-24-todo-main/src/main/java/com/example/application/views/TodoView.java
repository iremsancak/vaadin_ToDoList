package com.example.application.views;

import com.example.application.data.entity.ListEntry;
import com.example.application.data.repository.ListEntryRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.shared.Registration;

import java.util.Collections;

@Route(value="todo", layout =LayoutView.class)
public class TodoView extends VerticalLayout {

    private ListEntryRepo service;
    TextField descriptionField = new TextField();
    Button addButton = new Button("Add");
    DeleteForm deleteForm;
    EditForm editForm;
    Grid<ListEntry> grid = new Grid<>(ListEntry.class, false);

    public TodoView(ListEntryRepo service) {
        this.service = service;
        configureAddButton();
        configureGrid();
        configureForm();
        add(
                new H1("Todo"),
                new HorizontalLayout(descriptionField, addButton),
                getContent()
        );
        closeEditor();
    }
    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, editForm, deleteForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureForm() {
        editForm = new EditForm(null);
        editForm.setWidth("20vw");
        editForm.addSaveListener(this::saveListEntry);
        editForm.addCloseListener(e -> closeEditor());
        deleteForm = new DeleteForm(null);
        deleteForm.addDeleteListener(this::deleteListEntry);
        deleteForm.addCloseListener(e -> closeEditor());
        deleteForm.setWidth("20vw");
    }
    public void configureAddButton() {
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(click -> {
            if(descriptionField.getValue().length() == 0) {
                Notification notification = Notification
                        .show("Description can't be empty!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            else {
                var entry = new ListEntry(descriptionField.getValue());
                service.save(entry);
                descriptionField.clear();
                grid.setItems(new ListDataProvider<>(service.findAll()));
            }

        });
    }
    public void configureGrid() {
        grid.addColumn(listEntry -> listEntry.getDone()).setHeader("isDone");
        grid.addColumn(listEntry -> listEntry.getDescription()).setHeader("Task");
        addEditButton();
        addDeleteButton();
        grid.setHeight("80vh");
        grid.setItems(new ListDataProvider<>(service.findAll()));
    }
    public void addEditButton() {
        grid.addComponentColumn(listEntry -> {
            // edit: added click listener for inline-editing of the person. Editor must be configured for this to work. See https://vaadin.com/components/vaadin-grid/java-examples/grid-editor
            // You don't have to use inline-editing if you don't want. you can also edit the item in a separate Layout with Input fields and a Binder.
            Button editButton = new Button("Edit", click -> {
                closeEditor();
                editForm.setListEntry(listEntry);
                editForm.setVisible(true);
                grid.setItems(new ListDataProvider<>(service.findAll()));
            });
            editButton.getStyle().set("color", "#000000");
            return editButton;
        });
    }
    public void addDeleteButton() {
        grid.addComponentColumn(listEntry -> {
            // edit: added click listener for inline-editing of the person. Editor must be configured for this to work. See https://vaadin.com/components/vaadin-grid/java-examples/grid-editor
            // You don't have to use inline-editing if you don't want. you can also edit the item in a separate Layout with Input fields and a Binder.
            Button deleteButton = new Button("Delete", click -> {
                closeEditor();
                deleteForm.setListEntry(listEntry);
                deleteForm.setVisible(true);
                grid.setItems(new ListDataProvider<>(service.findAll()));
            });
            deleteButton.getStyle().set("color", "#000000");
            return deleteButton;
        });
    }
    private void closeEditor() {
        editForm.setVisible(false);
        deleteForm.setVisible(false);
    }
    private void saveListEntry(EditForm.SaveEvent event) {
        service.save(event.getListEntry());
        grid.setItems(new ListDataProvider<>(service.findAll()));
        closeEditor();
    }
    private void deleteListEntry(DeleteForm.DeleteEvent event) {
        service.delete(event.getListEntry());
        grid.setItems(new ListDataProvider<>(service.findAll()));
        closeEditor();
    }

}
