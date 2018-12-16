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
		Generated for 199311300478.
    N=541943169829234727477122697102275720972053146173972333934461,e=3,c=398924993181138906590584190725292968825560162810793723052587
    N=1235559568656185372229671459847644219326103310686849347266229,e=3,c=492210655361863905807061259315457553414294037367103445414124
    N=1189363168839866028644614938349842324542055957124930112530987,e=3,c=732007191931262531495434848531299493575938917093773948145721

		// find c such that c = c1 mod N1 = c2 mod N2 = c3 mod N3
				// find largest Ni


				/* Smaller Values to test implementation of chinese theorem
				N[0] = BigInteger.valueOf(10);
				N[1] = BigInteger.valueOf(3);
				N[2] = BigInteger.valueOf(7);

				c[0] = BigInteger.valueOf(1);
				c[1] = BigInteger.valueOf(2);
				c[2] = BigInteger.valueOf(6);*/

				// Set up for Chinese theorem
				BigInteger[] N_prod = new BigInteger[3];
				N_prod[0] = N[1].multiply(N[2]);
				N_prod[1]= N[0].multiply(N[2]);
				N_prod[2] = N[0].multiply(N[1]);
				BigInteger N_total = N[0].multiply(N[1].multiply(N[2]));

				BigInteger x[] = new BigInteger[3];

				/*System.out.println("### INITIAL ###");
				System.out.println("N[0] =\t"+N[0]);
				System.out.println("N[1] =\t"+N[1]);
				System.out.println("N[2] =\t"+N[2]);
				System.out.println("c[0] =\t"+c[0]);
				System.out.println("c[1] =\t"+c[1]);
				System.out.println("c[2] =\t"+c[2]);
				System.out.println("N0_prod =\t"+N_prod[0]);
				System.out.println("N1_prod =\t"+N_prod[1]);
				System.out.println("N2_prod =\t"+N_prod[2]);
				System.out.println("N_total =\t"+N_total);
				System.out.println("x[0] =\t"+x[0]);
				System.out.println("x[1] =\t"+x[1]);
				System.out.println("x[2] =\t"+x[2]);*/

				// Calculate x values of Chinese theorem
				for (int i = 0; i<3; i++) {
					x[i] = N_prod[i].modInverse(N[i]);
					//System.out.println("x["+i+"] =\t"+x[i]);
				}

				// Calculate C based on x, c and N_prod values
				BigInteger C = BigInteger.ZERO;
				BigInteger[] y = new BigInteger[3];
				for (int i = 0; i<3; i++) {
					y[i] = x[i].multiply(c[i].multiply(N_prod[i]));
					C = C.add(y[i]);
				}
				C = C.mod(N_total);	// find small than N1*N2*N3
				System.out.println("C = "+ C);

				// cbrt to calculate m from C
				BigInteger m = CubeRoot.cbrt(C);

				return m;
	}

}
