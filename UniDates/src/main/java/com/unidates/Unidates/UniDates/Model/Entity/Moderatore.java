package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Moderatore extends Utente {

    @OneToMany(mappedBy = "moderatore", cascade = CascadeType.REMOVE)
    private Collection<Ammonimenti> AmmonimentiInviati;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "studente_id", referencedColumnName = "id")
    private Studente studente;

    @OneToMany(mappedBy = "moderatore", cascade = CascadeType.REMOVE)
    private Collection<Segnalazioni> segnalazioniRicevute;

    public Moderatore(){
    }

    public Moderatore(String email, String password, Studente studente) {
        super(email, password, Ruolo.MODERATORE);
        AmmonimentiInviati = new ArrayList<Ammonimenti>();
        this.studente = studente;
    }

    public void addAmmonimento(Ammonimenti ammonimento){
        AmmonimentiInviati.add(ammonimento);
    }

    public Collection<Ammonimenti> getAmmonimentiInviati() {
        return AmmonimentiInviati;
    }

    public void setAmmonimentiInviati(Collection<Ammonimenti> ammonimentiInviati) {
        AmmonimentiInviati = ammonimentiInviati;
    }

    public Collection<Segnalazioni> getSegnalazioniRicevute() {
        return segnalazioniRicevute;
    }

    public void setSegnalazioniRicevute(Collection<Segnalazioni> segnalazioniRicevute) {
        this.segnalazioniRicevute = segnalazioniRicevute;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }
}
