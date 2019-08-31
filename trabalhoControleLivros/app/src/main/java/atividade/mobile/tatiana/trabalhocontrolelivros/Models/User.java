package atividade.mobile.tatiana.trabalhocontrolelivros.Models;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String standardIcon; // drawable name

    // Construtor para users tirados do bd
    public User(int id, String name, String email, String username, String password, String standardIcon) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.standardIcon = standardIcon;
    }

    // construtor para novos usuários
    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStandardIcon(String standardIcon) {
        this.standardIcon = standardIcon;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getStandardIcon() {
        return this.standardIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, username, password);
    }
}
