package SubstitutionCipherScheme;

import java.util.Iterator;
import java.util.LinkedHashSet;

//SizeLimitException is used to check the key length. Thrown if the key has invalid length.
class SizeLimitException extends Exception {
	public SizeLimitException(String exception) {
		super(exception);
	}
}

public class PlayFairCipher {

	
	public PlayFairCipher() {}
	
	//this method checks size of key.
	private static void checkKeyLength(String key) throws SizeLimitException {
		if(key.length() < 0 || key.length() == 0 || key.length() > 26) 
			// if key length is less than or equal to 0 OR more than 25..then exception is thrown
			throw new SizeLimitException("Invalid length of key:" + key.length() + ". Should be greater than 0 and less than 26.");
	}
	
	//this method is used to prepare the key matrix(5x5). It takes character array of the key and converts it to 5x5 matrix.  
	private static char[][] prepareKeyMatrix(char[] key) {
		//LinkedHashSet is used because it has the property of storing unique values and maintaining their entry positions.
	    final char[] ALPHABETS = "abcdefghiklmnopqrstuvwxyz".toCharArray();  
		LinkedHashSet<Character> set = new LinkedHashSet<>();
		//loop for adding key array to the set firstly
		for(char c : key) {
			if(c == 'j') //following the property of playfair cipher to replace each 'j' with 'i'
				set.add('i');
			else
				set.add(c);
		}
		//loop for filling remaining blocks with alphabets array
		for(int i = 0 ; i < 25 ; i++) {
			set.add(ALPHABETS[i]);
		}
		//iterator used to iterate thru each element of set and adding it to the charArr[5][5]
		Iterator<Character> itr = set.iterator();
		char[][] charArr = new char[5][5];
		for(int i = 0; i < 5 ; i++)
			for(int j = 0 ; j < 5 ; j++)
				charArr[i][j] = itr.next();
		return charArr;
	}
	
	//this method is used to prepare the key matrix(nx2). It takes character array of the key and converts it to nx2 matrix.
	private static char[][] preparePlainTextMatrix(char[] plainText) {
		//if length is odd, then append 'z' character to plainText; 
		if(plainText.length%2 != 0)
			plainText = (String.valueOf(plainText) + "z").toCharArray();
		char[][] plainTextMatrix = new char[plainText.length/2][2];
		for(int i = 0, k = 0; i < plainText.length/2 ; i++) {
			for(int j = 0 ; j < 2 ; j++) {
				if(plainText[k] == 'j') //following the property of playfair cipher to replace each 'j' with 'i'
					plainTextMatrix[i][j] = 'i';
				else
					plainTextMatrix[i][j] = plainText[k];
				k++;
			}
		}
		return plainTextMatrix;
	}
	
	//public encrypt method which firstly checks length of key and then calls encrypt0 
	public void encrypt(String plainText, String key) {
		try {
			checkKeyLength(key);
		} catch (SizeLimitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		encrypt0(plainText, key);
	}
	
	//main encryption starts here
	private static void encrypt0(String plainText, String key) {
		String cipherText = ""; //cipherText in which cipher will be stored 
		char[][] preparedKeyMatrix = prepareKeyMatrix(key.toLowerCase().toCharArray()); // prepared key matrix[5x5] 
		char[][] preparedPlainTextMatrix = preparePlainTextMatrix(plainText.toLowerCase().toCharArray()); // prepared key matrix[nx2] 
		
		//main encryption loop starts
		for(int i = 0 ; i < preparedPlainTextMatrix.length ; i++) {
			for(int j = 0 ; j < 2 ;) {
				//getting the of nx2 matrix's element in key matrix with the help of getIndexOfChar method 
				int[] index1 = getIndexOfChar(preparedKeyMatrix, preparedPlainTextMatrix[i][j]); //index of 1st char 
				int[] index2 = getIndexOfChar(preparedKeyMatrix, preparedPlainTextMatrix[i][j+1]); //index of 2nd char

				//for debugging purpose, commented
//				System.out.println(i + " " + j);
//
//				System.out.println(cipherText);
//				for(int x : index1)
//					System.out.print(x);
//				System.out.println(preparedPlainTextMatrix[i][j]);
//				for(int x : index2)
//					System.out.print(x);
//				System.out.println(preparedPlainTextMatrix[i][j+1]);
				
				//substitution starts here:
				//CASE 1 : If both the letters are in the same column, take the letter below each one (going back to the top if at the bottom)
				if(index1[1] == index2[1] && index1[0] != index2[0]) {
					cipherText = cipherText + preparedKeyMatrix[(index1[0]+1)%5][index1[1]] + preparedKeyMatrix[(index2[0]+1)%5][index2[1]];
					break;
				}

				//CASE 1 : If both letters are in the same row, take the letter to the right of each one (going back to the left if at the farthest right)
				if(index1[0] == index2[0] && index1[1] != index2[1]) {
					cipherText = cipherText + preparedKeyMatrix[index1[0]][(index1[1]+1)%5] + preparedKeyMatrix[index2[0]][(index2[1]+1)%5];
					break;
				}

				//CASE 3 : If neither of the preceding two rules are true, form a rectangle with the two letters and take the letters on the horizontal opposite corner of the rectangle.
				if(index1[0] != index2[0] && index1[1] != index2[1]) {
					if(index1[1] < index2[1]) {
						int d = index2[1] - index1[1];
						cipherText = cipherText + preparedKeyMatrix[index1[0]][(index1[1]+d)%5] + preparedKeyMatrix[index2[0]][(index2[1]-d)%5];
					}
					else {
						int d = index1[1] - index2[1];
						cipherText = cipherText + preparedKeyMatrix[index1[0]][(index1[1]-d)%5] + preparedKeyMatrix[index2[0]][(index2[1]+d)%5];
					}
					break;
				}
			}
		}

		System.out.println(cipherText); //printing final cipher text
		
		//printing key matrix[5x5]
		for(int i = 0; i < 5 ; i++) {
			System.out.println();
			for(int j = 0 ; j < 5 ; j++) {
				System.out.print(preparedKeyMatrix[i][j]);
			}
		}
		
		System.out.println();
		
		//printing plain text matrix[nx2]
		for(int i = 0; i < 7 ; i++) {
			System.out.println();
			for(int j = 0 ; j < 2 ; j++) {
				System.out.print(preparedPlainTextMatrix[i][j]);
			}
		}
	} 
	
	//will return the index of the character provided in the 2D matrix. returns 1D matrix with 2 length -> index[0] is ROW and index[1] is Column  
	//1st parameter is the matrix itself
	//2nd parameter is the character which index needs to be found
	private static int[] getIndexOfChar(char[][] matrix, char c) { //
		int[] index = new int[2];
		for(int i = 0 ; i < 5 ; i++) {
			for(int j = 0 ; j < 5 ; j++) {
				if(matrix[i][j] == c) { //when character found  
					index[0] = i; //the row value
					index[1] = j; //the column value
					break;
				}
			}
		}
		return index;
	}
	
	//main method
	public static void main(String[] a) {
		PlayFairCipher cipher = new PlayFairCipher();
		cipher.encrypt("winteriscoming", "gameofthrones");
		//zbfhrdsbimocfo
	}

}
