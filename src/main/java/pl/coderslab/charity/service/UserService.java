package pl.coderslab.charity.service;

import pl.coderslab.charity.dto.RegistrationDTO;
import pl.coderslab.charity.model.User;

public interface UserService {

    User findByEmail(String email);
    void saveUser(RegistrationDTO user);
}
