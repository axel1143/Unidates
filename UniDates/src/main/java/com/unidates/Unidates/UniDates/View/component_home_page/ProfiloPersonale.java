package com.unidates.Unidates.UniDates.View.component_home_page;


import com.example.application.views.Person;
import com.unidates.Unidates.UniDates.View.main.MainViewProfile;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaddon.CustomMediaQuery;

import java.util.Arrays;
import java.util.List;


@Route(value = "profilo-personale", layout = MainViewProfile.class)
@PageTitle("Profilo")
@CssImport("./styles/views/home/profilopersonale.css")
public class ProfiloPersonale extends VerticalLayout implements AfterNavigationObserver {

    Grid<Person> grid = new Grid<>();
    private Select<String> interessi = new Select<>();

    public ProfiloPersonale(){
        setId("personal-profile");
        addClassName("personal-profile");
        setSizeFull();
        grid.setWidth("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn((person -> createSingleUser(person)));
        add(grid);
    }

    private VerticalLayout createSingleUser(Person person){
        VerticalLayout utente = new VerticalLayout();
        utente.addClassName("card-home");
        utente.setSpacing(false);
        utente.getThemeList().add("spacing-s");

        //First Layout
        HorizontalLayout nome = new HorizontalLayout();
        nome.addClassName("top-layout");
        nome.setSpacing(false);
        nome.getThemeList().add("spacing-s");

        Span nome_utente = new Span(person.getName());
        nome.addClassName("nome");
        nome.add(nome_utente);

        //Second Layout
        HorizontalLayout middle = new HorizontalLayout();
        middle.addClassName("middle-layout");
        middle.setSpacing(false);
        middle.getThemeList().add("spacing-s");

        VerticalLayout image = new VerticalLayout();
        image.addClassName("photo");
        Image image_profilo = new Image();
        image_profilo.setSrc(person.getImage());
        image.setId("foto");
        image.add(image_profilo);


        VerticalLayout all_info = new VerticalLayout();

        //info 1
        HorizontalLayout info_uno = new HorizontalLayout();
        TextField name = new TextField("Nome");
        name.setValue(person.getName());
        name.setEnabled(false);

        TextField cognome = new TextField("Cognome");
        cognome.setEnabled(false);

        TextField date = new TextField("Data");
        date.setValue(person.getDate());
        date.setEnabled(false);

        EmailField email_profilo = new EmailField("Email");
        //setta email
        email_profilo.setEnabled(false);

        info_uno.add(name,cognome,date,email_profilo);

        //info 2
        HorizontalLayout info_due = new HorizontalLayout();

        NumberField altezza = new NumberField("Altezza (cm)");
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);
        altezza.setEnabled(false);

        TextField città = new TextField("Città");
        città.setEnabled(false);
        TextField luogo = new TextField("Luogo di nascita");
        luogo.setEnabled(false);

        info_due.add(altezza,città,luogo);

        //info 3
        HorizontalLayout info_tre = new HorizontalLayout();

        TextField capelli = new TextField("Capelli");
        capelli.setEnabled(false);

        TextField occhi = new TextField("Occhi");
        occhi.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setItems("Uomo","Donna","Altro");
        interessi.setEnabled(false);

        info_tre.add(capelli,occhi,interessi);


        MemoryBuffer img = new MemoryBuffer();
        Upload upload = new Upload(img);
        upload.setMaxFiles(1);
        upload.getStyle().set("margin-top","4em");


        all_info.add(info_uno,info_due,info_tre,upload);
        middle.add(image,all_info);

        //Thirs layout
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addClassName("bottom-layout");
        buttons.setSpacing(false);
        buttons.getThemeList().add("spacing-s");

        Button conferma = new Button("Conferma", buttonClickEvent -> {
            name.setEnabled(false);
            date.setEnabled(false);
        });
        conferma.setEnabled(false);
        conferma.setId("confirm");

        Button modifica = new Button("Modifica",buttonClickEvent -> {
            name.setEnabled(true);
            conferma.setEnabled(true);
            date.setEnabled(true);
        });
        modifica.setId("modifica");


        Notification notifica = new Notification();
        notifica.setId("notifica");
        PasswordField prima = new PasswordField("Nuova password:");
        prima.getStyle().set("margin-left","2em");
        PasswordField secondo = new PasswordField("Conferma password");
        secondo.getStyle().set("margin-left","2em");
        Button annulla = new Button("Annulla",buttonClickEvent -> {
            notifica.close();
        });
        annulla.getStyle().set("margin-left","1em");
        Button conf = new Button("Conferma",buttonClickEvent -> {
            //implmenmtare invio notifica
        });
        conf.getStyle().set("margin-left","2em");
        notifica.add(prima,secondo,conf,annulla);
        Button chpassword = new Button("Cambia Password",buttonClickEvent -> {
            notifica.open();
        });
        chpassword.setId("pass");


        buttons.add(modifica,conferma,chpassword);


        utente.add(nome,middle,buttons);
        return  utente;

    }


    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<Person> persons = Arrays.asList( //
                createPerson("https://randomuser.me/api/portraits/men/42.jpg", "John Smith", "May 8",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20")
        );

        grid.setItems(persons);
    }



    private static Person createPerson(String image, String name, String date, String post, String likes,
                                       String comments, String shares) {
        Person p = new Person();
        p.setImage(image);
        p.setName(name);
        p.setDate(date);
        p.setPost(post);
        p.setLikes(likes);
        p.setComments(comments);
        p.setShares(shares);

        return p;
    }
}