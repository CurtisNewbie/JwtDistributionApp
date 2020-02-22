package com.curtisnewbie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.Base64.Encoder;

public class App {

    static final String ALGORITHM = "RSA";
    static final int KEY_SIZE = 2048;
    static final Scanner sc = new Scanner(System.in);
    static final String DIR_FILE = "keys";
    static final String PRIKEY_FILE = DIR_FILE + "/" + "private.pem";
    static final String PUBKEY_FILE = DIR_FILE + "/" + "public.pem";

    public static void main(String[] args) {
        printIntro();
        generateKeyPair();
    }

    static void printIntro() {
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("    RSA Key Pair Generator");
        System.out.println();
        System.out.println("    Key Size:2048");
        System.out.println();
        System.out.println("    By CurtisNewbie (Yongjie Zhuang)");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }

    /**
     * Generate brand new key pair
     */
    static void generateKeyPair() {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(ALGORITHM);
            kpg.initialize(KEY_SIZE);
            KeyPair keyPair = kpg.generateKeyPair();
            Key priv = keyPair.getPrivate();
            Key pub = keyPair.getPublic();
            Encoder base64 = Base64.getEncoder();
            String priStr = base64.encodeToString(priv.getEncoded());
            String pubStr = base64.encodeToString(pub.getEncoded());
            displayKeys(priStr, pubStr);
            writeKeysToFile(priStr, pubStr);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Internal Error - " + e.getClass());
        }
    }

    static void displayKeys(String priKey, String pubKey) {
        System.out.printf("[Private Key]:\n%s\n\n[Public Key]:\n%s\n", priKey, pubKey);
    }

    static void writeKeysToFile(String priKey, String pubKey) {
        File file = new File(DIR_FILE);
        file.mkdir();

        File priKeyFile = new File(PRIKEY_FILE);
        File pubKeyFile = new File(PUBKEY_FILE);
        try {
            if (priKeyFile.createNewFile() && pubKeyFile.createNewFile()) {
                try (FileWriter priKeyWriter = new FileWriter(priKeyFile);
                        FileWriter pubKeyWriter = new FileWriter(pubKeyFile);) {
                    priKeyWriter.write(priKey);
                    pubKeyWriter.write(pubKey);
                    System.out.printf("\n[Private Key is written to file]:\n%s\n", priKeyFile.getAbsolutePath());
                    System.out.printf("\n[Public Key is written to file]:\n%s\n", pubKeyFile.getAbsolutePath());
                }
            } else {
                displayIOError();
            }
        } catch (IOException e) {
            displayIOError();
        }
    }

    static void displayIOError() {
        System.err.println(
                "\nFailed to write public/private keys to files. Files with same name may have been created.\n");
    }
}
