package transpositionCipherScheme;

import Jama.Matrix;

class MatrixLengthNotEqualException extends Exception {
	private static final long serialVersionUID = 1L;

	public MatrixLengthNotEqualException(String exception) {
		super(exception);
	}
}

class MatrixModularInverseIsZeroException extends Exception {
	private static final long serialVersionUID = 1L;

	public MatrixModularInverseIsZeroException(String exception) {
		super(exception);
	}
}

public class HillCipher {

	private static int[][] keyMatrix;

	public int[][] getKeyMatrix() {
		return keyMatrix;
	}

	public void setKeyMatrix(int[][] keyMatrix) {
		HillCipher.keyMatrix = keyMatrix;
	}

	private static int[][] generateRandomMatrix(int size) {

		int[][] arr = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				arr[i][j] = (int) (Math.random() * 10);
			}
		}

		return arr;
	}

	private static void checkMatrixLengthForMultiplication(int[][] arr1, int[] arr2)
			throws MatrixLengthNotEqualException {
		if (arr1.length != arr1[0].length)
			throw new MatrixLengthNotEqualException("**Exception**\nKey matrix should be NxN.");
		else if (arr1.length != arr2.length)
			throw new MatrixLengthNotEqualException(
					"**Exception**\nKey matrix and Text Matrix should have equal length.");
	}

	private static int[] multiplyMatrix(int[][] arr1, int[] arr2) {
		try {
			checkMatrixLengthForMultiplication(arr1, arr2);
			int[] result = new int[arr2.length];
			for (int i = 0; i < arr1.length; i++) {
				int sum = 0;
				for (int j = 0; j < arr1[0].length; j++) {
					sum += arr1[i][j] * arr2[j];
				}
				result[i] = (sum) % 26;
			}
			return result;
		} catch (MatrixLengthNotEqualException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String encrypt0(String plainText, int[][] keyMatrix, int blockSize) {
		final String ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
		String cipherText = "";
		int[] resultMatrix;

		for (int i = 0; i < plainText.length();) {

			int[] textArr = new int[blockSize];

			for (int j = 0; j < blockSize; j++, i++) {
				try {
					textArr[j] = ALPHABETS.indexOf(plainText.charAt(i));
				} catch (StringIndexOutOfBoundsException e) {
					textArr[j] = ALPHABETS.indexOf('z');
				}
			}

			resultMatrix = multiplyMatrix(keyMatrix, textArr);

			for (int j = 0; j < resultMatrix.length; j++) {
				cipherText += ALPHABETS.charAt(resultMatrix[j]);
			}
		}

		return cipherText;
	}
	
	public boolean checkMatrixModularInverse(int[][] matrix) {
		Matrix matrix1 = new Matrix(intToDouble(matrix));
		int d = modInverse((int) Math.round(matrix1.det()), 26);
		if(d == 0)
			return false;
		return true;
	}

	public String encrypt(String plainText, int[][] keyMatrix, boolean useAutoGeneratedKey) throws MatrixModularInverseIsZeroException {
		int blockSize, d = 0;
		if (useAutoGeneratedKey) {
			blockSize = 3;
			do {
				keyMatrix = generateRandomMatrix(blockSize);
				Matrix matrix = new Matrix(intToDouble(keyMatrix));
				d = modInverse((int) Math.round(matrix.det()), 26);
			} while(d == 0);
			HillCipher.keyMatrix = keyMatrix;
		} else {
			blockSize = keyMatrix.length;
			if(!checkMatrixModularInverse(keyMatrix))
				throw new MatrixModularInverseIsZeroException("Key Matrix provided has a modular inverse of 0.");
		}
		return encrypt0(plainText.toLowerCase(), keyMatrix, blockSize);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	// Decryption///////////////////////////////////////////////////////////////////////////////

	private static double[][] intToDouble(int[][] arr) {
		double[][] arr1 = new double[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr1[i][j] = (double) arr[i][j];
			}
		}
		return arr1;
	}

	public String decrypt(String cipherText, double[][] key) throws MatrixModularInverseIsZeroException {

		Matrix matrix = new Matrix(key);
		Matrix matrix1 = matrix.inverse();
		Matrix matTemp = new Matrix(key.length, key.length);
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key.length; j++) {
				matTemp.set(i, j, mod(Math.round(matrix1.get(i, j) * matrix.det()), 26d));
			}
		}
		int d = modInverse((int) Math.round(matrix.det()), 26);
		if(d == 0)
			throw new MatrixModularInverseIsZeroException("Key Matrix provided has a modular inverse of 0.");
		System.out.println(d);
		matTemp = matTemp.times(d);
		int[][] matFinalInverse = new int[key.length][key.length];
		for (int i = 0; i < key.length; i++) {
			for (int j = 0; j < key.length; j++) {
				matFinalInverse[i][j] = (int) mod(matTemp.get(i, j), 26);
			}
		}
//		
//		for(int i=0;i<3;i++){
//			System.out.println();
//			for(int j=0;j<3;j++){
//				System.out.print(matFinalInverse[i][j]+" ");
//			}
//		}

		String plainText = "";
		final String ALPHABETS = "abcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < cipherText.length();) {

			int[] textArr = new int[key.length];

			for (int j = 0; j < key.length; j++, i++) {
				textArr[j] = ALPHABETS.indexOf(cipherText.toLowerCase().charAt(i));
			}

			int[] finalMatrix = multiplyMatrix(matFinalInverse, textArr);
			for (int x : finalMatrix)
				plainText += ALPHABETS.charAt(x);
		}
//		System.out.println(plainText);

		return plainText;
	}

	public String decrypt(String cipherText, int[][] key) throws MatrixModularInverseIsZeroException {
		return decrypt(cipherText, intToDouble(key));
	}

	private static int modInverse(int a, int m) {
		a = a % m;
		for (int x = 1; x < m; x++)
			if ((a * x) % m == 1)
				return x;
		return 0;

	}

	private static double mod(double a, double b) {
		double i = a % b;
		if (i < 0)
			i += b;
		return i;
	}
}
