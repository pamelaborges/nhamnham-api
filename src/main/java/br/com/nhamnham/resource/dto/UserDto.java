package br.com.nhamnham.resource.dto;

import br.com.nhamnham.model.User;

public class UserDto {

    private String name;
    private String username;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User toDomain(UserDto dto){
        User domain = new User();
        domain.setUsername(dto.getUsername());
        domain.setPassword(dto.getPassword());
        domain.setEmail(dto.getEmail());
        domain.setName(dto.getName());
        return domain;
    }

    public static UserDto toDto(User domain){
        UserDto dto = new UserDto();
        dto.setUsername(domain.getUsername());
        dto.setEmail(domain.getEmail());
        dto.setName(domain.getName());
        return dto;
    }
}
