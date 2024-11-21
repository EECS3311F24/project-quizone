@GetMapping("/getQuizHistory")
public List<Quiz> getQuizHistory() {
    // Logic to read quiz history from quiz-history.json and return it
    return quizHistoryList;
}
