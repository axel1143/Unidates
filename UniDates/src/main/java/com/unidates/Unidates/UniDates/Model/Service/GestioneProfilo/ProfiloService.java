package com.unidates.Unidates.UniDates.Model.Service.GestioneProfilo;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.FotoRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneProfilo.ProfiloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfiloService {

    @Autowired
    private ProfiloRepository profiloRepository;

    @Autowired
    private FotoRepository fotoRepository;

    public void eliminaProfilo(Profilo profilo, String password){
        profiloRepository.deleteById(profilo.getId());
    }

    public void modificaProfilo(Profilo profilo, String password){
        profiloRepository.save(profilo);
    }

    public void aggiungiFoto(Profilo profilo, Foto foto){

        foto.setProfilo(profilo);
        fotoRepository.save(foto);
    }

    public void eliminaFoto(Foto foto){
        fotoRepository.delete(foto);
    }

    public Profilo visualizzaProfilo(Studente studente){
        return studente.getProfilo();
    }
}