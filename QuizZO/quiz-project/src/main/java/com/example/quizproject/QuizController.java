package com.example.quizproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The QuizController class provides REST endpoints for managing quizzes.
 */
@RestController
public class QuizController {

    private static final String FILE_PATH = Paths.get(System.getProperty("user.dir"), "quizzes.json").toString();
    private static final String ARCHIVE_FILE_PATH = Paths.get(System.getProperty("user.dir"), "quiz-history.json").toString();

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @GetMapping("/get-quizzes")
    public List<Quiz> getQuizzes() {
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
                if (jsonData.length == 0) {
                    return new ArrayList<>();
                }
                ObjectMapper mapper = getObjectMapper();
                Quiz[] quizzes = mapper.readValue(jsonData, Quiz[].class);
                return Arrays.asList(quizzes);
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @PostMapping("/save-quiz")
    public String saveQuiz(@RequestBody Quiz quiz) {
        try {
            // Ensure difficulty is not null Ticket 2 Task 2 SP3
            if (quiz.getDifficulty() == null || quiz.getDifficulty().isEmpty()) {
                quiz.setDifficulty("Not Specified"); // Default value for difficulty
            }
            if (quiz.getDeadline() != null && quiz.getDeadline().isBefore(LocalDateTime.now())) {
                return "Failed to save the quiz: Deadline must be in the future.";
            }

            // Clear existing quizzes
            List<Quiz> quizList = new ArrayList<>();
            quizList.add(quiz);

            // Save only the new quiz
            saveQuizzesToFile(quizList);

            return "Quiz saved successfully with deadline: " + (quiz.getDeadline() != null ? quiz.getDeadline() : "None");
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to save the quiz.";
        }
    }


    @PostMapping("/archive-and-clear-quiz")
    public String archiveAndClearQuiz() {
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
                ObjectMapper mapper = getObjectMapper();

                if (jsonData.length == 0) {
                    return "No active quiz to archive.";
                }

                Quiz[] currentQuizzes = mapper.readValue(jsonData, Quiz[].class);

                List<Quiz> archivedQuizzes = new ArrayList<>();
                if (Files.exists(Paths.get(ARCHIVE_FILE_PATH))) {
                    byte[] archiveData = Files.readAllBytes(Paths.get(ARCHIVE_FILE_PATH));
                    if (archiveData.length > 0) {
                        Quiz[] existingArchive = mapper.readValue(archiveData, Quiz[].class);
                        archivedQuizzes.addAll(Arrays.asList(existingArchive));
                    }
                }

                archivedQuizzes.addAll(Arrays.asList(currentQuizzes));

                try (FileWriter archiveWriter = new FileWriter(ARCHIVE_FILE_PATH)) {
                    mapper.writeValue(archiveWriter, archivedQuizzes);
                }

                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    writer.write("[]");
                }

                return "Quiz archived and cleared successfully!";
            } else {
                return "No active quiz to archive.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to archive and clear quiz.";
        }
    }

    @GetMapping("/get-quiz-history")
    public List<Quiz> getQuizHistory() {
        try {
            if (Files.exists(Paths.get(ARCHIVE_FILE_PATH))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(ARCHIVE_FILE_PATH));
                if (jsonData.length == 0) {
                    return new ArrayList<>();
                }
                ObjectMapper mapper = getObjectMapper();
                Quiz[] archivedQuizzes = mapper.readValue(jsonData, Quiz[].class);
                return Arrays.asList(archivedQuizzes);
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @DeleteMapping("/delete-quiz-history")
    public String deleteQuizHistory() {
        try (FileWriter writer = new FileWriter(ARCHIVE_FILE_PATH)) {
            writer.write("[]");
            return "Quiz history deleted successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to delete quiz history.";
        }
    }

    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestBody List<String> submittedAnswers) {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return "No active quiz to submit.";
            }

            // Read the current quiz
            byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
            ObjectMapper mapper = getObjectMapper();
            Quiz[] quizzes = mapper.readValue(jsonData, Quiz[].class);

            if (quizzes.length == 0) {
                return "No active quiz to submit.";
            }

            Quiz quiz = quizzes[0]; // Assuming only one active quiz at a time
            List<Question> questions = quiz.getQuestions();
            int correctCount = 0;
            StringBuilder feedback = new StringBuilder();

            // Grade the quiz
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                String correctAnswer = question.getCorrectAnswer();
                String userAnswer = i < submittedAnswers.size() ? submittedAnswers.get(i) : null;

                if (correctAnswer.equals(userAnswer)) {
                    correctCount++;
                    feedback.append("Question ").append(i + 1).append(": Correct\n");
                } else {
                    feedback.append("Question ").append(i + 1).append(": Incorrect (Correct Answer: ").append(correctAnswer).append(")\n");
                }
            }

            // Calculate score
            int totalQuestions = questions.size();
            double score = ((double) correctCount / totalQuestions) * 100;

            feedback.append("Score: ").append((int) score).append("%\n");

            // Archive the quiz with grade
            List<Quiz> archivedQuizzes = new ArrayList<>();
            if (Files.exists(Paths.get(ARCHIVE_FILE_PATH))) {
                byte[] archiveData = Files.readAllBytes(Paths.get(ARCHIVE_FILE_PATH));
                if (archiveData.length > 0) {
                    Quiz[] existingArchive = mapper.readValue(archiveData, Quiz[].class);
                    archivedQuizzes.addAll(Arrays.asList(existingArchive));
                }
            }

            // Attach the grade to the quiz
            quiz.setDescription(quiz.getDescription() + "\nScore Received: " + (int) score + "%");
            archivedQuizzes.add(quiz);

            try (FileWriter archiveWriter = new FileWriter(ARCHIVE_FILE_PATH)) {
                mapper.writeValue(archiveWriter, archivedQuizzes);
            }

            // Clear the active quiz
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write("[]");
            }

            return feedback.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to submit the quiz.";
        }
    }

    private void saveQuizzesToFile(List<Quiz> quizzes) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            ObjectMapper mapper = getObjectMapper();
            mapper.writeValue(writer, quizzes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
