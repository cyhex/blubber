package com.b14h.libs;

import org.junit.Assert;
import org.junit.Test;


public class BlubConverterTest {

    @Test
    public void testToBlub() {
        Assert.assertEquals(100, BlubConverter.toBlub(1), 0.1);
    }

    @Test
    public void testToEur() {
        Assert.assertEquals(1, BlubConverter.toEur(100), 0.1);
    }

}