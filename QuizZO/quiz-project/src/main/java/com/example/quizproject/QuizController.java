package com.example.quizproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The QuizController class provides REST endpoints for managing quizzes.
 * It allows users to retrieve, save, archive, clear, and delete quiz data.
 */
@RestController
public class QuizController {

    // Path to the JSON file where current quizzes are stored.
    private static final String FILE_PATH = Paths.get(System.getProperty("user.dir"), "quizzes.json").toString();

    // Path to the JSON file where archived quizzes are stored.
    private static final String ARCHIVE_FILE_PATH = Paths.get(System.getProperty("user.dir"), "quiz-history.json").toString();

    /**
     * Retrieves the list of current quizzes from the quizzes.json file.
     *
     * @return A list of Quiz objects, or an empty list if the file does not exist or is empty.
     */
    @GetMapping("/get-quizzes")
    public List<Quiz> getQuizzes() {
        System.out.println("getQuizzes() called.");
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
                if (jsonData.length == 0) {
                    System.out.println("quizzes.json is empty.");
                    return new ArrayList<>();
                }
                ObjectMapper mapper = new ObjectMapper();
                Quiz[] quizzes = mapper.readValue(jsonData, Quiz[].class);
                return Arrays.asList(quizzes);
            } else {
                System.out.println("quizzes.json does not exist.");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("IOException occurred in getQuizzes().");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves a new quiz to the quizzes.json file, overwriting any existing data.
     *
     * @param quiz The Quiz object to save.
     * @return A success or failure message.
     */
    @PostMapping("/save-quiz")
    public String saveQuiz(@RequestBody Quiz quiz) {
        System.out.println("saveQuiz() called with quiz: " + quiz.getTitle());
        try {
            // Overwrite quizzes.json with the new quiz
            List<Quiz> quizList = new ArrayList<>();
            quizList.add(quiz);
            saveQuizzesToFile(quizList);
            return "Quiz saved successfully!";
        } catch (Exception e) {
            System.out.println("Exception occurred in saveQuiz().");
            e.printStackTrace();
            return "Failed to save the quiz.";
        }
    }

    /**
     * Archives the current quizzes and clears the quizzes.json file.
     * Moves the data to quiz-history.json for record-keeping.
     *
     * @return A success or failure message.
     */
    @PostMapping("/archive-and-clear-quiz")
    public String archiveAndClearQuiz() {
        System.out.println("archiveAndClearQuiz() method called.");

        try {
            System.out.println("Quizzes file path: " + FILE_PATH);
            System.out.println("Archive file path: " + ARCHIVE_FILE_PATH);

            if (Files.exists(Paths.get(FILE_PATH))) {
                System.out.println("quizzes.json exists. Proceeding to archive.");

                // Read current quizzes
                byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
                ObjectMapper mapper = new ObjectMapper();

                // Check if quizzes.json is empty
                if (jsonData.length == 0) {
                    System.out.println("quizzes.json is empty. Nothing to archive.");
                    return "No active quiz to archive.";
                }

                Quiz[] currentQuizzes = mapper.readValue(jsonData, Quiz[].class);
                System.out.println("Current quizzes read successfully.");

                // Read existing archive if it exists
                List<Quiz> archivedQuizzes = new ArrayList<>();
                if (Files.exists(Paths.get(ARCHIVE_FILE_PATH))) {
                    System.out.println("quiz-history.json exists. Reading existing archive.");
                    byte[] archiveData = Files.readAllBytes(Paths.get(ARCHIVE_FILE_PATH));

                    if (archiveData.length > 0) {
                        Quiz[] existingArchive = mapper.readValue(archiveData, Quiz[].class);
                        archivedQuizzes.addAll(Arrays.asList(existingArchive));
                        System.out.println("Existing archive loaded successfully.");
                    } else {
                        System.out.println("quiz-history.json is empty.");
                    }
                } else {
                    System.out.println("quiz-history.json does not exist. It will be created.");
                }

                // Add current quizzes to the archive
                archivedQuizzes.addAll(Arrays.asList(currentQuizzes));
                System.out.println("Current quizzes added to archive.");

                // Save the updated archive
                try (FileWriter archiveWriter = new FileWriter(ARCHIVE_FILE_PATH)) {
                    mapper.writeValue(archiveWriter, archivedQuizzes);
                    System.out.println("Archive updated successfully.");
                }

                // Clear the current quizzes file
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    writer.write("[]");
                    System.out.println("quizzes.json cleared.");
                }

                return "Quiz archived and cleared successfully!";
            } else {
                System.out.println("quizzes.json does not exist. No active quiz to archive.");
                return "No active quiz to archive.";
            }
        } catch (IOException e) {
            System.out.println("IOException occurred during archiving.");
            e.printStackTrace();
            return "Failed to archive and clear quiz.";
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during archiving.");
            e.printStackTrace();
            return "An unexpected error occurred.";
        }
    }

