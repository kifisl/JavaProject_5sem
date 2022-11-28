async function register() {
    let registerObj = {
        login: document.getElementsByClassName("login-input")[0].value,
        password: document.getElementsByClassName("password-input")[0].value,
        email: document.getElementsByClassName("email-input")[0].value
    }

    const register = await fetch("http://localhost:3000/register", {
        method: 'POST',
        body: JSON.stringify(registerObj),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if(!register.ok) {
        const error = await register.json();
        alert(error.errorMessage);
    }
    else {
        alert("Please, go to the email and activate your account")
        location.assign('https://temp-mail.org/ru/');
    }
}