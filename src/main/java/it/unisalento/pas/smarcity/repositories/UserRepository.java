package it.unisalento.pas.smarcity.repositories;

import it.unisalento.pas.smarcity.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByCognome(String cognome);

    public User findByUsername(String username);
}
