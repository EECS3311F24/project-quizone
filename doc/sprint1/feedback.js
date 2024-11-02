// This assumes that quiz data is stored in localStorage and has a similar structure
function loadQuiz() {
    const quiz = JSON.parse(localStorage.getItem('currentQuiz'));
    if (!quiz) {
        alert('No quiz available. Please create one first.');
        return;
    }

    const form = document.getElementById('quizForm');
    quiz.questions.forEach((question, index) => {
        const questionHtml = `
            <div>
                <label>${question.questionText}</label>
                ${question.options.map(option => 
                    `<input type="radio" name="question${index}" value="${option.trim()}">${option}`).join('<br>')}
            </div>
        `;
        form.innerHTML += questionHtml;
    });
}

function submitQuiz() {
    const storedQuiz = JSON.parse(localStorage.getItem('currentQuiz'));
    const resultsContainer = document.getElementById('quizResults');
    resultsContainer.innerHTML = '';  // Clear previous results

    let score = 0;
    storedQuiz.questions.forEach((question, index) => {
        const userAnswer = document.querySelector(`input[name="question${index}"]:checked`)?.value;
        const isCorrect = userAnswer === question.correctAnswer.trim();
        const resultText = isCorrect ? 'Correct' : 'Incorrect';
        score += isCorrect ? 1 : 0;

        const feedbackHTML = `
            <div>
                <p>Question: ${question.questionText}</p>
                <p>Your answer: ${userAnswer || "No answer given"} (${resultText})</p>
                <p>Correct answer: ${question.correctAnswer}</p>
            </div>
        `;
        resultsContainer.innerHTML += feedbackHTML;
    });

    resultsContainer.innerHTML += `<p>Your Score: ${score} / ${storedQuiz.questions.length}</p>`;
}

document.getElementById('submitQuizBtn').addEventListener('click', submitQuiz);

window.onload = loadQuiz;
