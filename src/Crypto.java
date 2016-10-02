

import substitutionCipherScheme.CaesarCipher;
import substitutionCipherScheme.VernamCipher;
import transpositionCipherScheme.HillCipher;
import transpositionCipherScheme.PlayFairCipher;

public class Crypto {
	
	// main method
	public static void main(String[] a) {
		
		System.out.println("PlayFair Cipher:\n");
		
		PlayFairCipher cipher = new PlayFairCipher();
		System.out.println(cipher.encrypt("winteriscoming", "gameofthrones"));
		System.out.println(cipher.decrypt("zbfhrdsbimocfo", "gameofthrones"));
		
		//////////

		System.out.println("\nCaesar Cipher:\n");
		
		CaesarCipher c = new CaesarCipher();
		System.out.println(c.encrypt("Kushal", 3));;
		System.out.println(c.decrypt("NXVKDO", 3));
		
		//////////
		
		System.out.println("\nHill Cipher:\n");
		
		HillCipher cipher2 = new HillCipher();
//		System.out.println(cipher2.encrypt("kushal", new int[][] {{6,24,1}, {13,16,10}, {20,17,15}}, false));	
//		System.out.println(cipher2.decrypt("ljacqzkagcykqyz", new double[][] {{3,11,11}, {5,2,1}, {3,21,10}}));
//		System.out.println(cipher2.decrypt("wuqvpo", new double[][] {{4,6,1},{7,7,1},{4,5,9}}));
		try {
			System.out.println(cipher2.encrypt("wuqvpo", new int[][] {{4,6,1},{7,7,1},{4,5,9}}, true));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		///////////

		System.out.println("\nVernam Cipher:\n");
	
		VernamCipher vcipher = new VernamCipher();
		System.out.println(vcipher.encrypt("winteriscoming", "gameofthrones"));
		System.out.println(vcipher.decrypt("cizxswbztczmfm", "gameofthrones"));
	}

}
