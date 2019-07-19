package com.elk.core.allure;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;

import java.util.function.Consumer;

public final class StepAssertion extends SoftAssertions {

    @Override
    @Step
    public void assertAll() {
        super.assertAll();
    }

    @Step("assertions")
    public static void assertSoftly(Consumer<SoftAssertions> softly) {
        SoftAssertions assertions = new SoftAssertions();
        softly.accept(assertions);
        assertions.assertAll();
    }
}
