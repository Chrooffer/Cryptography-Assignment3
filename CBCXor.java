import java.io.BufferedReader;
import java.io.FileReader;

import javax.xml.bind.DatatypeConverter;

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


		return new String(encrypted);
	}
}


/*
Decrypting CBC with simple XOR (5 points)
We intercepted a message that was encrypted using cypher-block chaining.
We also know the plain-text value of the first block.
Can you reconstruct the complete plain-text message?

Less

First, have a re-cap on how CBC works. In this case
the encryption function is simply a XOR (+) operation
with the key, i.e. Ci = K + (Mi + Ci-1), where C0 = IV.

In the text box below you find the known first block (= the
12 digit number your provided) and the encrypted message.
You know that the block size is 12.

199311300478

62C9C46C1E22C27C48C0BE76EDF611DC83414A0AD56EC1111F999A375B3385
24119AE23D81F6029483135F14D96CD94259919D634A33C4261C97FF3D86F9
08948E081107916FC216189690374B298A364E9AE42E88B032DBC716500497
65DE515ED9A9780D2598361A93FF6988AB658FD3165515DB7A9B165FD9A96A
162A8A205694BB2CC1BB249BD1165415882E9B120C9DA9740A25962005D3BC
3992EF2D92D4120D00C13B871200C9A07F1C60DF354CD6AE24CAA6229B9054
0B01C1259255548DEE4C5974C23E4CEFA86B9EE86AAA81524948D145D004
*/
