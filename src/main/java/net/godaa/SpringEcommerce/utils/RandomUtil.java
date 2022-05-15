package net.godaa.SpringEcommerce.utils;

public class RandomUtil {

    public static String generateActivationKey() {
        return generateRandomAlphanumericString();
    }

    public static String generateRandomAlphanumericString() {
        return RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM);
    }
}
