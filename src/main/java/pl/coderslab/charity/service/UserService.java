package pl.coderslab.charity.service;

import pl.coderslab.charity.dto.RegistrationDTO;
import pl.coderslab.charity.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
    void saveUser(RegistrationDTO user);
}
