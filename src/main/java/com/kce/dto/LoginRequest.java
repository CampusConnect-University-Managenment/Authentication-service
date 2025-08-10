package com.kce.dto;

public class LoginRequest {

        private String username;
        private String password;
//private String uniqueId;
        public LoginRequest() {}

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
//        public String getUniqueId(){
//            return uniqueId;
//        }
//        public void setUniqueId(String uniqueId){
//            this.uniqueId=uniqueId;
//        }
}
