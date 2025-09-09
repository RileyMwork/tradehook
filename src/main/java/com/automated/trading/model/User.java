package com.automated.trading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String email;

    private String password;

    private String webhooktradingApiKey;

    private String alpacaApiKey;

    private String alpacaSecretKey;

    public User(){

    }

    public User(String email){
        this.email = email;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(Integer id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String webhooktradingApiKey, String alpacaApiKey, String alpacaSecretKey){
        this.email = email;
        this.password = password;
        this.webhooktradingApiKey = webhooktradingApiKey;
        this.alpacaApiKey = alpacaApiKey;
        this.alpacaSecretKey = alpacaSecretKey;
    }

    public User(String email, String webhooktradingApiKey, String alpacaApiKey, String alpacaSecretKey){
        this.email = email;
        this.webhooktradingApiKey = webhooktradingApiKey;
        this.alpacaApiKey = alpacaApiKey;
        this.alpacaSecretKey = alpacaSecretKey;
    }

    public User(Integer id, String email, String password, String webhooktradingApiKey, String alpacaApiKey, String alpacaSecretKey) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.webhooktradingApiKey = webhooktradingApiKey;
        this.alpacaApiKey = alpacaApiKey;
        this.alpacaSecretKey = alpacaSecretKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getWebhookTradingApiKey() {
        return webhooktradingApiKey;
    }

    public void setWebhookTradingApiKey(String webhooktradingApiKey) {
        this.webhooktradingApiKey = webhooktradingApiKey;
    }

    public String getAlpacaApiKey() {
        return alpacaApiKey;
    }

    public void setAlpacaApiKey(String alpacaApiKey) {
        this.alpacaApiKey = alpacaApiKey;
    }

    public String getAlpacaSecretKey() {
        return alpacaSecretKey;
    }

    public void setAlpacaSecretKey(String alpacaSecretKey) {
        this.alpacaSecretKey = alpacaSecretKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  
        if (obj == null) return false; 
        if (getClass() != obj.getClass()) return false; 

        User other = (User) obj;

        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;

        if (email == null) {
            if (other.email != null) return false;
        } else if (!email.equals(other.email)) return false;

        if (password == null) {
            if (other.password != null) return false;
        } else if (!password.equals(other.password)) return false;

        if (webhooktradingApiKey == null) {
            if (other.webhooktradingApiKey != null) return false;
        } else if (!webhooktradingApiKey.equals(other.webhooktradingApiKey)) return false;

        if (alpacaApiKey == null) {
            if (other.alpacaApiKey != null) return false;
        } else if (!alpacaApiKey.equals(other.alpacaApiKey)) return false;

        if (alpacaSecretKey == null) {
            if (other.alpacaSecretKey != null) return false;
        } else if (!alpacaSecretKey.equals(other.alpacaSecretKey)) return false;

        return true; 
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", webhooktradingApiKey='" + webhooktradingApiKey + '\'' +
                ", alpacaApiKey='" + alpacaApiKey + '\'' +
                ", alpacaSecretKey='" + alpacaSecretKey + '\'' +
                '}';
    }
    
}