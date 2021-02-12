package com.unidates.Unidates.UniDates.Unit;

import com.unidates.Unidates.UniDates.Manager.Registrazione.ConfermaRegistrazione;
import com.unidates.Unidates.UniDates.Manager.Registrazione.OnRegistrationCompleteEvent;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class TestOnRegistrationCompleteEvent {

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private ConfermaRegistrazione confermaRegistrazione;

    @Test
    public void launchEvent_valid() {

        Studente utente = new Studente("testregistrazione@gmail.com", "passwordp");
        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(utente, Locale.getDefault(), "");
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        SimpleMailMessage oracolo = new SimpleMailMessage();
        oracolo.setTo(utente.getEmail());
        oracolo.setText("http://localhost:8080" + event.getAppUrl() + "/api/UserManager/registrationConfirm?token=");
        oracolo.setSubject("Conferma la registrazione");
        SimpleMailMessage ritornato = confermaRegistrazione.confermaRegistrazione(event);
        ritornato.setText(ritornato.getText().substring(0, ritornato.getText().length() - 36));
        Assertions.assertEquals(oracolo, ritornato);
    }

}