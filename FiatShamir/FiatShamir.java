import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

public class FiatShamir {

	public static class ProtocolRun {
		public final BigInteger R;
		public final int c;
		public final BigInteger s;

		public ProtocolRun(BigInteger R, int c, BigInteger s) {
			this.R = R;
			this.c = c;
			this.s = s;
		}
	}

	public static void main(String[] args) {
		String filename = "input.txt";
		BigInteger N = BigInteger.ZERO;
		BigInteger X = BigInteger.ZERO;
		ProtocolRun[] runs = new ProtocolRun[10];
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			N = new BigInteger(br.readLine().split("=")[1]);
			X = new BigInteger(br.readLine().split("=")[1]);
			for (int i = 0; i < 10; i++) {
				String line = br.readLine();
				String[] elem = line.split(",");
				runs[i] = new ProtocolRun(
						new BigInteger(elem[0].split("=")[1]),
						Integer.parseInt(elem[1].split("=")[1]),
						new BigInteger(elem[2].split("=")[1]));
			}
			br.close();
		} catch (Exception err) {
			System.err.println("Error handling file.");
			err.printStackTrace();
			System.exit(1);
		}
		BigInteger m = recoverSecret(N, X, runs);
		System.out.println("Recovered message: " + m);
		System.out.println("Decoded text: " + decodeMessage(m));
	}

	public static String decodeMessage(BigInteger m) {
		return new String(m.toByteArray());
	}

	/**
	 * Recovers the secret used in this collection of Fiat-Shamir protocol runs.
	 *
	 * @param N
	 *            The modulus
	 * @param X
	 *            The public component
	 * @param runs
	 *            Ten runs of the protocol.
	 * @return
	 */
	private static BigInteger recoverSecret(BigInteger N, BigInteger X,
			ProtocolRun[] runs) {
		//Recover the secret value x such that x^2 = X (mod N).


		// find runs with same R
		int runIndex1 = 0;
		int runIndex2 = 1;

		/*
		 * Print all R of the runs
		 *
		for (int i = 0; i<runs.length; i++) {
			System.out.println("R_of_Run_"+i+":  "+runs[i].R);
		}*/

		// Find runs with matching R
		while (runIndex1 < runs.length-1 && !runs[runIndex1].R.equals(runs[runIndex2].R)) {
			//System.out.println("rI1="+runIndex1+", rI2="+runIndex2);
			if(runIndex2<runs.length-1) {
				runIndex2++;
			}
			else {
				runIndex1++;
				runIndex2 = runIndex1+1;
			}
		}
		System.out.println("FOUND MATCH --> rI1="+runIndex1+", rI2="+runIndex2);

		/*
		 * Outputs for debugging
		 *
		System.out.println("\nN = "+N);
		System.out.println("X = "+X);
		System.out.println("\n### Index1 = "+runIndex1+" ###");
		System.out.println("R1 = "+runs[runIndex1].R);
		System.out.println("c1 = "+runs[runIndex1].c);
		System.out.println("s1 = "+runs[runIndex1].s);
		System.out.println("\n### Index2 = "+runIndex2+" ###");
		System.out.println("R2 = "+runs[runIndex2].R);
		System.out.println("c2 = "+runs[runIndex2].c);
		System.out.println("s2 = "+runs[runIndex2].s);
		*/

		// differentiate c=0 and c=1
		int run1 = -1;
		int run2 = -1;
		if (runs[runIndex1].c == runs[runIndex2].c) {	// end function if e is the same for both cases
			return null;
		}
		else {
			if(runs[runIndex1].c == 0) {
				run1 = runIndex1;
				run2 = runIndex2;
			}
			else {
				run1 = runIndex2;
				run2 = runIndex1;
			}
		}

		/*
		 * Computation to break protokol
		 * s1 = R mod N		--> for e = 0
		 * s2 = R * x mod N	--> for e = 1
		 *
		 * therefore compute
		 * x = s2 * r^-1 = s2 * s1^-1 mod N
		 */

		BigInteger R = runs[run1].R;	// same for run 1 and 2
		int c1 = runs[run1].c;		// is 0
		BigInteger s1 = runs[run1].s;
		int c2 = runs[run2].c;		// is 1
		BigInteger s2 = runs[run2].s;

		/*
		 * Output to check Code

		System.out.println("\n### STAGE ###");
		System.out.println("R = "+R);
		System.out.println("N = "+N);
		System.out.println("\n### Run1 ###");
		System.out.println("c1 = "+c1);
		System.out.println("s1 = "+s1);
		System.out.println("\n### Run2 ###");
		System.out.println("c2 = "+c2);
		System.out.println("s2 = "+s2);
		*/

		//BigInteger s1_inv = BigInteger.ZERO;
		BigInteger s1_inv = s1.modInverse(N);
		BigInteger x = s2.multiply(s1_inv);
		x = x.mod(N);




		System.out.println("\nx = "+x);
		return x;

		//return BigInteger.ZERO;
	}
}
