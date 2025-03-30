@PostMapping("/submitQuiz")
public String submitQuiz(@RequestParam String title, @RequestParam String description, @RequestParam List<String> questions) {
    Quiz newQuiz = new Quiz(title, description, questions);
    saveQuiz(newQuiz);
    return "Quiz successfully created!";
}
