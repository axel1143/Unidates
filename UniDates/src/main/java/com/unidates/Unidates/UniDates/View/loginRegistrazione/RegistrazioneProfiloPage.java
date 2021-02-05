package com.unidates.Unidates.UniDates.View.loginRegistrazione;

import com.unidates.Unidates.UniDates.Controller.ModifyProfileControl;
import com.unidates.Unidates.UniDates.Controller.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.AlreadyExistUserException;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.gatanaso.MultiselectComboBox;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Route(value = "registrazione_due")
@PageTitle("Registrazione_2")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/views/registrazione/registrazione_due.css")
public class RegistrazioneProfiloPage extends VerticalLayout {

    @Autowired
    UserManagementControl userManagementControl;

    @Autowired
    ModifyProfileControl modifyProfileControl;


    private Select<String> interessi = new Select<>();
    private TextField nome, residenza, luogo_di_nascita, cognome, numero, contatto_ig;
    private DatePicker picker = new DatePicker();
    private Select<String> capelli = new Select<>();
    private Select<String> occhi = new Select<>();
    private NumberField altezza;
    private RadioButtonGroup<String> sessi = new RadioButtonGroup<>();
    private MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
    private Checkbox checkbox;
    private MemoryBuffer image;
    private FotoDTO toadd;


    public RegistrazioneProfiloPage(){
        addAttachListener(event -> create());
    }

