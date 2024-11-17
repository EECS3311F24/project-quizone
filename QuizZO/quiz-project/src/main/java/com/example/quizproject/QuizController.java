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
 * The QuizController class provides REST endpoints for managing quizzes,
 * including pausing and resuming quiz attempts.
 */
@RestController
public class QuizController {

    private static final String FILE_PATH = Paths.get(System.getProperty("user.dir"), "quizzes.json").toString();
    private static final String ARCHIVE_FILE_PATH = Paths.get(System.getProperty("user.dir"), "quiz-history.json").toString();

    /**
     * Retrieves the list of current quizzes from the quizzes.json file.
     *
     * @return A list of Quiz objects, or an empty list if the file does not exist or is empty.
     */
    @GetMapping("/get-quizzes")
    public List<Quiz> getQuizzes() {
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
                if (jsonData.length == 0) return new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
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

    /**
     * Retrieves the list of archived quizzes from quiz-history.json.
     *
     * @return A list of archived Quiz objects, or an empty list if the file does not exist or is empty.
     */
    @GetMapping("/get-quiz-history")
    public List<Quiz> getQuizHistory() {
        try {
            if (Files.exists(Paths.get(ARCHIVE_FILE_PATH))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(ARCHIVE_FILE_PATH));
                if (jsonData.length == 0) return new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
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

    /**
     * Saves a new quiz to the quizzes.json file.
     *
     * @param quiz The Quiz object to save.
     * @return A success or failure message.
     */
    @PostMapping("/save-quiz")
    public String saveQuiz(@RequestBody Quiz quiz) {
        try {
            List<Quiz> quizList = new ArrayList<>();
            quizList.add(quiz);
            saveQuizzesToFile(quizList);
            return "Quiz saved successfully!";
        } catch (Exception e) {
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
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
                ObjectMapper mapper = new ObjectMapper();

                if (jsonData.length == 0) {
                    return "No active quizzes to archive.";
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

                // Write updated archive back to file
                try (FileWriter archiveWriter = new FileWriter(ARCHIVE_FILE_PATH)) {
                    mapper.writeValue(archiveWriter, archivedQuizzes);
                }

                // Clear the active quizzes file
                try (FileWriter writer = new FileWriter(FILE_PATH)) {
                    writer.write("[]");
                }

                return "Quiz archived and cleared successfully!";
            } else {
                return "No active quizzes to archive.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to archive and clear quiz.";
        }
    }

    /**
     * Deletes all quiz history data from quiz-history.json by writing an empty array.
     *
     * @return A success or failure message.
     */
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

    /**
     * Pauses the quiz by setting the paused state and remaining time.
     *
     * @param quizTitle The title of the quiz to pause.
     * @param remainingTime The remaining time for the quiz in seconds.
     * @return A success or failure message.
     */
    @PostMapping("/pause-quiz/{quizTitle}")
    public String pauseQuiz(@PathVariable String quizTitle, @RequestParam Integer remainingTime) {
        try {
            List<Quiz> quizzes = getQuizzes();
            for (Quiz quiz : quizzes) {
                if (quiz.getTitle().equalsIgnoreCase(quizTitle)) {
                    quiz.setPaused(true);
                    quiz.setRemainingTime(remainingTime);
                    saveQuizzesToFile(quizzes);
                    return "Quiz paused successfully!";
                }
            }
            return "Quiz not found.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to pause the quiz.";
        }
    }

    /**
     * Resumes a paused quiz by clearing the paused state.
     *
     * @param quizTitle The title of the quiz to resume.
     * @return A success or failure message.
     */
    @PostMapping("/resume-quiz/{quizTitle}")
    public String resumeQuiz(@PathVariable String quizTitle) {
        try {
            List<Quiz> quizzes = getQuizzes();
            for (Quiz quiz : quizzes) {
                if (quiz.getTitle().equalsIgnoreCase(quizTitle)) {
                    if (quiz.isPaused()) {
                        quiz.setPaused(false);
                        saveQuizzesToFile(quizzes);
                        return "Quiz resumed successfully!";
                    } else {
                        return "Quiz is not paused.";
                    }
                }
            }
            return "Quiz not found.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to resume the quiz.";
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

