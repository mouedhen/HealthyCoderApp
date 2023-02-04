package com.healthycoderapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    void test() {
        assertTrue(BMICalculator.isDietRecommended(81.2, 1.65));
    }

}