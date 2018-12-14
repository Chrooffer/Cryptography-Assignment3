// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

import java.util.ArrayList;

public class CryptoLib {

	/**
	 * Returns an array "result" with the values "result[0] = gcd",
	 * "result[1] = s" and "result[2] = t" such that "gcd" is the greatest
	 * common divisor of "a" and "b", and "gcd = a * s + b * t".
	 **/
	public static int[] EEA(int a, int b) {
		// Note: as you can see in the test suite,
		// your function should work for any (positive) value of a and b.

		int gcd = -1;
		int[] s = new int[3];
		int[] t = new int[3];
		int[] result = new int[3];
		int[] reminder = new int[3];
		int quotient = -1;

		if (a < 1 || b < 1){
			System.out.println("Bad Values (below 0)");
			return result;
		}

		reminder[0] = a;
		reminder[1] = b;
		s[0] = 1;
		s[1] = 0;
		t[0] = 0;
		t[1] = 1;

		/*System.out.println("INITIAL");
		System.out.println("q = " + quotient);
		System.out.println("r0="+reminder[0]+"\t r1="+reminder[1]+"\t r2="+reminder[2]);
		System.out.println("s0="+s[0]+"\t s1="+s[1]+"\t s2="+s[2]);
		System.out.println("t0="+t[0]+"\t t1="+t[1]+"\t t2="+t[2]);*/

		boolean success = false;
		while (!success) {
			//Calculate values of new row
			quotient = (reminder[0] / reminder[1]);
			//System.out.println("q="+quotient);
			reminder[2] =  reminder[0] % reminder[1];
			//System.out.println("r2="+reminder[2]);
			s[2] = s[0] - s[1]*quotient;
			//System.out.println("s2="+s[2]);
			t[2] = t[0] - t[1]*quotient;
			//System.out.println("t2="+t[2]);

			//Pushing up one row
			reminder[0] = reminder[1];
			reminder[1] = reminder[2];
			s[0] = s[1];
			s[1] = s[2];
			t[0] = t[1];
			t[1] = t[2];

			/*System.out.println("q = " + quotient);
			System.out.println("r0="+reminder[0]+"\t r1="+reminder[1]+"\t r2="+reminder[2]);
			System.out.println("s0="+s[0]+"\t s1="+s[1]+"\t s2="+s[2]);
			System.out.println("t0="+t[0]+"\t t1="+t[1]+"\t t2="+t[2]);*/

			if(reminder[1] == 0) {
				success = true;
				gcd = reminder[0];
				//System.out.println("Success - gcd: "+gcd);
			}
		}

		result[0] = gcd;
		result[1] = s[0];
		result[2] = t[0];
		return result;
	}

	/*
	 * Returns Euler's Totient for value "n".
	 **/
	public static int EulerPhi(int n) {

		int reminder = n;
		int counter = 2;
		ArrayList<Integer> primeFactors = new ArrayList<Integer>();

		// special cases n = 1 and n < 1
		if(n==1) {
			return 1;
		}
		if(n<1) {
			return 0;
		}

		boolean success = false;
		while(!success) {
			if (reminder % counter == 0) {
				reminder = reminder /counter;
				primeFactors.add(counter);
				//System.out.println(counter);
				counter = 2;
			}
			else {
				counter++;
			}

			if (counter == reminder) {
				success = true;
				primeFactors.add(counter);
				//System.out.println(counter);
			}
		}

		int phi = 1;
		int lastPrime = -1;
		for (int i=0; i<primeFactors.size(); i++) {
			if(primeFactors.get(i).equals(lastPrime)) {
				phi = phi * primeFactors.get(i);
			}
			else {
				int factor = primeFactors.get(i) - 1;
				phi = phi * factor;
				lastPrime = primeFactors.get(i);
			}
		}

		return phi;
	}

	/**
	 * Returns the value "v" such that "n*v = 1 (mod m)".
	 * Returns 0 if the modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {

		int counter = 1;
		boolean success = false;

		// dealing with negative n --> find equivalent case with positive n
		while (n<0) {
			n = n+m;
		}
		// special case n=0 (would be covered in the algorithm but here it's a shortcut)
		if (n == 0) {
			return 0;
		}

		while (!success && counter<m) {
			if ((counter*n)%m == 1) {
				success = true;
			}
			else {
				counter++;
			}
		}
		if (success) {
			return counter;
		}
		else {
			return 0;
		}
	}

	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {

		int upperLimit = n/3;
		for (int a = 2; a<=upperLimit; a++) {
			int x = 0;
			for (int i = 1; i<n; i++) {
				if (i == 1) {
					x = a % n;
				}
				else {
					x = (x*a) % n;
				}
			}

			if(x != 1) {
					return a;

			}
		}
		return 0;
	}

	/**
	 * Returns the probability that calling a perfect hash function with
	 * "n_samples" (uniformly distributed) will give one collision (i.e. that
	 * two samples result in the same hash) -- where "size" is the number of
	 * different output values the hash function can produce.
	 **/
	public static double HashCP(double n_samples, double size) {

		double probCollision = 1;
		double probNoCollision = 1;

		//edge case if n_samples greater than size a collision can't be avoided
		if (n_samples > size) {
			return 0;
		}

		//calculate probability of no collision
		for (int i = 0; i<n_samples; i++) {
			double singleProb = (size - i)/size;
			probNoCollision = probNoCollision * singleProb;
		}

		//probabulity of collision is 1 - probability of no collision
		probCollision = 1-probNoCollision;

		return probCollision;
	}

}
/*A Math Library for Cryptography (5 points)
In this assignment you will implement a library that
provides a number of mathematical functions commonly
used in cryptography, such as Euler's Phi Function (Totient),
the Extended Euclidean Algorithm and some Primality Test.

Your library needs to successfully pass a test suite that we provide.

In this assignment you are required to follow the skeleton code
we provide, to make sure that the test suite works.

All functions should work for arbitrary integers.
You are not allowed to call already implemented libraries
(BigInteger, GMP, ...).
Make sure your implementation passes our tests before submitting.

The following archives contain a skeleton code and test
suite for each programming language: cryptolib_cpp.zip,
cryptolib_java.zip and cryptolib_haskell.zip.

You should implement the following functions
(plus any helper code you deem necessary):

Extended Euclidean Algorithm
Euler's Phi Function (Totient)
Modular Inverse
Your function should return 0 if the number is not invertible.
Fermat Primality test
Instead of picking random values a to test the primality of a number n, make a start from 2 and increment it by 1 at each new iteration, until you have tested all the values below n/3.
HashCollisionProbability
*/
