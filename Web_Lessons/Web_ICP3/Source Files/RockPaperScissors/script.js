/* JavaScript for Rock Paper Scissors */

/* Variable/Elements assigned */
let userOption = document.getElementById("userOption");
let winnerText = document.getElementById("winnerText");
let button = document.getElementsByClassName("btn");
let cpuImages = document.getElementsByClassName("cpuImgs");
let playAgainButton = document.getElementById("playAgain");
let startButton = document.getElementById("startBtn");

let gameOver = false;
let boolStart = true;

/* Onclick functions for Star and Play Again buttons */
startButton.onclick = function() {startBtnOff(false)};
playAgainButton.onclick = function () {reset(gameOver)};

/* Turns off Start Button on click*/
function startBtnOff(boolST) {
    boolStart = boolST;
    startButton.style.display = "none";
}

/* Resets the game to Play Again */
function reset(boolPA)
{
    if (boolPA === true) {
        gameOver = false
        userOption.style.display = "block"
        playAgainButton.style.display = "none";
        winnerText.innerHTML = "";
        cpuImages[0].style.border = "0";
        cpuImages[1].style.border = "0";
        cpuImages[2].style.border = "0";
    }
}

/* RNG for Computer, Conditional Statements/Logic for Rock Paper Scissors */
function RockPaperScissorsGame(btnNum) {
    if (gameOver === false && boolStart === false) {
        const cpuNum = Math.floor(Math.random() * 3);
        cpuImages[cpuNum].style.border = "10px solid #FF1789";

        if ((btnNum === 0 && cpuNum === 1) || (btnNum === 1 && cpuNum === 2) || (btnNum === 2 && cpuNum === 0))
        {
            winnerText.innerHTML = "CPU Wins";
        }

        else if ((btnNum === 1 && cpuNum === 0) || (btnNum === 2 && cpuNum === 1) || (btnNum === 0 && cpuNum === 2))
        {
            winnerText.innerHTML = "You Win";
        }

        else
        {
            winnerText.innerHTML = "Draw";
        }

        gameOver = true;
        userOption.style.display = "none";
        playAgainButton.style.display = "inline-block";
    }
}

/* Add Event Listeners for Rock Paper Scissors Buttons */
for (let i = 0; i < button.length; i++) {
    button[i].addEventListener('click', function () {
        if (button[i].id === "rockUser" && gameOver === false)
        {
            RockPaperScissorsGame(0);
        }

        else if (button[i].id === "paperUser" && gameOver === false)
        {
            RockPaperScissorsGame(1);
        }

        else if (button[i].id === "scissorsUser" && gameOver === false)
        {
            RockPaperScissorsGame(2);
        }
    });
}