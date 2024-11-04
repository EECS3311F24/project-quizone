QuizOne
QuizOne is a customizable quiz app designed specifically for tech students and computer science enthusiasts. It empowers users to create their own flashcards and quizzes, offering a unique alternative to platforms like NeetCode and LeetCode. QuizOne is particularly useful for practicing coding problems and reinforcing knowledge from computer science courses.

Features
Create Custom Flashcards: Users can build their own flashcards for any topic, from LeetCode-style questions to specific concepts covered in computer science classes.
Tailored for CS Students: Focuses on the needs of tech students, allowing them to practice coding challenges without predefined solutions.
User-Generated Content: Unlike platforms that provide pre-made problems and solutions, QuizOne puts the power in the hands of the users by letting them create and manage their problem sets.

### Prerequisites
- **Java 11** or higher installed. Verify by running `java -version`.
- **Maven** installed for building and running the project.
- Ensure the `quizzes.json` and `quiz-history.json` files are present and have read/write permissions.

### Running the Project
1. **Clone the repository**: Run `git clone https://github.com/EECS3311F24/project-quizone.git` and navigate to `cd project-quizone/doc/sprint1`.
2. **Build the project using Maven**: Run `mvn clean install`.
3. **Run the Spring Boot application**: Run `mvn spring-boot:run`.
4. **Access the application**: Visit `http://localhost:8080/form-creation.html` in your web browser to view and interact with quizzes.

### Features Included in Sprint 1
- **Quiz Creation**: Create quizzes with a title, description, and questions.
- **Modes**: Switch between flashcard and quiz modes.
- **Timer Functionality**: Customizable quiz timers.
- **Feedback**: View instant feedback after quiz attempts.
- **Flag Questions**: Flag questions for review during a quiz.
- **Quiz History**: View and manage previous quiz attempts.

### Troubleshooting
- **Java Issues**: Ensure Java is correctly installed and set up in your `PATH`.
- **Permissions**: Verify that `quizzes.json` and `quiz-history.json` are not read-only.
- **Port Conflicts**: Confirm that `http://localhost:8080` is not occupied by another application.

## License
This project is licensed under the MIT License.