    public void create(){
        StudenteDTO da_registrare = (StudenteDTO) UI.getCurrent().getSession().getAttribute("utente_reg");

        if(da_registrare == null){
           UI.getCurrent().navigate("registrazione");
        }

        setSizeFull();

        VerticalLayout padre = new VerticalLayout();
        padre.setAlignItems(Alignment.CENTER);
        padre.setId("principale");
        setAlignItems(Alignment.CENTER);

        HorizontalLayout verticals = new HorizontalLayout();
        verticals.setId("layouts-vertical");
        VerticalLayout sinistra = new VerticalLayout(primo());
        VerticalLayout destra = new VerticalLayout(secondo());

        HorizontalLayout topics = new HorizontalLayout();
        multiselectComboBox.setLabel("Seleziona Topic");
        multiselectComboBox.setPlaceholder("Scelti...");
        Hobby [] topic = Hobby.values();
        List<String> topiclist = new ArrayList<String>();
        for(Hobby h : topic) topiclist.add(h.toString());
        multiselectComboBox.setItems(topiclist);

        topics.add(multiselectComboBox);

        verticals.add(sinistra,destra);

        checkbox = new Checkbox();
        checkbox.setLabel("Acconsenti il trattamento dei dati");

        Button conferma = new Button("Conferma",buttonClickEvent -> {
            //Sessione
            if(nome.isEmpty()){
                Notification nome_errore = new Notification("Inserisci il campo Nome",3000, Notification.Position.MIDDLE);
                nome_errore.open();
            }
            else if(cognome.isEmpty()){
                Notification cognome_errore = new Notification("Inserisci il campo Cognome",3000, Notification.Position.MIDDLE);
                cognome_errore.open();
            }
            else if(picker.isEmpty() || !checkMaggiorenne(picker.getValue())){
                Notification picker_errore = new Notification("Devi essere maggiorenne per registrarti",3000, Notification.Position.MIDDLE);
                picker_errore.open();
            }
            else if(luogo_di_nascita.isEmpty()){
                Notification luogo_di_nascita_errore = new Notification("Inserisci il campo Luogo di nascita",3000, Notification.Position.MIDDLE);
                luogo_di_nascita_errore.open();
            }
            else if(residenza.isEmpty()){
                Notification residenza_errore = new Notification("Inserisci il campo Residenza",3000, Notification.Position.MIDDLE);
                residenza_errore.open();
            }
            else if(sessi.isEmpty()){
                Notification sessi_errore = new Notification("Inserisci il campo Sessi",3000, Notification.Position.MIDDLE);
                sessi_errore.open();
            }
            else if(interessi.isEmpty()){
                Notification interessi_errore = new Notification("Inserisci il campo Interessi",3000, Notification.Position.MIDDLE);
                interessi_errore.open();
            }
            else if(capelli.isEmpty()){
                Notification capelli_errore = new Notification("Inserisci il campo Capelli",3000, Notification.Position.MIDDLE);
                capelli_errore.open();

            }
            else if(occhi.isEmpty()){
                Notification occhi_errore = new Notification("Inserisci il campo Occhi",3000, Notification.Position.MIDDLE);
                occhi_errore.open();
            }
            else if(altezza.isEmpty()){
                Notification altezza_errore = new Notification("Inserisci il campo Altezza",3000, Notification.Position.MIDDLE);
                altezza_errore.open();
            }
            else if(multiselectComboBox.isEmpty()){
                Notification topic_errore = new Notification("Inserisci il campo Topic",3000, Notification.Position.MIDDLE);
                topic_errore.open();
            }
            else if(checkbox.isEmpty()){
                Notification dati_errore = new Notification("Acconsenti al trattamento dati",3000, Notification.Position.MIDDLE);
                dati_errore.open();
            }

            else {
                ArrayList<Hobby> hobby = new ArrayList<Hobby>();
                for (String s : multiselectComboBox.getValue()) hobby.add(Hobby.valueOf(s));



                ProfiloDTO  profiloDTO = new ProfiloDTO(nome.getValue(),cognome.getValue(),luogo_di_nascita.getValue(),residenza.getValue(),picker.getValue(),
                        altezza.getValue(),Sesso.valueOf(sessi.getValue()),Interessi.valueOf(interessi.getValue()),Colori_Capelli.valueOf(capelli.getValue()),
                        Colore_Occhi.valueOf(occhi.getValue()),hobby, toadd); //AGGIUNGERE TELEFONO E CONTATTO
                profiloDTO.setNumeroTelefono(numero.getValue());
                profiloDTO.setNickInstagram(contatto_ig.getValue());

                StudenteDTO studenteDTO = new StudenteDTO(da_registrare.getEmail(),da_registrare.getPassword(), profiloDTO);

                try {
                    userManagementControl.registrazioneStudente(studenteDTO, VaadinServletRequest.getCurrent());
                }
                catch (AlreadyExistUserException e){
                   new Notification("Email già in uso", 2000, Notification.Position.MIDDLE).open();

                }
                catch (InvalidFormatException e){
                    new Notification("Uno o più campi non validi", 2000, Notification.Position.MIDDLE).open();

                }

                //NOTIFICA DI SUCCESSO REGISTRAZIONE
                Notification successo_registrazione = new Notification();
                successo_registrazione.setPosition(Notification.Position.MIDDLE);
                H5 notifica = new H5("La registrazione è andata a buon fine. Confermare l'email di validazione prima di accedere al sistama");
                Button ricevuto = new Button("OK");
                ricevuto.addClickListener(buttonClickEvent1 -> {
                    UI.getCurrent().navigate("login");
                    successo_registrazione.close();
                });
                VerticalLayout successo_registrazione_layout = new VerticalLayout();
                successo_registrazione_layout.setAlignItems(Alignment.CENTER);
                successo_registrazione_layout.add(notifica,ricevuto);
                successo_registrazione.add(successo_registrazione_layout);
                successo_registrazione.open();
            }
        });


        Span star = new Span("I campi (*) non sono obbligatori");
        H2 titolo = new H2("Inserisci i dati del profilo!");
        titolo.setId("titolo-registrazione");

        padre.add(titolo,verticals,topics,checkbox,star,conferma);
        add(padre);
    }

    private boolean checkMaggiorenne(LocalDate value) {
       return Period.between(value,LocalDate.now()).getYears() >= 18;
    }

