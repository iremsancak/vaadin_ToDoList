package com.example.application.views;

import com.example.application.data.entity.ListEntry;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class DeleteForm extends FormLayout {
    private ListEntry listEntry;
    Binder<ListEntry> binder = new BeanValidationBinder<>(ListEntry.class);
    Checkbox isDone = new Checkbox("isDone");
    TextField description = new TextField("Description");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    public DeleteForm(ListEntry listEntry) {
        binder.bind(description, ListEntry::getDescription, ListEntry::setDescription);
        binder.bind(isDone, ListEntry::getDone, ListEntry::setDone);
        isDone.setReadOnly(true);
        description.setEnabled(false);
        VerticalLayout v = new VerticalLayout();
        v.add(description, isDone, createButtonsLayout());
        add(v);
    }
    public void setListEntry(ListEntry listEntry) {
        this.listEntry = listEntry;
        binder.readBean(listEntry);
    }
    private Component createButtonsLayout() {
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        close.addClickShortcut(Key.ESCAPE);

        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, listEntry)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        return new HorizontalLayout(delete, close);
    }
    public static abstract class DeleteFormEvent extends ComponentEvent<DeleteForm> {
        private ListEntry listEntry;

        protected DeleteFormEvent(DeleteForm source, ListEntry listEntry) {
            super(source, false);
            this.listEntry = listEntry;
        }

        public ListEntry getListEntry() {
            return listEntry;
        }
    }
    public static class DeleteEvent extends DeleteFormEvent {
        DeleteEvent(DeleteForm source, ListEntry listEntry) {
            super(source, listEntry);
        }

    }

    public static class CloseEvent extends DeleteFormEvent {
        CloseEvent(DeleteForm source) {
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
    public Registration addDeleteListener(ComponentEventListener<DeleteForm.DeleteEvent> listener) {
        return addListener(DeleteForm.DeleteEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<DeleteForm.CloseEvent> listener) {
        return addListener(DeleteForm.CloseEvent.class, listener);
    }
}
