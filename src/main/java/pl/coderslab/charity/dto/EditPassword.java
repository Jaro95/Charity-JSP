package pl.coderslab.charity.dto;


import lombok.Data;

@Data
public class EditPassword {

    private String password;
    private String repeatPassword;
}
