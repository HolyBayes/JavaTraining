package com.noveogroup.java.generator;

import java.util.Date;
import java.util.Random;

/**
 * The generator generates random data.
 * <p>
 * It uses {@link ${artifactId}.util.Random} for generating numbers and own algorithm for generation strings
 * </p>
 *
 * @author Mikhail Khorkov
 */
public class Generator {
    /**
     * Max length of local or domain part of email address.
     */
    public static final int MAX_EMAIL_LEN = 15;

    /**
     * Max time deviation from now.
     */
    public static final int MAX_TIME_DEVIATION = Integer.MAX_VALUE;

    private static final char[] ALPHA = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '_', '-', '.', };

    private static final String[] DOMAIN_END = new String[]{".com", ".ru", ".рф", "org"};

    private final Random rand;

    /**
     * Constructor.
     * <p>
     * Initializing by {@link System${symbol_pound}nanoTime()}
     * </p>
     */
    public Generator() {
        rand = new Random(System.nanoTime());
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextBytes}.
     */
    public void nextBytes(final byte[] bytes) {
        rand.nextBytes(bytes);
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextInt}.
     */
    public int nextInt() {
        return rand.nextInt();
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextInt(int)}.
     */
    public int nextInt(final int n) {
        return rand.nextInt(n);
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextLong}.
     */
    public long nextLong() {
        return rand.nextLong();
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextBoolean}.
     */
    public boolean nextBoolean() {
        return rand.nextBoolean();
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextFloat}.
     */
    public float nextFloat() {
        return rand.nextFloat();
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextDouble}.
     */
    public double nextDouble() {
        return rand.nextDouble();
    }

    /**
     * Delegate to {@link ${artifactId}.util.Random${symbol_pound}nextGaussian}.
     */
    public double nextGaussian() {
        return rand.nextGaussian();
    }

    /**
     * Generate string with length.
     * <p>
     * New string must satisfy a regex [a-zA-Z._-]+
     * </p>
     *
     * @param len length of new string
     * @return random string with the len length
     * @throws IllegalArgumentException if len <= 0
     */
    public String nextWord(final int len) {
        if (len <= 0) {
            throw new IllegalArgumentException("len must be positive");
        }
        final char[] result = new char[len];
        final int alphaLen = ALPHA.length;
        for (int i = 0; i < len; ++i) {
            result[i] = ALPHA[nextInt(alphaLen)];
        }
        return new String(result);
    }

    /**
     * Generate email address.
     * <p>
     * Local and domain parts of new email might be less or equal
     * {@link ${package}.util.ValueGenerator${symbol_pound}MAX_EMAIL_LEN}
     * </p>
     *
     * @return 'valid' email address
     */
    public String nextEmail() {
        final int localLen = nextInt(MAX_EMAIL_LEN - 1) + 1;
        final int domainLen = nextInt(MAX_EMAIL_LEN - 1) + 1;
        final String domainEnd = DOMAIN_END[nextInt(DOMAIN_END.length)];
        return nextWord(localLen) + '@' + nextWord(domainLen) + domainEnd;
    }

    /**
     * Generate {@link ${artifactId}.util.Date} from past or future.
     * <p>
     * New date might be in
     * {@link ${package}.util.ValueGenerator${symbol_pound}MAX_TIME_DEVIATION} milliseconds
     * </p>
     *
     * @return date from past or future
     */
    public Date nextDate() {
        final int timeToggle = nextBoolean() ? 1 : -1;
        final long now = System.currentTimeMillis();
        return new Date(now + timeToggle * nextInt(MAX_TIME_DEVIATION));
    }
}
