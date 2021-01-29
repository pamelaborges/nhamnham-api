package br.com.nhamnham.resource.enumeration;

import java.util.Arrays;

public enum Roles {

    ADMIN("ADMIN", "Administrador"),
    ENTERPRISE_ADMIN("ENTERPRISE_ADMIN", "Administrador da empresa"),
    ENTERPRISE_OPERATION("ENTERPRISE_OPERATION", "Operador da empresa");

    Roles(String name, String description) {
        this.description = description;
        this.name = name;
    }

    private String description;
    private String name;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Roles getByName(String name) {
        return Arrays.stream(Roles.values()).
                filter(role -> role.getName().equals(name))
                .findAny().get();
    }
}
