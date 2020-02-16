package com.curtisnewbie.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class PrivateKeyStore {

    private String privateKey;

    @PostConstruct
    public void init() throws IOException {
        this.privateKey = loadPrivateKey();
    }

    public String getPrivateKeyStr() {
        return this.privateKey;
    }

    private String loadPrivateKey() throws IOException {
        var in = this.getClass().getClassLoader().getResourceAsStream("config.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            return reader.readLine().substring(20);
        }
    }
}