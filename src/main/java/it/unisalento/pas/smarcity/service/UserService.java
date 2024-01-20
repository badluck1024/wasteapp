package it.unisalento.pas.smarcity.service;

import it.unisalento.pas.smarcity.domain.User;
import it.unisalento.pas.smarcity.dto.UserDTO;
import it.unisalento.pas.smarcity.repositories.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static it.unisalento.pas.smarcity.configuration.SecurityConfig.passwordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private UserDTO convertToDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNome(user.getNome());
        userDTO.setCognome(user.getCognome());
        userDTO.setEmail(user.getEmail());
        userDTO.setEta(user.getEta());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRuolo(user.getRuolo());

        return userDTO;
    }

    private User convertToEntity (UserDTO userDTO) {

        User user = new User();
        user.setNome(userDTO.getNome());
        user.setCognome(userDTO.getCognome());
        user.setEmail(userDTO.getEmail());
        user.setEta(userDTO.getEta());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRuolo(userDTO.getRuolo());

        return user;
    }

    public UserDTO addUser(UserDTO userDTO) {

        User newUser = convertToEntity(userDTO);
        newUser.setPassword(passwordEncoder().encode(userDTO.getPassword()));
        newUser = userRepository.save(newUser);

        return convertToDTO(newUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        return convertToDTO(user);
    }

    public UserDTO updateUser(String id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        user.setNome(userDTO.getNome());
        user.setCognome(userDTO.getCognome());
        user.setEmail(userDTO.getEmail());
        user.setEta(userDTO.getEta());
        user.setUsername(userDTO.getCognome());
        user.setPassword(userDTO.getPassword());

        return convertToDTO(user);
    }

    public void deleteAllUser() {

        userRepository.deleteAll();
    }

    public UserDTO getUserByUsername(String username) {

        return convertToDTO(userRepository.findByUsername(username));
    }
}
