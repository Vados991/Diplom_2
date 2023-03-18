package org.example;

public class UserClient {
    private UserAuthData userAuthData;
    private String accessToken;
    private boolean success;

    public UserAuthData getUser() {
        return userAuthData;
    }

    public void setUser(UserAuthData userAuthData) {
        this.userAuthData = userAuthData;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}