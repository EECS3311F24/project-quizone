package com.example.quizproject;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Quiz class represents a quiz, containing a title, description, a list of questions,
 * a timer for time limits, and a flag for randomizing question order.
 */
public class Quiz {

    // The title of the quiz.
    @JsonProperty("title")
    private String title;

    // A brief description of the quiz.
    @JsonProperty("description")
    private String description;

    // A list of questions included in the quiz.
    @JsonProperty("questions")
    private List<Question> questions;

    // The time limit for completing the quiz, measured in minutes.
    @JsonProperty("timer")
    private Integer timer;

    // A flag indicating whether the questions should be presented in a randomized order.
    @JsonProperty("randomize")
    private Boolean randomize;

    // Tracks if the quiz is paused.
    @JsonProperty("paused")
    private boolean paused;

    // Tracks the remaining time for the quiz.
    @JsonProperty("remainingTime")
    private Integer remainingTime;

    /**
     * Default constructor for the Quiz class.
     * Useful for deserialization and general instantiation.
     */
    public Quiz() {}

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public Boolean getRandomize() {
        return randomize;
    }

    public void setRandomize(Boolean randomize) {
        this.randomize = randomize;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }
}
