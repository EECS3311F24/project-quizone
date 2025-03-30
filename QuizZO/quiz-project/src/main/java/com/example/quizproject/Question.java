package com.example.quizproject;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Question class represents a quiz question, including the question text, available answer options,
 * and the correct answer. This class is annotated for JSON serialization/deserialization.
 */
public class Question {

    // The text of the question.
    @JsonProperty("text")
    private String text;

    // The available options for the question, stored as an array of strings.
    @JsonProperty("options")
    private String[] options; // Assuming options are stored as an array of strings

    // The correct answer for the question.
    @JsonProperty("correctAnswer")
    private String correctAnswer;

    /**
     * Default constructor for the Question class.
     * This is needed for deserialization and other general uses.
     */
    public Question() {}

    /**
     * Parameterized constructor for creating a Question with initial values.
     *
     * @param text The text of the question.
     * @param options The array of answer options for the question.
     * @param correctAnswer The correct answer for the question.
     */
    public Question(String text, String[] options, String correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the text of the question.
     *
     * @return The text of the question.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the question.
     *
     * @param text The text to set for the question.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the available options for the question.
     *
     * @return An array of strings representing the options.
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Sets the available options for the question.
     *
     * @param options The array of answer options to set.
     */
    public void setOptions(String[] options) {
        this.options = options;
    }

    /**
     * Gets the correct answer for the question.
     *
     * @return The correct answer as a string.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the correct answer for the question.
     *
     * @param correctAnswer The correct answer to set.
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
