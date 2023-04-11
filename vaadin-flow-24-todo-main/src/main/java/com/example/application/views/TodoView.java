package com.example.application.views;

import com.example.application.data.entity.ListEntry;
import com.example.application.data.repository.ListEntryRepo;
import com.example.application.data.service.ToDoListService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.List;

@Route(value="todo", layout =LayoutView.class)
public class TodoView extends VerticalLayout {

    private ListEntryRepo service;
    TextField descriptionField = new TextField();
    Button addButton = new Button("Add");
    VerticalLayout todos = new VerticalLayout();
    Grid<ListEntry> grid = new Grid<>(ListEntry.class);

    public TodoView(ListEntryRepo service) {
        this.service = service;

        todos.setPadding(false);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(click -> {
            var entry = new ListEntry(descriptionField.getValue());
            service.save(entry);
            todos.add(createCheckbox(entry));
            descriptionField.clear();
            grid.setItems(new ListDataProvider<>(service.findAll()));

        });
        grid.setColumns("Description", "Done");
        add(
                new H1("Todo"),
                new HorizontalLayout(descriptionField, addButton),
                grid
        );

    }

    private Component createCheckbox(ListEntry listEntry) {
        return new Checkbox(listEntry.getDescription(), listEntry.getDone(), e -> {
            listEntry.setDone(e.getValue());
        });
    }
}
