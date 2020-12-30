package com.unidates.Unidates.UniDates.Service.GestioneUtenti;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneUtenti.ModeratoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeratoreService {

    @Autowired
    ModeratoreRepository moderatoreRepository;

    public Moderatore findByEmail(String email){
        return moderatoreRepository.findByEmail(email);
    }



}