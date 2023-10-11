
const backwardButton = document.getElementById("backwardButton");

backwardButton.addEventListener('click', ()=>{
    window.history.back();
})

const submitButton = document.getElementById("submitButton");

submitButton.addEventListener('click',()=>{
    document.getElementById('review-form').submit();
})

