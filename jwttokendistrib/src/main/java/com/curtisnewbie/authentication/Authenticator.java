package com.curtisnewbie.authentication;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import com.curtisnewbie.persistence.Admin;
import com.curtisnewbie.persistence.AdminDao;
import com.curtisnewbie.util.RandomGenerator;

@Stateless
public class Authenticator {

    /** length of jti property in JWT */
    private final int JTI_LEN = 10;

    @Inject
    private RandomGenerator generator;

    @Inject
    private PrivateKeyStore keyStore;

    @Inject
    private AdminDao dao;

    @Inject
    @HashAlgorithm
    private String hashing_algo;

    @Inject
    @AsymmetricAlgorithm
    private String asyme_algo;

    /**
     * Decode BASIC encoded string to an array of username and password
     */
    public String[] decode(String basicStr) {
        String[] splits = basicStr.split("\\s+");
        if (splits.length < 2)
            throw new WebApplicationException("Not valid BASIC authentication string");

        String authInfo = splits[1];
        try {
            return new String(Base64.getDecoder().decode(authInfo)).split(":");
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("BASIC authentication string is not BASE64 encoded.");
        }
    }

    /**
     * Check whether the provided name and pw (SHA-256 hashed) matches the one in
     * the database.
     * 
     * @param name name
     * @param pw   password
     * @return {@code TRUE} if the provided credential matches one in database,
     *         {@code FALSE} if not.
     * @throws NoSuchAlgorithmException
     */
    public boolean isAuthenticated(String name, String pw) throws NoSuchAlgorithmException {
        Admin admin = dao.getAdmin(name);
        MessageDigest md = MessageDigest.getInstance(hashing_algo);
        String hashInStr = toHexStr(md.digest(pw.getBytes(StandardCharsets.UTF_8)));
        if (admin != null && admin.getKeyHash().equals(hashInStr))
            return true;
        else
            return false;
    }

    /**
     * Generate a JWT token for admin role that lasts for 1000 seconds. The given
     * name will be set to the "sub" subject property in the JWT.
     * 
     * @param name
     * @return JWT token for ADMIN role that expires after 1000 seconds
     * @throws Exception when private key is invalid, algorithm not exists
     */
    public String generateJWT(String name) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        PrivateKey prvKey = getPrivateKey(keyStore.getPrivateKeyStr());
        JwtBuilder builder = Jwts.builder().setHeaderParam("kid", "jwt.key").setHeaderParam("typ", "JWT")
                .claim("groups", new String[] { "admin" }).claim("upn", "bookstore")
                .claim("jti", generator.generateRandomString(JTI_LEN)).claim("sub", name).claim("iss", "bookstore")
                .claim("iat", Instant.now().getEpochSecond()).claim("exp", Instant.now().getEpochSecond() + 1000)
                .signWith(signatureAlgorithm, prvKey);
        return builder.compact();
    }

    /**
     * Convert privateKey in String to PrivateKey object
     * 
     * @param key privateKey in String
     * @return generated PrivateKey object
     */
    public PrivateKey getPrivateKey(String key) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(key);
        return KeyFactory.getInstance(asyme_algo).generatePrivate(new PKCS8EncodedKeySpec(bytes));
    }

    /**
     * Convert bytes[] to String in Hexdecimal format
     * 
     * @param bytes
     * @return a String in Hexdecimal format
     */
    private String toHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}