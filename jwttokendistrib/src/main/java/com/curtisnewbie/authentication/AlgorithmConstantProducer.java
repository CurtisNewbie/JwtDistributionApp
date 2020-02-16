package com.curtisnewbie.authentication;

import javax.enterprise.inject.Produces;

public class AlgorithmConstantProducer {

    @Produces
    @HashAlgorithm
    public final String HASH_ALGORITHM = "SHA-256";

    @Produces
    @AsymmetricAlgorithm
    public final String ASYMMETRIC_ALGORITHM = "RSA";
}