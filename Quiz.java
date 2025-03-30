package com.example.quizproject;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Quiz class represents a quiz with customizable settings, including a timer and randomization.
 */
public class Quiz {

    // The time limit for completing the quiz, in minutes.
    @JsonProperty("timer")
    private Integer timer;

    // A flag indicating if questions should be presented in random order.
    @JsonProperty("randomize")
    private Boolean randomize;

    /**
     * Gets the time limit for the quiz.
     *
     * @return The timer value in minutes.
     */
    public Integer getTimer() {
        return timer;
    }

    /**
     * Sets the time limit for the quiz.
     *
     * @param timer The timer value to set, in minutes.
     */
    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    /**
     * Gets whether the questions should be randomized.
     *
     * @return True if questions should be randomized; false otherwise.
     */
    public Boolean getRandomize() {
        return randomize;
    }

    /**
     * Sets the flag for randomizing the order of questions.
     *
     * @param randomize True to randomize questions; false to keep the original order.
     */
    public void setRandomize(Boolean randomize) {
        this.randomize = randomize;
    }
}
