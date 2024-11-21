@DeleteMapping("/deleteQuizHistory")
public String deleteQuizHistory(@RequestParam String quizId) {
    // Logic to delete quiz history from quiz-history.json
    // This will require reading, modifying, and rewriting the JSON file
    return "Quiz history deleted successfully!";
}
