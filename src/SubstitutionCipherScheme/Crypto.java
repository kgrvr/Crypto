package substitutionCipherScheme;

public class Crypto {
	
	// main method
	public static void main(String[] a) {
		
		System.out.println("PlayFair Cipher:\n");
		
		PlayFairCipher cipher = new PlayFairCipher();
		System.out.println(cipher.encrypt("winteriscoming", "gameofthrones"));
		// zbfhrdsbimocfo
		System.out.println(cipher.decrypt("zbfhrdsbimocfo", "gameofthrones"));
		
		//////////

		System.out.println("\nCaesar Cipher:\n");
		
		CaesarCipher c = new CaesarCipher();
		System.out.println(c.encrypt("Kushal", 3));;
		System.out.println(c.decrypt("NXVKDO", 3));
		
		//////////
		
		System.out.println("\nHill Cipher:\n");
		
		HillCipher cipher2 = new HillCipher();
		System.out.println(cipher2.encrypt("Kushal", new int[][] {{0,11,15}, {7,0,1}, {4,19,0}}, false));
		System.out.println(cipher2.decrypt("wkejic", new double[][] {{0,11,15}, {7,0,1}, {4,19,0}}));
		
	}

}
