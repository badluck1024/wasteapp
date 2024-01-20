package it.unisalento.pas.smarcity.restcontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.unisalento.pas.smarcity.domain.User;
import it.unisalento.pas.smarcity.dto.*;
import it.unisalento.pas.smarcity.exceptions.UserNotFoundException;
import it.unisalento.pas.smarcity.repositories.UserRepository;
import it.unisalento.pas.smarcity.repositories.WasteRecordRepository;
import it.unisalento.pas.smarcity.service.MessageSender;
import it.unisalento.pas.smarcity.service.UserService;
import it.unisalento.pas.smarcity.service.WasteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.unisalento.pas.smarcity.configuration.SecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private WasteRecordService wasteRecordService;

    @Autowired
    private it.unisalento.pas.smarcity.security.JwtUtilities jwtUtilities;

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Secured("ROLE_OPERATORE_AGENZIA")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO updateUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUser();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/waste-trigger")
    public ResponseEntity<String> throwWaste(@RequestBody WasteDTO request,
                                             @RequestHeader("Authorization") String authToken) {

        String username = jwtUtilities.extractUsername(authToken.replace("Bearer ", ""));

        messageSender.thrownWasteTrigger(username, request.getBinId(), request.getWasteType(), request.getWaste());

        System.out.println("Username: " + username);
        System.out.println("bin id: " + request.getBinId());
        System.out.println("wasteType: " + request.getWasteType());
        System.out.println("waste: " + request.getWaste());

        WasteRecordDTO newRecord = new WasteRecordDTO();
        newRecord.setUserUsername(username);
        newRecord.setWaste(request.getWaste());
        newRecord.setWasteTypeDeclared(request.getWasteType());
        newRecord.setWasteTypeEffective(request.getBinType());
        String formattedDateTime = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        newRecord.setTimeStamp(formattedDateTime);
        newRecord.setBinLocation(request.getBinLocation());
        wasteRecordService.addWasteRecord(newRecord);

        return ResponseEntity.ok("Waste disposal request sent");
    }

    @GetMapping("/username")
    public ResponseEntity<UserDTO> findUserByUsername(@RequestParam String username) {

        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value="/find", method = RequestMethod.GET)
    public List<UserDTO> findByCognome(@RequestParam String cognome) {

        List<User> result = userRepository.findByCognome(cognome);
        List<UserDTO> utenti = new ArrayList<>();

        for(User user: result){
            UserDTO userDTO = new UserDTO();
            //userDTO.setId(user.getId());
            userDTO.setNome(user.getNome());
            userDTO.setCognome(user.getCognome());
            userDTO.setEmail(user.getEmail());
            userDTO.setEta(user.getEta());
            userDTO.setUsername(user.getUsername());

            utenti.add(userDTO);
        }

        return utenti;
    }

    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO) {

        System.out.println("Username: " + loginDTO.getUsername());
        System.out.println("Password: " + loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        User user = userRepository.findByUsername(authentication.getName());

        if(user == null) {
            throw new UsernameNotFoundException(loginDTO.getUsername());
        }

        //Serve per mantenere il token
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtUtilities.generateToken(user.getUsername());
        String userId = user.getId();
        System.out.println("Token generated: " + jwt);

        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt, userId));
    }
}
