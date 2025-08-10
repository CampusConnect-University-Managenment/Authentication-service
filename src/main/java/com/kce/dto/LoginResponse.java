package com.kce.dto;


public class LoginResponse {
    private String token;
    private String role;
   private String email;
   private String unique_id;
    public LoginResponse(String token, String role,String email,String unique_id) {
        this.token = token;
        this.role = role;
        this.email=email;
        this.unique_id=unique_id;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
    public String getEmail() { return email; }
    public String getUnique_id(){
        return unique_id;
    }
}
