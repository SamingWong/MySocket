package thread;

import java.io.*;
import java.security.*;

import javax.xml.bind.*;

class InstanceCallbackDigest implements Runnable {

	private String filename;
	private InstanceCallbackDigestUserInterface callback;

	public InstanceCallbackDigest(String filename, InstanceCallbackDigestUserInterface callback) {
		this.filename = filename;
		this.callback = callback;
	}

	@Override
	public void run() {
		try (FileInputStream in = new FileInputStream(filename)) {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			try (DigestInputStream din = new DigestInputStream(in, sha)) {
				while (din.read() != -1);
			}

			byte[] digest = sha.digest();
			callback.receiveDigest(digest);
		} catch (FileNotFoundException ex) {
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
	}

}

public class InstanceCallbackDigestUserInterface{
	private String filename;
	private byte[] digest;
	
	public InstanceCallbackDigestUserInterface(String filename){
		this.filename = filename;
	}
	
	public void calculateDigest(){
		InstanceCallbackDigest cb = new InstanceCallbackDigest(filename, this);
		Thread t = new Thread(cb);
		t.start();
	}
	
	public void receiveDigest(byte[] digest){
		this.digest = digest;
		System.out.println(this);
	}
	
	public String toString(){
		String result = filename + ":";
		if(digest != null){
			result += DatatypeConverter.printHexBinary(digest);
		}else{
			result += "digest is not available";
		}
		return result;
	}
	public static void main(String[] args) {
		String[] str = new String[4];
		str[0] = "F:\\eclipse工作空间\\MyJava\\src\\MyJavaSock\\Example10_01.java";
		str[1] = "F:\\eclipse工作空间\\MyJava\\src\\MyJavaSock\\Example10_02.java";
		str[2] = "F:\\eclipse工作空间\\MyJava\\src\\MyJavaSock\\Example10_03.java";
		str[3] = "F:\\eclipse工作空间\\MyJava\\src\\MyJavaSock\\Example10_04.java";

		for(String filename : str){
			InstanceCallbackDigestUserInterface cd = new InstanceCallbackDigestUserInterface(filename);
			cd.calculateDigest();
		}
	}
}

