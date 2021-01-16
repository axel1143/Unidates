package com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni;

import javax.persistence.*;

@Entity
@Table(name = "Messaggio")
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private String testoMessaggio;

    private String emailMittente;
    private String emailDestinatario;


    public Messaggio() {
    }

    public Messaggio(Chat chat, String testoMessaggio, String emailMittente, String emailDestinatario) {
        this.chat = chat;
        this.testoMessaggio = testoMessaggio;
        this.emailMittente = emailMittente;
        this.emailDestinatario = emailDestinatario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getTestoMessaggio() {
        return testoMessaggio;
    }

    public void setTestoMessaggio(String testoMessaggio) {
        this.testoMessaggio = testoMessaggio;
    }

    public String getEmailMittente() {
        return emailMittente;
    }

    public void setEmailMittente(String emailMittente) {
        this.emailMittente = emailMittente;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

}
