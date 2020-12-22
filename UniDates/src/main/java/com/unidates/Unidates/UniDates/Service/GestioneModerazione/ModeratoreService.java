package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.Moderatore;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.ModeratoreRepository;
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