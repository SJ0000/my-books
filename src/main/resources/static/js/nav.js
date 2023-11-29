const logoutButton = document.getElementById("logoutButton");

logoutButton.addEventListener('click', () => {
    fetch('/logout', {method: 'POST'})
        .then(() => {
            window.location.href = '/'
        })
})