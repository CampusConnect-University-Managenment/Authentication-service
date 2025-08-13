package com.kce.dto;


public class LoginResponse {
    private String token;
    private String role;
   private String email;
   private String uniqueId;
    public LoginResponse(String token, String role,String email,String uniqueId) {
        this.token = token;
        this.role = role;
        this.email=email;
        this.uniqueId=uniqueId;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
    public String getEmail() { return email; }
    public String getUniqueId(){
        return uniqueId;
    }
}
