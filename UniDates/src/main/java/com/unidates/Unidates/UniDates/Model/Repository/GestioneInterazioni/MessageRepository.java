package com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messaggio, Long> {
}