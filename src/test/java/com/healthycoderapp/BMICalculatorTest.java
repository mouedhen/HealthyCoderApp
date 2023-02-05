package com.healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Before all unit tests.");
    }

    @Test
    void should_ReturnTrue_When_DietRecommended() {
        // given
        double weight = 81.2;
        double height = 1.65;

        // when
        boolean isDietRecommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertTrue(isDietRecommended);
    }

    @Test
    void should_ReturnFalse_When_DietNotRecommended() {
        // given
        double weight = 50.0;
        double height = 1.92;

        // when
        boolean isDietRecommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertFalse(isDietRecommended);
    }

    @Test
    void should_ThrowArithmeticException_When_HeightZero() {
        // given
        double weight = 50.0;
        double height = 0.0;
        // when
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
        // then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertAll(
                () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWorstBMI.getWeight())
        );
    }

    @Test
    void should_ReturnNullWorstBMI_When_CoderListIsEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertNull(coderWorstBMI);
    }

    @Test
    void should_ThrowArithmeticException_When_CoderListContainCoderWithHeightZero() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(0.0, 98.0));
        coders.add(new Coder(1.82, 64.7));

        // when
        Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));
        double[] expected = {18.52, 29.59, 19.53};

        // when
        double[] bmiScore = BMICalculator.getBMIScores(coders);

        // then
        assertArrayEquals(expected, bmiScore);
    }

    @Test
    void should_ReturnEmptyBMIScoreArray_When_CoderListIsEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        double[] expected = {};

        // when
        double[] bmiScore = BMICalculator.getBMIScores(coders);

        // then
        assertArrayEquals(expected, bmiScore);
    }
}