async function auth() {
    let authObj = {
        login: document.getElementsByClassName("login-input")[0].value,
        password: document.getElementsByClassName("password-input")[0].value
    }

    const token = await fetch("http://localhost:3000/auth", {
        method: 'POST',
        body: JSON.stringify(authObj),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if(!token.ok) {
        const error = await token.json();
        alert(error.errorMessage);
    }
    else {
        const json = await token.json();
        localStorage.setItem("token", json.token);
        localStorage.setItem("login", authObj.login);
        location.assign("http://localhost:3000/main");
    }
}