package com.b14h.services;

import org.junit.Assert;
import org.junit.Test;

public class UpcServiceTest {

    @Test
    public void testSearch() throws Exception {
        String res = UpcService.search("4029764001807");
        Assert.assertTrue(res.contains("productname"));
    }
}