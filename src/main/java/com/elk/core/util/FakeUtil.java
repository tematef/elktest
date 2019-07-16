package com.elk.core.util;

import com.github.javafaker.Faker;

public class FakeUtil {

    private static Faker faker = new Faker();

    public static Faker faker() {
        return faker;
    }
}
