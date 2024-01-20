package it.unisalento.pas.smarcity.repositories;

import it.unisalento.pas.smarcity.domain.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BillRepository extends MongoRepository<Bill, String> {

    List<Bill> findByUsername(String username);

    List<Bill> findByIsPaidTrue();

    List<Bill> findByIsPaidFalse();
}
