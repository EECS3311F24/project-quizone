package com.example.quizproject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
    @JsonProperty("text")
    private String text;

    @JsonProperty("options")
    private String[] options; // Assuming options are stored as an array of strings

    @JsonProperty("correctAnswer")
    private String correctAnswer;

    // Default constructor
    public Question() {}

    // Parameterized constructor (optional, useful for testing)
    public Question(String text, String[] options, String correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
