package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {
    private final String environment = "prod";

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests.");
    }

    @Nested
    class IsDietRecommendedTests {

        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
            // given
            double weight = coderWeight;
            double height = coderHeight;

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
    }

    @Nested
    @DisplayName("Find Coder With Worst BMI Tests")
    class FindCoderWithWorstBMITests {

        @Test
        @DisplayName("Should Return Coder With Worst BMI When Coder List Not Empty")
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

        @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
        void should_ReturnCoderWithWorstBMIIn500Ms_When_CoderListHas10000Elements() {
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));

            // given
            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10_000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }

            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertTimeout(Duration.ofMillis(500), executable);
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
    }

    @Nested
    class GetBMIScoresTests {

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
}