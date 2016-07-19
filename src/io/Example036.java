package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Example036 {

	public static void generateCharacter(OutputStream out) throws IOException{
		int firstPrintableCharacter = 33;
		int numberOfPrintableCharacter = 94;
		int numberOfCharactersPerLine = 72;
		int start = firstPrintableCharacter;
		
		byte[] line = new byte[numberOfCharactersPerLine + 2];
		
		for(int i = start; i < start + numberOfCharactersPerLine; i++){
			line[i - start] = (byte)((i - firstPrintableCharacter)
					% numberOfPrintableCharacter + firstPrintableCharacter);

			line[72] = (byte) '\r';
			line[73] = (byte) '\n';
			out.write(line);
		}
		
		
		
	}
	
	public static void main(String args[]) throws IOException{
		try {
			FileOutputStream fos = new FileOutputStream("Files\\01.txt");
			generateCharacter(fos);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Helloween");
	}
}