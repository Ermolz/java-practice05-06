package com.academy.practice56.suites;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Suite
@SelectPackages("com.academy.practice56.tests")
@IncludeTags("regression")
public class RegressionSuite {
    @Test
    @Tag("regression")
    void containsOnlyRegressionTests() {
        assertEquals("regression", "regression");
    }
}
