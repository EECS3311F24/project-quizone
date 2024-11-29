package com.example.quizproject;

import java.util.List;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Quiz class represents a quiz, containing a title, description, a list of questions,
 * a timer for time limits, a flag for randomizing question order, and a deadline.
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

    // The deadline for the quiz.
    @JsonProperty("deadline")
    private LocalDateTime deadline; // Deadline for quiz completion

    //  Difficulty Field Ticket 2 Task 1 SP3
    @JsonProperty("difficulty")
    private String difficulty;
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

    /**
     * Gets the deadline for the quiz.
     *
     * @return The deadline as a LocalDateTime object.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Sets the deadline for the quiz.
     *
     * @param deadline The deadline to set.
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }


    // **Getters and Setters for Difficulty** Ticket 2 Task 1 SP3
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
