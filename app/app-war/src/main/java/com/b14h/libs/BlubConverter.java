package com.b14h.libs;

public class BlubConverter {

    public static double toBlub(double eur) {
        return eur / Constants.BLUB_EUR_RATIO;
    }

    public static double toEur(double blub) {
        return blub * Constants.BLUB_EUR_RATIO;
    }

}
