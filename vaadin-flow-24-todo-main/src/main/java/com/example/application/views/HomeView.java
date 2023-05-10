package com.example.application.views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="", layout=LayoutView.class)
public class HomeView extends VerticalLayout {
    HomeView() {
        add(new H2("Welcome!"), new Paragraph("This is a simple Web-Application done with the help of the Web-Framework Vaadin.\n" +
                "The Web-Application is a simple todo List, new List entries can be created, existing ones can be edited and Entries can be deleted."));
    }

}
