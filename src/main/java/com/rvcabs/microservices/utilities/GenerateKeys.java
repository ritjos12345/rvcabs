package com.rvcabs.microservices.utilities;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

@Component
public class GenerateKeys {

	private KeyPairGenerator keyGen;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private static final String PUBLICKEY_PREFIX    = "-----BEGIN PUBLIC KEY-----";
	private static final String PUBLICKEY_POSTFIX   = "-----END PUBLIC KEY-----";
	private static final String PRIVATEKEY_PREFIX   = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String PRIVATEKEY_POSTFIX  = "-----END RSA PRIVATE KEY-----";


	public GenerateKeys(){}
	private void generateSecureKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(1024);
	}

	private void createKeys() {
		KeyPair pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	private PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	private PublicKey getPublicKey() {
		return this.publicKey;
	}

/*	private void saveKeysInDb (String appId, String publicKey, String privateKey)
	{
		this.jdbcInMemory.insertData(appId, publicKey, privateKey);
	}*/


	/*public ApplicationKeysDto generatePubicAndPrivateKeys() {
		String publicKeyPEM = null;
		String privateKeyPEM;
		ApplicationKeysDto applicationKeysDto= new ApplicationKeysDto();
		System.out.println("main method of generator");
		try {
			this.generateSecureKeys();
			this.createKeys();

			// THIS IS PEM:
	        publicKeyPEM = PUBLICKEY_PREFIX + "\n" + DatatypeConverter.printBase64Binary(this.getPublicKey().getEncoded()).replaceAll("(.{64})", "$1\n") + "\n" + PUBLICKEY_POSTFIX;
	        privateKeyPEM = PRIVATEKEY_PREFIX + "\n" + DatatypeConverter.printBase64Binary(this.getPrivateKey().getEncoded()).replaceAll("(.{64})", "$1\n") + "\n" + PRIVATEKEY_POSTFIX;
	        
	        applicationKeysDto.setPublickey(publicKeyPEM);
	        applicationKeysDto.setPrivatekey(privateKeyPEM);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return applicationKeysDto;
	}*/
	public PrivateKey readPrivateKey(String privateKey)
			throws IOException, GeneralSecurityException {
		PrivateKey key;
		privateKey = privateKey.replace(
				"-----BEGIN RSA PRIVATE KEY-----\n", "")
				.replace("-----END RSA PRIVATE KEY-----", "");
		byte[] keyBytes = DatatypeConverter.parseBase64Binary(privateKey);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		key = kf.generatePrivate(spec);
		return key;
	}

	
	public PublicKey readPulicKey(String publicKey)
			throws IOException, GeneralSecurityException {
		PublicKey key;
		publicKey = publicKey.replace(
				"-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----\"", "");
		byte[] keyBytes = DatatypeConverter.parseBase64Binary(publicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		key = kf.generatePublic(spec);
		return key;
	}
}
