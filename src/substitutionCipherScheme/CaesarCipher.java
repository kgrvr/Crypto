package substitutionCipherScheme;

public class CaesarCipher {
    
    private static int shiftCounter = 0;
    private static final String ALPHABETS = "abcdefghijklmnopqrstuvwxyz";

    public CaesarCipher() {}
    
    private static void checkText(String text) {
        if(text == null) {
			throw new NullPointerException();
		}
    }
    
    private static void checkAndSetShiftCounter(int counter) {
        if(counter < 0) {
			shiftCounter = 0;
		} else {
			shiftCounter = counter;
		}
    }
    
    public String encrypt(String plainText, int shiftCounter) {
        checkText(plainText);
        checkAndSetShiftCounter(shiftCounter);
        return encrypt0(plainText.toLowerCase());
    }
    
    private static String encrypt0(String plainText) {
        String cipherText = "";
        for(int i = 0 ; i < plainText.length() ; i++) {
            int charPos = ALPHABETS.indexOf(plainText.charAt(i));
            char tempChar = ALPHABETS.charAt((shiftCounter + charPos) % 26);
            cipherText += tempChar;
        }
        return cipherText.toUpperCase();
    }
    
    public String decrypt(String cipherText, int shiftCounter) {
        checkText(cipherText);
        checkAndSetShiftCounter(shiftCounter);
        return decrypt0(cipherText.toLowerCase());
    }
    
    private static String decrypt0(String cipherText) {
        String plainText = "";
        for(int i = 0 ; i < cipherText.length() ; i++) {
            int charPos = ALPHABETS.indexOf(cipherText.charAt(i));
            int keyVal = (charPos - shiftCounter) % 26;
            if(keyVal < 0) {
				keyVal = keyVal + 26;
			}
            char tempChar = ALPHABETS.charAt(keyVal);
            plainText += tempChar;
        }
        return plainText;
    }

}
