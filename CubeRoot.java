import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * Helper class for the RSA assignment. This class provides the method 'cbrt'
 * which returns the cube root of a BigInteger.
 *
 * The code is taken from
 * "A Java Math.BigDecimal Implementation of Core Mathematical Functions"
 * Available at: http://arxiv.org/abs/0908.3030
 */
public class CubeRoot {

	/**
	 * Returns the cube root for big integers.
	 *
	 * @param val
	 *            Value to compute the cube root of.
	 * @return (Rounded down) cube root of argument. That is, a value x such
	 *         that x*x*x = val.
	 */
	static public BigInteger cbrt(BigInteger val) {
		return root(3, new BigDecimal(val)).toBigInteger();
	}

	/**
	 * The integer root.
	 *
	 * @param n
	 *            the positive argument.
	 * @param x
	 *            the non-negative argument.
	 * @return The n-th root of the BigDecimal rounded to the precision implied
	 *         by x, x^(1/n).
	 */
	static private BigDecimal root(final int n, final BigDecimal x) {
		if (x.compareTo(BigDecimal.ZERO) < 0) {
			throw new ArithmeticException("negative argument " + x.toString()
					+ " of root");
		}
		if (n <= 0) {
			throw new ArithmeticException("negative power " + n + " of root");
		}
		if (n == 1) {
			return x;
		}
		/* start the computation from a double precision estimate */
		BigDecimal s = new BigDecimal(Math.pow(x.doubleValue(), 1.0 / n));
		/*
		 * this creates nth with nominal precision of 1 digit
		 */
		final BigDecimal nth = new BigDecimal(n);
		/*
		 * Specify an internal accuracy within the loop which is slightly larger
		 * than what is demanded by Ã‚epsÃ‚ below.
		 */
		final BigDecimal xhighpr = scalePrec(x, 2);
		MathContext mc = new MathContext(2 + x.precision());
		/*
		 * Relative accuracy of the result is eps.
		 */
		final double eps = x.ulp().doubleValue() / (2 * n * x.doubleValue());
		for (;;) {
			/*
			 * s = s -(s/n-x/n/s^(n-1)) = s-(s-x/s^(n-1))/n; test correction
			 * s/n-x/s for being smaller than the precision requested. The
			 * relative correction is (1-x/s^n)/n,
			 */
			BigDecimal c = xhighpr.divide(s.pow(n - 1), mc);
			c = s.subtract(c);
			MathContext locmc = new MathContext(c.precision());
			c = c.divide(nth, locmc);
			s = s.subtract(c);
			if (Math.abs(c.doubleValue() / s.doubleValue()) < eps) {
				break;
			}
		}
		return s.round(new MathContext(err2prec(eps)));
	} /* BigDecimalMath.root */

	/**
	 * Append decimal zeros to the value. This returns a value which appears to
	 * have a higher precision than the input.
	 *
	 * @param x
	 *            The input value
	 * @param d
	 *            The (positive) value of zeros to be added as least significant
	 *            digits.
	 * @return The same value as the input but with increased (pseudo)
	 *         precision.
	 */
	static private BigDecimal scalePrec(final BigDecimal x, int d) {
		return x.setScale(d + x.scale());
	}

	/**
	 * Convert a relative error to a precision.
	 *
	 * @param xerr
	 *            The relative error in the variable. The value returned depends
	 *            only on the absolute value, not on the sign.
	 * @return The number of valid digits in x. The value is rounded down, and
	 *         on the pessimistic side for that reason.
	 */
	static private int err2prec(double xerr) {
		/*
		 * Example: an error of xerr=+-0.5 a precision of 1 (digit), an error of
		 * +-0.05 a precision of 2 (digits)
		 */
		return 1 + (int) (Math.log10(Math.abs(0.5 / xerr)));
	}

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
