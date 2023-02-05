package com.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCalculatorTest {

    @Test
    void should_ReturnBad_When_AvgBelow20() {
        // given
        int weeklyCardioMin = 40;
        int weeklyWorkouts = 1;

        // when
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkouts);

        // then
        assertEquals("bad", actual);
    }

    @Test
    void should_ReturnAverage_When_AvgBetween20And40() {
        // given
        int weeklyCardioMin = 40;
        int weeklyWorkouts = 3;

        // when
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkouts);

        // then
        assertEquals("average", actual);
    }

    @Test
    void should_ReturnGood_When_AvgAbove40() {
        // given
        int weeklyCardioMin = 40;
        int weeklyWorkouts = 7;

        // when
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkouts);

        // then
        assertEquals("good", actual);
    }

    @ParameterizedTest(name = "weeklyCardioMin={0}, weeklyWorkouts={1}")
    @CsvSource(value = {"-40, 7", "40, -7"})
    void should_ThrowException_When_InputBelowZero(int weeklyCardioMin, int weeklyWorkouts) {
        // given: weeklyCardioMin, weeklyWorkouts
        // when
        Executable executable = () -> ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkouts);

        // then
        assertThrows(RuntimeException.class, executable);
    }
}