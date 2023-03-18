package org.example;
import org.apache.commons.lang3.RandomStringUtils;

public class UserAuthData {
    private String name;
    private String password;
    private String email;
    public static UserAuthData usersRandomCreate() {
        UserAuthData userAuthData = new UserAuthData();
        String randomName = RandomStringUtils.randomAlphabetic(7);
        userAuthData.setEmail(randomName.toLowerCase() + "@yandex.ru");
        userAuthData.setName(randomName.toLowerCase());
        userAuthData.setPassword("1q2w3e4r5");
        return userAuthData;
    }

    public UserAuthData(String email, String password, String name) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserAuthData() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}