public Quiz retrieveQuiz(String quizId) {
    // Logic to read from quizzes.json and populate quiz object with timer and randomization
    try (Reader reader = new FileReader("quizzes.json")) {
        Gson gson = new Gson();
        Quiz quiz = gson.fromJson(reader, Quiz.class);
        return quiz;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
