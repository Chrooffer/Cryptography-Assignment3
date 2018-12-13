// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

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
		int s = -1;
		int t = -1;
		int[] result = new int[3];
		result[0] = gcd;
		result[1] = s;
		result[2] = t;
		return result;


	}

	/**
	 * Returns Euler's Totient for value "n".
	 **/
	public static int EulerPhi(int n) {
		return -1;
	}

	/**
	 * Returns the value "v" such that "n*v = 1 (mod m)". Returns 0 if the
	 * modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {
		return -1;
	}

	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {
		return -1;
	}

	/**
	 * Returns the probability that calling a perfect hash function with
	 * "n_samples" (uniformly distributed) will give one collision (i.e. that
	 * two samples result in the same hash) -- where "size" is the number of
	 * different output values the hash function can produce.
	 **/
	public static double HashCP(double n_samples, double size) {
		return -1;
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