    public VerticalLayout primo(){
        VerticalLayout sinistra = new VerticalLayout();
        sinistra.setId("layout-sinistra");
        nome = new TextField("Nome");
        cognome = new TextField("Cognome");
        picker = new DatePicker("Data di nascita");
        luogo_di_nascita = new TextField("Luogo di nascita");
        residenza = new TextField("Residenza");

        sessi.setLabel("Il tuo sesso:");
        Sesso[] sess = Sesso.values();
        sessi.setItems(sess[0].toString(),sess[1].toString(),sess[2].toString());
        sessi.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        sinistra.add(nome,cognome,picker,luogo_di_nascita,residenza,sessi);
        return sinistra;
    }



    public VerticalLayout secondo(){
        VerticalLayout destra = new VerticalLayout();
        destra.setId("layout-destra");

        numero = new TextField();
        numero.setLabel("Numero di telefono (*)");

        contatto_ig = new TextField();
        contatto_ig.setLabel("Contatto instagram (*)");

        interessi.setLabel("Interessi");
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());

        capelli.setLabel("Capelli");
        capelli.setPlaceholder("Colore capelli");
        Colori_Capelli [] colore_cap = Colori_Capelli.values();
        capelli.setItems(colore_cap[0].toString(),colore_cap[1].toString(),colore_cap[2].toString(),colore_cap[3].toString(),colore_cap[4].toString(),colore_cap[5].toString(),colore_cap[6].toString());

        occhi.setLabel("Occhi");
        occhi.setPlaceholder("Colore occhi");
        Colore_Occhi [] colore_occhi = Colore_Occhi.values();
        occhi.setItems(colore_occhi[0].toString(),colore_occhi[1].toString(),colore_occhi[2].toString(),colore_occhi[3].toString(),colore_occhi[4].toString(),colore_occhi[5].toString(),colore_occhi[6].toString());

        altezza = new NumberField("Altezza (cm)");
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);

        image = new MemoryBuffer();
        Span dropIcon = new Span("Inserisci una foto profilo!");
        Upload upload = new Upload(image);
        upload.setDropLabel(dropIcon);
        upload.setMaxFiles(1);
        Div output = new Div();
        output.getStyle().set("max-width","20px");
        output.getStyle().set("max-heght","20px");
        upload.addSucceededListener(event -> {
            try{
                toadd = new FotoDTO(image.getInputStream().readAllBytes());

                Component component = createComponent(event.getMIMEType(),event.getFileName(),image.getInputStream());
                HtmlComponent p = new HtmlComponent(Tag.P);
                p.getElement().setText(event.getFileName());
                upload.getElement().addEventListener("file-remove", new DomEventListener() {
                    @Override
                    public void handleEvent(DomEvent domEvent) {
                        component.setVisible(false);
                        p.setVisible(false);
                        toadd = null;

                    }
                });
                component.setId("component");
                output.add(p);
                output.add(component);
                //showOutput(event.getFileName(), component, output); ///where is this defined???
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        destra.add(numero,contatto_ig,interessi,capelli,occhi,altezza,upload,output);
        return destra;
    }


    private Component createComponent(String mimeType, String fileName, InputStream stream) {
        if (mimeType.startsWith("text")) {
            return createTextComponent(stream);
        } else if (mimeType.startsWith("image")) {
            Image image = new Image();
            try {
                byte[] bytes = IOUtils.toByteArray(stream);
                image.getElement().setAttribute("src", new StreamResource(fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setWidth(reader.getWidth(0) + "px");
                            image.setHeight(reader.getHeight(0) + "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'", mimeType, MessageDigestUtil.sha256(stream.toString()));
        content.setText(text);
        return content;

    }

    private Component createTextComponent(InputStream stream) {
        String text;
        try {
            text = IOUtils.toString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            text = "exception reading stream";
        }
        return new Text(text);
    }

}