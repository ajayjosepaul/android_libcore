/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tests.targets.security;

import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetNew;
import dalvik.annotation.TestTargets;

import junit.framework.TestCase;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
public abstract class KeyFactoryTest<PublicKeySpec extends KeySpec, PrivateKeySpec extends KeySpec>
        extends TestCase {

    private final String algorithmName;
    private final Class<PublicKeySpec> publicKeySpecClass;
    private final Class<PrivateKeySpec> privateKeySpecClass;

    private KeyFactory factory;
    private final TestHelper<KeyPair> helper;


    public KeyFactoryTest(String algorithmName, TestHelper<KeyPair> helper,
            Class<PublicKeySpec> publicKeySpecClass,
            Class<PrivateKeySpec> privateKeySpecClass) {
        this.algorithmName = algorithmName;
        this.helper = helper;
        this.publicKeySpecClass = publicKeySpecClass;
        this.privateKeySpecClass = privateKeySpecClass;
    }

    protected void setUp() throws Exception {
        super.setUp();
        factory = getFactory();
    }

    private KeyFactory getFactory() {
        try {
            return KeyFactory.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
        return null;
    }

    @TestTargets({
        @TestTargetNew(
            level = TestLevel.ADDITIONAL,
            method = "getKeySpec",
            args = {Key.class, Class.class}
        ),
        @TestTargetNew(
            level = TestLevel.ADDITIONAL,
            method = "generatePrivate",
            args = {KeySpec.class}
        ),
        @TestTargetNew(
            level = TestLevel.ADDITIONAL,
            method = "generatePublic",
            args = {KeySpec.class}
        ),
        @TestTargetNew(
            level=TestLevel.COMPLETE,
            method="method",
            args={}
        )
    })
    public void testKeyFactory() {
        PrivateKeySpec privateKeySpec = null;
        try {
            privateKeySpec = factory.getKeySpec(DefaultKeys.getPrivateKey(algorithmName),
                    privateKeySpecClass);
        } catch (InvalidKeySpecException e) {
            fail(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }

        PrivateKey privateKey = null;
        try {
            privateKey = factory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException e) {
            fail(e.getMessage());
        }

        PublicKeySpec publicKeySpec = null;
        try {
            publicKeySpec = factory.getKeySpec(DefaultKeys.getPublicKey(algorithmName),
                    publicKeySpecClass);
        } catch (InvalidKeySpecException e) {
            fail(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }

        PublicKey publicKey = null;
        try {
            publicKey = factory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            fail(e.getMessage());
        }
        
        KeyPair keyPair = new KeyPair(publicKey, privateKey);
        
        helper.test(keyPair);
    }
}
