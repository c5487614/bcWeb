package cn.org.hbca.caserver.gen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
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
import java.util.Date;
import java.util.Random;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import cn.org.hbca.caserver.entity.Cert4Gen;

public class CertGenerator {
	
	public static X509Certificate createCert(Cert4Gen cert,PublicKey pubKey,PrivateKey priKey) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException{
		X509V3CertificateGenerator certGenerator = new X509V3CertificateGenerator();
		certGenerator.setSerialNumber(BigInteger.valueOf(Math.abs(new Random().nextLong())));
		certGenerator.setSubjectDN(new X500Principal(cert.getDN()));
		certGenerator.setIssuerDN(new X500Principal(cert.getIssuerDN()));
		certGenerator.setNotBefore(cert.getBeginDate());
        certGenerator.setNotAfter(cert.getEndDate());
        certGenerator.setPublicKey(pubKey);
        certGenerator.setSignatureAlgorithm("SHA1withRSA");
        certGenerator.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        certGenerator.addExtension("2.4.16.11.7.1", true, "00001".getBytes());
        X509Certificate certificate = (X509Certificate)certGenerator.generate(priKey, "BC");
        return certificate;
	}
	public static KeyStore createKeyStore(PublicKey pubKey,PrivateKey priKey) throws KeyStoreException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, CertificateException, IOException{
		KeyStore ks = KeyStore.getInstance("PKCS12");
		char[] password = "11111111".toCharArray();
		ks.load(null, password);
		Cert4Gen root = getRootCert();
		Cert4Gen cert = getRootCert();
		cert.setCN("Client");
		cert.setIssuerDN(root.getDN());
		Certificate[] outChain = { createCert(cert, pubKey, priKey),createCert(root, pubKey, priKey) };
		ks.setKeyEntry("", priKey, password,outChain);
		return ks;
	}
	public static boolean StorePFXFile(KeyStore ks,String pfxPath,char[] password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		OutputStream outputStream = new FileOutputStream(pfxPath);
		ks.store(outputStream, password);
		outputStream.flush();
		outputStream.close();
		return true;
	}
	
	private static Cert4Gen getRootCert(){
		Cert4Gen root = new Cert4Gen();
		root.setC("CN");
		root.setCN("ROOT");
		Calendar rightNow = Calendar.getInstance();
		Date now = new Date();
		rightNow.setTime(now);
		root.setBeginDate(rightNow.getTime());
		rightNow.add(Calendar.YEAR, 1);
		root.setEndDate(rightNow.getTime());
		root.setL("Wuhan");
		root.setO("TEST-O");
		root.setOU("TEST-OU");
		root.setS("Hubei");
		root.setIssuerDN(root.getDN());
		return root;
	}
}
