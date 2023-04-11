package com.example.application.views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="", layout=LayoutView.class)
public class HomeView extends VerticalLayout {
    HomeView() {
        add(new H2("Home"), new H4("test"));
    }

}
