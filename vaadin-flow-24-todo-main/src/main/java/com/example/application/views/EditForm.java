package com.example.application.views;

import com.example.application.data.entity.ListEntry;
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
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class EditForm extends FormLayout {
    TextField description = new TextField("Description");
    Checkbox isDone = new Checkbox("Done");
    Button save = new Button("Save");
    Button close = new Button("Cancel");
    private ListEntry listEntry;
    Binder<ListEntry> binder = new BeanValidationBinder<>(ListEntry.class);
    public EditForm(ListEntry listEntry) {
        binder.bind(description, ListEntry::getDescription, ListEntry::setDescription);
        binder.bind(isDone, ListEntry::getDone, ListEntry::setDone);
        VerticalLayout v = new VerticalLayout();
        v.add(description, isDone, createButtonsLayout());
        add(v);
    }
    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, close);
    }
    private void validateAndSave() {
        try {
            binder.writeBean(listEntry);
            fireEvent(new SaveEvent(this, listEntry));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    public void setListEntry(ListEntry listEntry) {
        this.listEntry = listEntry;
        binder.readBean(listEntry);
    }
    public static abstract class EditFormEvent extends ComponentEvent<EditForm> {
        private ListEntry listEntry;

        protected EditFormEvent(EditForm source, ListEntry listEntry) {
            super(source, false);
            this.listEntry = listEntry;
        }

        public ListEntry getListEntry() {
            return listEntry;
        }
    }

    public static class SaveEvent extends EditFormEvent {
        SaveEvent(EditForm source, ListEntry listEntry) {
            super(source, listEntry);
        }
    }


    public static class CloseEvent extends EditFormEvent {
        CloseEvent(EditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
