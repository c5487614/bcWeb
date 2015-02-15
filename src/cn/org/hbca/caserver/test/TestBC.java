package cn.org.hbca.caserver.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Random;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import cn.org.hbca.caserver.gen.CertGenerator;

public class TestBC {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws SignatureException 
	 * @throws NoSuchProviderException 
	 * @throws IllegalStateException 
	 * @throws InvalidKeyException 
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, NoSuchProviderException, SignatureException, KeyStoreException, CertificateException, IOException {
		// TODO Auto-generated method stub
//		char[] password = "11111111".toCharArray();
//		String pfxPath = "E:/caservertest.pfx";
//		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//		keyPairGenerator.initialize(1024);
//		KeyPair keyPair = keyPairGenerator.generateKeyPair();
//		PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
//        
//		Certificate trustCert =  createCert("CN=CA", "CN=CA", publicKey, privateKey);
//		Certificate[] outChain = { createCert("CN=Client", "CN=CA", publicKey, privateKey), trustCert };
//		
//		KeyStore outStore = KeyStore.getInstance("PKCS12");
//		outStore.load(null, password);
//		outStore.setKeyEntry("mykey", privateKey, "11111111".toCharArray(), outChain);
//		OutputStream outputStream = new FileOutputStream(pfxPath);
//		outStore.store(outputStream, password);
//		outputStream.flush();
//		outputStream.close();
		char[] password = "11111111".toCharArray();
		String pfxPath = "E:/caservertest.pfx";
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        KeyStore ks = CertGenerator.createKeyStore(publicKey,privateKey);
        CertGenerator.StorePFXFile(ks,pfxPath,password);
	}
	
	public static X509Certificate createCert(String DN,String issuer,PublicKey pubKey,PrivateKey priKey) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException{
		X509V3CertificateGenerator certGenerator = new X509V3CertificateGenerator();
		certGenerator.setSerialNumber(BigInteger.valueOf(Math.abs(new Random().nextLong())));
		certGenerator.setSubjectDN(new X500Principal(DN));
		certGenerator.setIssuerDN(new X500Principal(issuer));
		certGenerator.setNotBefore(Calendar.getInstance().getTime());
        certGenerator.setNotAfter(Calendar.getInstance().getTime());
        certGenerator.setPublicKey(pubKey);
        certGenerator.setSignatureAlgorithm("SHA1withRSA");
        certGenerator.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        certGenerator.addExtension("2.4.16.11.7.1", true, "00001".getBytes());
        X509Certificate certificate = (X509Certificate)certGenerator.generate(priKey, "BC");
        return certificate;
	}

}
