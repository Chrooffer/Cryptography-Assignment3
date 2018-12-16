import java.io.BufferedReader;
import java.io.FileReader;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class CBCXor {

	public static void main(String[] args) {
		String filename = "input.txt";
		byte[] first_block = null;
		byte[] encrypted = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			first_block = br.readLine().getBytes();
			encrypted = DatatypeConverter.parseHexBinary(br.readLine());
			br.close();
		} catch (Exception err) {
			System.err.println("Error handling file.");
			err.printStackTrace();
			System.exit(1);
		}
		String m = recoverMessage(first_block, encrypted);
		System.out.println("Recovered message: " + m);
	}

	/**
	 * Recover the encrypted message (CBC encrypted with XOR, block size = 12).
	 *
	 * @param first_block
	 *            We know that this is the value of the first block of plain
	 *            text.
	 * @param encrypted
	 *            The encrypted text, of the form IV | C0 | C1 | ... where each
	 *            block is 12 bytes long.
	 */
	private static String recoverMessage(byte[] first_block, byte[] encrypted) {
		//In this case the encryption function is simply a XOR (+) operation with the key, i.e. Ci = K + (Mi + Ci-1), where C0 = IV.


		byte[] k = new byte[12];
		byte[] IV = Arrays.copyOfRange(encrypted, 0, 12);
		byte[] c1 = Arrays.copyOfRange(encrypted, 12, 24);
		byte[] m1 = first_block;

		/*
		System.out.println("k_empty = " + byteToNumberString(k));
		System.out.println("c0 = IV = " + byteToNumberString(IV));
		System.out.println("m1 = " + byteToNumberString(m1));

		System.out.println("### calculate key k ###"); */
		for (int i = 0; i<12; i++) {
			k[i] = (byte) (m1[i]^IV[i]^c1[i]);
		}
		//System.out.println("k_calculated = " + byteToNumberString(k));


		byte[] decrypted = new byte[encrypted.length];
		for (int i = 0; i<12; i++) {
			decrypted[i] = encrypted[i];
		}

		int maxBlock = encrypted.length / 12;
		int index;
		for (int i = 1; i<maxBlock; i++) {
			for (int j = 0; j<12; j++) {
				index = i*12 + j;
				decrypted[12*i + j] = (byte) (encrypted[index-12]^encrypted[index]^k[j]);
			}
		}

		// cut IV from decrypted array and convert into String
		byte[] mInBytes = Arrays.copyOfRange(decrypted, 12, decrypted.length);
		String m = new String(mInBytes);

		return m;
	}

	public static String byteToNumberString(byte[] bytes) {
		String result = "";
		for (int i = 0; i<bytes.length; i++) {
			result = result + bytes[i];
			if(i!= bytes.length-1) {
				result += ", ";
			}
		}
		return result;
	}

}
