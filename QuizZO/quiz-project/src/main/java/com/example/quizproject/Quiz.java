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
    private List<Question> questions; // Assuming you have a Question class for each quiz question

    // The time limit for completing the quiz, measured in minutes.
    @JsonProperty("timer")
    private Integer timer; // Time limit in minutes

    // A flag indicating whether the questions should be presented in a randomized order.
    @JsonProperty("randomize")
    private Boolean randomize; // Whether to randomize questions

    /**
     * Default constructor for the Quiz class.
     * Useful for deserialization and general instantiation.
     */
    public Quiz() {}

    /**
     * Gets the title of the quiz.
     *
     * @return The title as a string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the quiz.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the quiz.
     *
     * @return The description as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the quiz.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of questions in the quiz.
     *
     * @return A list of Question objects.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions in the quiz.
     *
     * @param questions The list of questions to set.
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

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
     * @param timer The timer value to set in minutes.
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

    private Integer score;
    /**
     * Gets the score received on the quiz.
     *
     * @return The score as an Integer.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Sets the score received on the quiz.
     *
     * @param score The score to set.
     */
    public void setScore(Integer score) {
        this.score = score;
    }
}
