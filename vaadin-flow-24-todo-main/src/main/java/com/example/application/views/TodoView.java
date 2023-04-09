package com.example.application.views;

import com.example.application.data.entity.ListEntry;
import com.example.application.data.repository.ListEntryRepo;
import com.example.application.data.service.ToDoListService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value="todo", layout =LayoutView.class)
public class TodoView extends VerticalLayout {

    private ToDoListService service;

    public TodoView(ToDoListService service) {
        this.service = service;

        var descriptionField = new TextField();
        var button = new Button("Add");
        var todos = new VerticalLayout();

        todos.setPadding(false);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickShortcut(Key.ENTER);
        button.addClickListener(click -> {
            var entry = new ListEntry(descriptionField.getValue());
            service.saveListEntry(entry);
            todos.add(createCheckbox(entry));
           //var todo = repo.save(new ListEntry(task.getValue()));
            //todos.add(createCheckbox(todo));
            descriptionField.clear();
        });

        service.findAllListEntries().forEach(listEntry -> todos.add(createCheckbox(listEntry)));

        add(
                new H1("Todo"),
                new HorizontalLayout(descriptionField, button),
                todos
        );
    }

    private Component createCheckbox(ListEntry listEntry) {
        return new Checkbox(listEntry.getDescription(), listEntry.isDone(), e -> {
            listEntry.setDone(e.getValue());
            //repo.save(listEntry);
        });
    }
}
