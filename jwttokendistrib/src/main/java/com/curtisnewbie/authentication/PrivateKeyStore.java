package com.curtisnewbie.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class PrivateKeyStore {

    @Inject
    @ConfigProperty(name = "jwt_private_key")
    String privateKey;

    public String getPrivateKeyStr() {
        return this.privateKey;
    }
}