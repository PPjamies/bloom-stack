package org.bloom.authservice.config;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecurityConfigTest {

    @Test
    void ecKeyPairTest() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1")); // P-256
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        assertNotNull(keyPair);

        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

        System.out.println("Public Key (Base64): " +
                Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("Private Key (Base64): " +
                Base64.getEncoder().encodeToString(privateKey.getEncoded()));
    }
}
