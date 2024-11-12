package keqing.gtqtcore.api.utils;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Mathematic Utilities for {@code BigInteger}.
 *
 * @author Magic_Sweepy
 *
 * <p>
 *     This class is Calculation class for {@code BigInteger},
 *     supported some method in {@link Math} (but for {@code BigInteger}).
 * </p>
 *
 * @see BigInteger
 * @see BigDecimal
 */
@SuppressWarnings("unused")
public class BigMath {

    //  Basic Number with BigDecimal formatting.

    private static final BigDecimal TWO = new BigDecimal("2");

    private static final BigDecimal FOUR = new BigDecimal("4");

    //  Parameters from Math class (but use BigDecimal).

    private static final BigDecimal E = new BigDecimal(Math.E);

    private static final BigDecimal HALF_PI = new BigDecimal(Math.PI / 2);

    private static final BigDecimal PI = new BigDecimal(Math.PI);

    //  Math Context, used to set precision and round.
    //  Hint: I think this precision is enough for Minecraft :)

    private static final int DEFAULT_PRECISION = 100;

    private static final MathContext MC = new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_UP);

    /**
     * Raw {@code sin} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the trigonometric sine of an angle {@code a},
     *     different with {@link Math#sin(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param   a  An angle (in radians).
     * @return     The Sine of the argument {@code a}.
     */
    public static BigDecimal sin(BigDecimal a) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal term = a;
        BigDecimal aSquared = a.multiply(a);
        BigDecimal factorial = BigDecimal.ONE;
        for (int n = 1; term.abs().compareTo(BigDecimal.ZERO) > 0; n += 2) {
            result = result.add(term);
            factorial = factorial.multiply(new BigDecimal(n + 1)).multiply(new BigDecimal(n + 2));
            term = term.multiply(aSquared).negate().divide(factorial, MC);
        }
        return result;
    }

    /**
     * Raw {@code cos} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the trigonometric cosine of an angle {@code a},
     *     different with {@link Math#cos(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param   a  An angle (in radians).
     * @return     The Cosine of the argument {@code a}.
     */
    public static BigDecimal cos(BigDecimal a) {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal term = BigDecimal.ONE;
        BigDecimal aSquared = a.multiply(a);
        BigDecimal factorial = BigDecimal.ONE;
        for (int n = 0; term.abs().compareTo(BigDecimal.ZERO) > 0; n += 2) {
            result = result.add(term);
            factorial = factorial.multiply(new BigDecimal(n + 1)).multiply(new BigDecimal(n + 2));
            term = term.multiply(aSquared).negate().divide(factorial, MC);
        }
        return result;
    }

    /**
     * Raw {@code tan} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the trigonometric tangent of an angle {@code a},
     *     different with {@link Math#tan(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param   a  An angle (in radians).
     * @return     The Tangent of the argument {@code a}.
     */
    public static BigDecimal tan(BigDecimal a) {
        return sin(a).divide(cos(a), MC);
    }

    /**
     * Raw {@code asin} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the trigonometric arc sine of an angle {@code a},
     *     different with {@link Math#asin(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param   a  An angle (in radians).
     * @return     The Arc Sine of the argument {@code a}.
     */
    public static BigDecimal asin(BigDecimal a) {
        if (a.compareTo(BigDecimal.ONE) > 0 || a.compareTo(BigDecimal.ONE.negate()) < 0) {
            throw new ArithmeticException("Domain error");
        }
        if (a.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        if (a.compareTo(BigDecimal.ONE) == 0) {
            return HALF_PI;
        }
        if (a.compareTo(BigDecimal.ONE.negate()) == 0) {
            return HALF_PI.negate();
        }
        return atan(a.divide(sqrt(BigDecimal.ONE.subtract(a.multiply(a)), MC), MC));
    }

    /**
     * Raw {@code acos} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the trigonometric arc cosine of an angle {@code a},
     *     different with {@link Math#acos(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param   a  An angle (in radians).
     * @return     The Arc Cosine of the argument {@code a}.
     */
    public static BigDecimal acos(BigDecimal a) {
        if (a.compareTo(BigDecimal.ONE) > 0 || a.compareTo(BigDecimal.ONE.negate()) < 0) {
            throw new ArithmeticException("Domain error");
        }
        if (a.compareTo(BigDecimal.ZERO) == 0) {
            return HALF_PI;
        }
        if (a.compareTo(BigDecimal.ONE) == 0) {
            return BigDecimal.ZERO;
        }
        if (a.compareTo(BigDecimal.ONE.negate()) == 0) {
            return PI;
        }
        return HALF_PI.subtract(asin(a));
    }

    /**
     * Raw {@code atan} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the trigonometric arc tangent of an angle {@code a},
     *     different with {@link Math#atan(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param   a  An angle (in radians).
     * @return     The Arc Tangent of the argument {@code a}.
     */
    public static BigDecimal atan(BigDecimal a) {
        if (a.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        if (a.compareTo(BigDecimal.ONE) == 0) {
            return HALF_PI.divide(TWO, MC);
        }
        if (a.compareTo(BigDecimal.ONE.negate()) == 0) {
            return HALF_PI.divide(TWO, MC).negate();
        }
        if (a.abs().compareTo(BigDecimal.ONE) > 0) {
            return HALF_PI.subtract(atan(BigDecimal.ONE.divide(a, MC)));
        }
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal term = a;
        BigDecimal aSquared = a.multiply(a);
        BigDecimal twoNMinusOne = BigDecimal.ONE;
        for (int n = 1; term.abs().compareTo(BigDecimal.ZERO) > 0; n++) {
            result = result.add(term.divide(twoNMinusOne, MC));
            term = term.multiply(aSquared).negate();
            twoNMinusOne = new BigDecimal(2 * n + 1);
        }
        return result;
    }


    /**
     * Raw {@code exp} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the Euler's number <i>e</i> raised to the power of {@code a},
     *     different with {@link Math#exp(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param   a  The exponent to raise <i>e</i>.
     * @return     The value <i>e</i><sup>{@code a}</sup>, where <i>e</i> is the base of the natural logarithms.
     */
    public static BigDecimal exp(BigDecimal a) {
        BigDecimal result = BigDecimal.ONE;
        BigDecimal term = BigDecimal.ONE;
        BigDecimal factorial = BigDecimal.ONE;
        for (int n = 1; term.abs().compareTo(BigDecimal.ZERO) > 0; n++) {
            term = term.multiply(a).divide(new BigDecimal(n), MC);
            result = result.add(term);
        }
        return result;
    }

    /**
     * Raw {@code log} function for {@code BigInteger}.
     *
     * <p>
     *     This method returns the Natural Logarithm (base <i>e</i>) of {@code a} ({@code ln(a)}),
     *     different with {@link Math#log(double)}, this method is for {@code BigInteger},
     *     or we can see: {@code BigDecimal}.
     * </p>
     *
     * @param a  A {@code BigDecimal} value.
     * @return   Natural Logarithm of {@code a}.
     */
    public static BigDecimal log(BigDecimal a) {
        if (a.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Logarithm of non-positive number is undefined.");
        }
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal term = a.subtract(BigDecimal.ONE).divide(a.add(BigDecimal.ONE), MC);
        BigDecimal termSquared = term.multiply(term);
        BigDecimal factor = BigDecimal.ONE;
        for (int n = 1; term.abs().compareTo(BigDecimal.ZERO) > 0; n += 2) {
            result = result.add(term.divide(new BigDecimal(n), MC));
            term = term.multiply(termSquared);
            factor = factor.add(BigDecimal.ONE);
        }
        return result.multiply(TWO);
    }

    /**
     * Raw {@code log10} function for {@code BigInteger}.
     *
     * <p>
     *     Just do base changing of {@link #log(BigDecimal)} ^^.
     *     <pre>{@code
     *         \log_{10}(x) = \frac{\log_{e}(x)}{\log_{e}(10)}
     *     }</pre>
     *     So log10 method = (log(x)/log(10))/log(e).
     * </p>
     *
     * @param a  {@code BigDecimal} Value.
     * @return   The base 10 logarithm of {@code a}.
     */
    public static BigDecimal log10(BigDecimal a) {
        if (a.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ArithmeticException("Logarithm of non-positive number is undefined.");
        }
        return log(a).divide(log(BigDecimal.TEN).divide(log(E), MC), MC);
    }

    /**
     * Raw {@code sqrt} function for {@code BigInteger} (cannot set precision).
     *
     * <p>
     *     Default Precision: {@link #MC}.
     * </p>
     *
     * @param a  A {@code BigDecimal} value.
     * @return   Rounded positive Square Root of {@code a}.
     */
    public static BigDecimal sqrt(BigDecimal a) {
        BigDecimal b = a.divide(TWO, MC);
        boolean done = false;
        final int maxIterations = MC.getPrecision() + 1;
        for (int i = 0; !done && i < maxIterations; i++) {
            BigDecimal c = a.divide(b, MC);
            c = c.add(b);
            c = c.divide(TWO, MC);
            done = c.equals(b);
            b = c;
        }
        return b;
    }

    /**
     * Raw {@code sqrt} function for {@code BigInteger}.
     *
     * @param a  A {@code BigDecimal} value.
     * @return   Rounded positive Square Root of {@code a}.
     */
    public static BigDecimal sqrt(BigDecimal a, MathContext mc) {
        BigDecimal b = a.divide(TWO, mc);
        boolean done = false;
        final int maxIterations = mc.getPrecision() + 1;
        for (int i = 0; !done && i < maxIterations; i++) {
            BigDecimal c = a.divide(b, mc);
            c = c.add(b);
            c = c.divide(TWO, mc);
            done = c.equals(b);
            b = c;
        }
        return b;
    }

    /**
     * Returns the smaller of two {@code BigInteger} values.
     * If the arguments have the same value, the result is that same value.
     *
     * @param a  an argument.
     * @param b  another argument.
     * @return   the smallest of {@code a} and {@code b}.
     */
    public static BigInteger min(BigInteger a, BigInteger b) {
        int result = a.compareTo(b);
        BigInteger minValue;
        if (result < 0) {
            minValue = a;
        } else {
            minValue = b;
        }
        return minValue;
    }

    /**
     * Returns the bigger of two {@code BigInteger} values.
     * If the arguments have the same value, the result is that same value.
     *
     * @param a  an argument.
     * @param b  another argument.
     * @return   the biggest of {@code a} and {@code b}.
     */
    public static BigInteger max(BigInteger a, BigInteger b) {
        int result = a.compareTo(b);
        BigInteger maxValue;
        if (result < 0) {
            maxValue = b;
        } else {
            maxValue = a;
        }
        return maxValue;
    }

    /**
     * Calculates sum of all elements in {@code values} ({@code long} array).
     *
     * <p>
     *     Hint: Donot adding the next value in {@code values} to current sum, maybe cause an overflow.
     * </p>
     *
     * @param values  Values to be summed.
     * @return        A {@code BigInteger} representing the sum of elements in {@code values}.
     */
    public static BigInteger summarizedValue(long[] values) {
        BigInteger retValue = BigInteger.ZERO;
        long currentSum = 0;

        for (long value : values) {
            if (currentSum != 0 && value > Long.MAX_VALUE - currentSum) {
                retValue = retValue.add(BigInteger.valueOf(currentSum));
                currentSum = 0;
            }
            currentSum += value;
        }

        if (currentSum != 0) {
            retValue = retValue.add(BigInteger.valueOf(currentSum));
        }

        return retValue;
    }

    public static BigInteger clamp(BigInteger value, BigInteger min, BigInteger max) {
        int result = value.compareTo(min);
        if (result < 0) {
            return min;
        } else {
            return min(value, max);
        }
    }

}

