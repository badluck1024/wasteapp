package it.unisalento.pas.smarcity.repositories;

import it.unisalento.pas.smarcity.domain.WasteRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WasteRecordRepository extends MongoRepository<WasteRecord, String> {
    List<WasteRecord> findByUserUsername(String username);
}
