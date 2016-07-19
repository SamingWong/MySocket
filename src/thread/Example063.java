package thread;

import java.io.*;
import java.security.*;

import javax.xml.bind.*;

class DigestRunnable implements Runnable {

	private String filename;

	public DigestRunnable(String filename) {
		this.filename = filename;
	}

	@Override
	public void run() {
		try (FileInputStream in = new FileInputStream(filename)) {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			try (DigestInputStream din = new DigestInputStream(in, sha)) {
				while (din.read() != -1)
					;
			}

			byte[] digest = sha.digest();
			StringBuilder result = new StringBuilder(filename);
			result.append(":");
			result.append(DatatypeConverter.printHexBinary(digest));

			System.out.println(result);
		} catch (FileNotFoundException ex) {
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
	}

}

public class Example063 {
	public static void main(String[] args) {
		DigestRunnable dr = new DigestRunnable("Files\\01.txt");
		Thread t = new Thread(dr);
		t.start();
	}
}
