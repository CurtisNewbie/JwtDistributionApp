package com.curtisnewbie.authentication;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
@Startup
public class PrivateKeyStore {

    @Inject
    @ConfigProperty(name = "jwt_private_key")
    private String privateKey;

    public String getPrivateKeyStr() {
        return this.privateKey;
    }
}