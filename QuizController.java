public void saveQuiz(Quiz quiz) {
    // Serialization logic to save quiz, including timer and randomizeQuestions fields
    try (FileWriter file = new FileWriter("quizzes.json")) {
        Gson gson = new Gson();
        file.write(gson.toJson(quiz));
        file.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