    /**
     * Retrieves the list of archived quizzes from quiz-history.json.
     *
     * @return A list of archived Quiz objects, or an empty list if the file does not exist or is empty.
     */
    @GetMapping("/get-quiz-history")
    public List<Quiz> getQuizHistory() {
        System.out.println("getQuizHistory() called.");
        try {
            if (Files.exists(Paths.get(ARCHIVE_FILE_PATH))) {
                System.out.println("quiz-history.json exists. Reading data.");
                byte[] jsonData = Files.readAllBytes(Paths.get(ARCHIVE_FILE_PATH));

                if (jsonData.length == 0) {
                    System.out.println("quiz-history.json is empty.");
                    return new ArrayList<>();
                }

                ObjectMapper mapper = new ObjectMapper();
                Quiz[] archivedQuizzes = mapper.readValue(jsonData, Quiz[].class);
                System.out.println("Archived quizzes loaded successfully.");
                return Arrays.asList(archivedQuizzes);
            } else {
                System.out.println("quiz-history.json does not exist.");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while reading quiz history.");
            e.printStackTrace();
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while reading quiz history.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Deletes all quiz history data from quiz-history.json by writing an empty array.
     *
     * @return A success or failure message.
     */
    @DeleteMapping("/delete-quiz-history")
    public String deleteQuizHistory() {
        System.out.println("deleteQuizHistory() called.");
        try (FileWriter writer = new FileWriter(ARCHIVE_FILE_PATH)) {
            writer.write("[]");
            System.out.println("quiz-history.json cleared.");
            return "Quiz history deleted successfully!";
        } catch (IOException e) {
            System.out.println("IOException occurred while deleting quiz history.");
            e.printStackTrace();
            return "Failed to delete quiz history.";
        }
    }

    /**
     * Saves the list of quizzes to the quizzes.json file.
     *
     * @param quizzes The list of Quiz objects to save.
     */
    private void saveQuizzesToFile(List<Quiz> quizzes) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, quizzes);
            System.out.println("Quizzes saved to file successfully.");
        } catch (IOException e) {
            System.out.println("IOException occurred while saving quizzes.");
            e.printStackTrace();
        }
    }

    /**
     * Submits the quiz results, including the score, archives the quiz, and clears the current quiz.
     *
     * @param quiz The Quiz object with updated score.
     * @return A success or failure message.
     */
    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestBody Quiz quiz) {
        System.out.println("submitQuiz() called with quiz: " + quiz.getTitle() + " and score: " + quiz.getScore());
        try {
            // Archive the quiz with the score
            archiveQuizWithScore(quiz);

            // Clear the current quizzes
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write("[]");
                System.out.println("quizzes.json cleared after submission.");
            }

            return "Quiz submitted and archived successfully!";
        } catch (Exception e) {
            System.out.println("Exception occurred in submitQuiz().");
            e.printStackTrace();
            return "Failed to submit the quiz.";
        }
    }
    /**
     * Archives a quiz with the score to the quiz-history.json file.
     *
     * @param quiz The Quiz object with the score to archive.
     */
    private void archiveQuizWithScore(Quiz quiz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Quiz> archivedQuizzes = new ArrayList<>();

            // Read existing archive if it exists
            if (Files.exists(Paths.get(ARCHIVE_FILE_PATH))) {
                System.out.println("quiz-history.json exists. Reading existing archive.");
                byte[] archiveData = Files.readAllBytes(Paths.get(ARCHIVE_FILE_PATH));

                if (archiveData.length > 0) {
                    Quiz[] existingArchive = mapper.readValue(archiveData, Quiz[].class);
                    archivedQuizzes.addAll(Arrays.asList(existingArchive));
                    System.out.println("Existing archive loaded successfully.");
                } else {
                    System.out.println("quiz-history.json is empty.");
                }
            } else {
                System.out.println("quiz-history.json does not exist. It will be created.");
            }

            // Add the new quiz with score to the archive
            archivedQuizzes.add(quiz);
            System.out.println("Quiz with score added to archive.");

            // Save the updated archive
            try (FileWriter archiveWriter = new FileWriter(ARCHIVE_FILE_PATH)) {
                mapper.writeValue(archiveWriter, archivedQuizzes);
                System.out.println("Archive updated successfully with new quiz.");
            }
        } catch (IOException e) {
            System.out.println("IOException occurred while archiving quiz with score.");
            e.printStackTrace();
        }
    }
}









