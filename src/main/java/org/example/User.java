package org.example;

public class User {
    private String name;
    private String password;
    private String email;

    public User(String name, String password, String email){ // конструктор со всеми параметрами
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User () { // конструктор без параметров
    }

    // геттер для поля name
    public String getName() {
        return name;
    }
    // сеттер для поля name
    public void setName(String name) {
        this.name = name;
    }

    // геттер для поля name
    public String getPassword() {
        return password;
    }
    // сеттер для поля name
    public void setPassword(String password) {
        this.password = password;
    }

    // геттер для поля name
    public String getEmail() {
        return email;
    }
    // сеттер для поля name
    public void setEmail(String email) {
        this.email = email;
    }

}
