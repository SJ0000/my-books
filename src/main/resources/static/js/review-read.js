const reviewId = document.getElementById('dataContainer').getAttribute('value');

const editButton = document.getElementById("editButton");
editButton.addEventListener('click', () => {
    window.location.href = "/review/edit?id=" + reviewId;
})



const removeConfirmButton = document.getElementById("removeConfirmButton");
removeConfirmButton.addEventListener('click', () => {
    fetch('/reviews/'+reviewId, {method: 'DELETE'})
        .then(() => {
            window.location.href = "/reviews";
        })
})

