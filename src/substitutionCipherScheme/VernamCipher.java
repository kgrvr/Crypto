package substitutionCipherScheme;

import java.util.List;
import java.util.ArrayList;

public class VernamCipher {

	final static String ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
	
	private static List<Integer> stringToArray(String text) {
		List<Integer> array = new ArrayList<Integer>();
		for(int i = 0 ; i < text.length() ; i++) {
			array.add(ALPHABETS.indexOf(text.charAt(i)));
		}
		return array;
	}
	
	private static String arrayToString(int[] arr) {
		String text = "";
		for(int i = 0 ; i < arr.length ; i++) {
			text += ALPHABETS.charAt(arr[i]);
		}
		return text;
	}
	
	public String encrypt(String plainText, String key) {
		List<Integer> plainTextArray = stringToArray(plainText);
		List<Integer> keyTextArray = stringToArray(key);
		int[] arr = new int[plainText.length()];
		for(int i = 0 ; i < plainTextArray.size() ;) {
			for(int j = 0 ; j < keyTextArray.size() ; j++, i++) {
				if(i == plainTextArray.size())
					break;
				arr[i] = (plainTextArray.get(i) + keyTextArray.get(j))%26;
			}
		}
		return arrayToString(arr);
	}
	
	public String decrypt(String cipherText, String key) {
		List<Integer> cipherTextArray = stringToArray(cipherText);
		List<Integer> keyTextArray = stringToArray(key);
		int[] arr = new int[cipherText.length()]; 
		for(int i = 0 ; i < cipherTextArray.size() ;) {
			for(int j = 0 ; j < keyTextArray.size() ; j++, i++) {
				if(i == cipherTextArray.size())
					break;
				arr[i] = mod((cipherTextArray.get(i) - keyTextArray.get(j)),26);
			}
		}
		return arrayToString(arr);
	}
	
	private static int mod(int a, int b) {
		int i = a % b;
		if (i < 0)
			i += b;
		return i;
	}

}
