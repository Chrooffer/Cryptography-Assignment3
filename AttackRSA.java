import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class AttackRSA {

	public static void main(String[] args) {
		String filename = "input.txt";
		BigInteger[] N = new BigInteger[3];
		BigInteger[] e = new BigInteger[3];
		BigInteger[] c = new BigInteger[3];
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for (int i = 0; i < 3; i++) {
				String line = br.readLine();
				String[] elem = line.split(",");
				N[i] = new BigInteger(elem[0].split("=")[1]);
				e[i] = new BigInteger(elem[1].split("=")[1]);
				c[i] = new BigInteger(elem[2].split("=")[1]);
			}
			br.close();
		} catch (Exception err) {
			System.err.println("Error handling file.");
			err.printStackTrace();
		}
		BigInteger m = recoverMessage(N, e, c);
		System.out.println("Recovered message: " + m);
		System.out.println("Decoded text: " + decodeMessage(m));
	}

	public static String decodeMessage(BigInteger m) {
		return new String(m.toByteArray());
	}

	/**
	 * Tries to recover the message based on the three intercepted cipher texts.
	 * In each array the same index refers to same receiver. I.e. receiver 0 has
	 * modulus N[0], public key d[0] and received message c[0], etc.
	 *
	 * @param N
	 *            The modulus of each receiver.
	 * @param e
	 *            The public key of each receiver (should all be 3).
	 * @param c
	 *            The cipher text received by each receiver.
	 * @return The same message that was sent to each receiver.
	 */
	private static BigInteger recoverMessage(BigInteger[] N, BigInteger[] e,
			BigInteger[] c) {
				//Do Assignment
    /*
    N=541943169829234727477122697102275720972053146173972333934461,e=3,c=398924993181138906590584190725292968825560162810793723052587
    N=1235559568656185372229671459847644219326103310686849347266229,e=3,c=492210655361863905807061259315457553414294037367103445414124
    N=1189363168839866028644614938349842324542055957124930112530987,e=3,c=732007191931262531495434848531299493575938917093773948145721
    */
		//d = e^-1 mod phi(N)
		//BigInteger eulerPhi = CryptoLib.EulerPhi(N[0]);

		//BigInteger bie = BigInteger.valueOf(myInteger.intValue());

		//String d = CryptoLib.ModInv(e, eulerPhi);

		BigInteger[] bic = new BigInteger[3];
		BigInteger[] bid = new BigInteger[3];
		BigInteger[] biephi = new BigInteger[3];


    for (int i = 0; i<3; i++){
      System.out.println("N[" + i + "]: " + N[i]);
      System.out.println("e[" + i + "]: " + e[i]);
      System.out.println("c[" + i + "]: " + c[i]);
			
			biephi[i] = e[i].modInverse(N[i]);
			System.out.println("ModInverse of N[i]: " + biephi[i]);

			System.out.println();
    }


		return BigInteger.ZERO;
	}

  /*
  Attacking RSA (10 points)
  This attack applies to the case in which the same message is
  encrypted using RSA to three different recipients.
  The enablers of the attack are (1) all recipients have
  the same public key (e = 3) and (2) the recipients have
  different modulus (N1, N2, N3) that are coprime. Your goal
  in this assignment is to use the three eavesdropped ciphertexts
  and recover the secret message! The message you will recover
  is an ASCII encoding of a name you should know ;)

  The text field below displays the modulus (N), the public key
  (e) and the cipher text (c) of three recipients of the same message (m).
  Your goal is to retrieve m.

  You can try starting from here, but we provide additional hints if you want some help. Give a hint

  1. We know that each cipher text is computed as m3. Why does it not make sense to directly compute the cube root on one the cipher texts (c)?

  2. Computing the cube root with some modulus N is equivalent to solving the discrete
  log problem for exponent e=3, which we know to be hard.
  What about trying to move to a bigger modulus so that no modular reduction occurs when you encrypt the message?

  3. N1N2N3 is a large enough modulus, since in RSA encryption the message must be an element in ZNi.
  That is, you can see this is as m < Ni for i=1,2,3 and therefore m3 < N1N2N3.

  4. In order to find m3 the large modulus you can use the Chinese Remainder Theorem.

  5. This is your last hint. https://en.wikipedia.org/wiki/Chinese_remainder_theorem

  N=541943169829234727477122697102275720972053146173972333934461,e=3,c=398924993181138906590584190725292968825560162810793723052587
  N=1235559568656185372229671459847644219326103310686849347266229,e=3,c=492210655361863905807061259315457553414294037367103445414124
  N=1189363168839866028644614938349842324542055957124930112530987,e=3,c=732007191931262531495434848531299493575938917093773948145721
  */

}
