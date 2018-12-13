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
		return new String(encrypted);
	}
}


/*
199311300478
62C9C46C1E22C27C48C0BE76EDF611DC83414A0AD56EC1111F999A375B338524119AE23D81F6029483135F14D96CD94259919D634A33C4261C97FF3D86F908948E081107916FC216189690374B298A364E9AE42E88B032DBC71650049765DE515ED9A9780D2598361A93FF6988AB658FD3165515DB7A9B165FD9A96A162A8A205694BB2CC1BB249BD1165415882E9B120C9DA9740A25962005D3BC3992EF2D92D4120D00C13B871200C9A07F1C60DF354CD6AE24CAA6229B90540B01C1259255548DEE4C5974C23E4CEFA86B9EE86AAA81524948D145D004
*/
