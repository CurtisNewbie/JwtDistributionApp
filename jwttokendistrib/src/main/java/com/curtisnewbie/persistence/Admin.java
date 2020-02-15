package com.curtisnewbie.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    private String name;
    @Column(nullable = false, unique = true)
    private String keyHash;
    @Column(nullable = false)
    private String salt;

    /**
     * @return the keyHash
     */
    public String getKeyHash() {
        return keyHash;
    }

    /**
     * @param keyHash the keyHash to set
     */
    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}