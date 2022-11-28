var photoLink = null;
var checkAdm = null;

document.addEventListener('DOMContentLoaded',  async () => {
    const checkAdmin = await fetch("http://localhost:3000/api/admin", {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    checkAdm = checkAdmin.ok;
    if(!checkAdmin.ok) document.getElementsByClassName("admin-panel")[0].classList.add("visibility-hidden");
});

window.onload = async () => {
    if(localStorage.getItem("token") == null) {
        location.assign("http://localhost:3000/auth");
        alert("Please, log in");
    }

    const getEvents = await fetch("http://localhost:3000/api/events", {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const events = await getEvents.json();
    generateEvents(events);

    document.getElementsByClassName("events")[0].onclick = function(event) {
        let div = event.target;
        if (div.className == 'event-id' || div.className == 'event-name') generateEventsDescription(div.parentNode);
        else if (div.className == 'event') generateEventsDescription(div);
        else return;
    };

    // let input = document.getElementsByClassName("input-file")[0];
    // input.onchange = e => {
    //     let file = e.target.files[0];
    //     photoLink = file.name;
    // }
    // input.click();
    // const file_attach = document.getElementById('file_attach');
    // photoLink = file_attach.files[0];
    // file_attach.addEventListener("change", async () => {
    //     const fData = new FormData();
    //     fData.append("file_attach", file_attach.files[0]);
    //
    //     let fetchResponse = await fetch("/upload", {
    //         method: "POST",
    //         body: fData
    //     });
    //
    //     return await fetchResponse.text();
    // });
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("login");
    location.assign("http://localhost:3000/auth");
}

function generateEvents(events) {
    let eventsList = document.getElementsByClassName("events")[0];
    for(let i = 0; i < events.length; i++) {
        let event = document.createElement("div");
        event.classList.add("event");

        let idEvent = document.createElement("div");
        idEvent.classList.add("event-id");
        idEvent.innerText = `Event: ${events[i].id}`;

        let nameEvent = document.createElement("div");
        nameEvent.classList.add("event-name");
        nameEvent.innerText = `Name: ${events[i].name}`;

        event.appendChild(idEvent);
        event.appendChild(nameEvent);

        eventsList.appendChild(event);
    }
}

var selectedDiv;

async function generateEventsDescription(div) {
    if (selectedDiv) {
        selectedDiv.classList.remove('highlight');
    }
    selectedDiv = div;
    selectedDiv.classList.add('highlight');

    let eventId = Number(selectedDiv.firstChild.innerText.substr(7));

    const getEvent = await fetch(`http://localhost:3000/api/events/${eventId}`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const event = await getEvent.json();

    document.getElementsByClassName("events-description")[0].classList.remove("visibility-hidden");
    document.getElementsByClassName("title")[0].innerText = event.name;
    document.getElementsByClassName("description")[0].innerHTML = event.description + "<br/><br/>Athletes: ";
    for(let i = 0; i < event.athletesSet.length; i++) {
        document.getElementsByClassName("description")[0].innerHTML += event.athletesSet[i].name + " ";
    }
    document.getElementsByClassName("date")[0].innerText = `Date: ${event.date}`;
    document.getElementsByClassName("region")[0].innerText = `Region: ${event.region}`;

    const getReviews = await fetch(`http://localhost:3000/api/reviews/event/${eventId}`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const reviews = await getReviews.json();

    let delReviews = document.getElementsByClassName("comment");
    if(delReviews.length > 0) while(delReviews[0]) delReviews[0].remove();

    let commentList = document.getElementsByClassName("comments-list")[0];
    for(let i = 0; i < reviews.length; i++) {
        let comment = document.createElement("div");
        comment.classList.add("comment");

        let login = document.createElement("div");
        login.classList.add("comment-login");
        login.innerText = `${reviews[i].user.login}: `;

        let photo = document.createElement("div");
        photo.classList.add("comment-photo");

        if(reviews[i].user.role.role == "ROLE_USER") {
            if(reviews[i].photoLink == null) photo.innerHTML = `<img src="/images/defaultUser.jpg" width="50px" height="50px"/>`
            else photo.innerHTML = `<img src="/images/${reviews[i].photoLink}" width="50px" height="50px"/>`;
        }
        else {
            if(reviews[i].photoLink == null) photo.innerHTML = `<img src="/images/defaultAdmin.png" width="50px" height="50px"/>`
            else photo.innerHTML = `<img src="/images/${reviews[i].photoLink}" width="50px" height="50px"/>`;
        }

        let text = document.createElement("div");
        text.classList.add("comment-text");
        text.innerText = reviews[i].description;

        comment.appendChild(login);
        comment.appendChild(photo);
        comment.appendChild(text);

        if(checkAdm) {
            comment.innerHTML += `<div class="${reviews[i].id}" onclick='deleteRev(this)'><img class="delete-review-img" src="/images/deleteReview.jpg" width="20px" height="20px"/></div>`
        }

        commentList.appendChild(comment);
    }
}

async function addComment() {
    const getUser = await fetch(`http://localhost:3000/api/users/${localStorage.getItem("login")}`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const user = await getUser.json();

    let idU = user.id;
    let idE = Number(selectedDiv.firstChild.innerText.substr(7));

    const fData = new FormData(document.forms.com);

    let getReview = await fetch(`/upload/event/${idE}/user/${idU}`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: fData
    });

    if(!getReview.ok) {
        let error = await getReview.json();
        alert(error.errorMessage);
    }
    else {
        let review = await getReview.json();

        let commentList = document.getElementsByClassName("comments-list")[0];
        let comment = document.createElement("div");
        comment.classList.add("comment");

        let login = document.createElement("div");
        login.classList.add("comment-login");
        login.innerText = `${review.user.login}: `;

        let photo = document.createElement("div");
        photo.classList.add("comment-photo");

        if (review.user.role.role == "ROLE_USER") {
            if (review.photoLink == null) photo.innerHTML = `<img src="/images/defaultUser.jpg" width="50px" height="50px"/>`
            else photo.innerHTML = `<img src="/images/${review.photoLink}" width="50px" height="50px"/>`;
        } else {
            if (review.photoLink == null) photo.innerHTML = `<img src="/images/defaultAdmin.png" width="50px" height="50px"/>`
            else photo.innerHTML = `<img src="/images/${review.photoLink}" width="50px" height="50px"/>`;
        }

        let text = document.createElement("div");
        text.classList.add("comment-text");
        text.innerText = review.description;

        comment.appendChild(login);
        comment.appendChild(photo);
        comment.appendChild(text);

        if (checkAdm) {
            comment.innerHTML += `<div class="${review.id}" onclick='deleteRev(this)'><img class="delete-review-img" src="/images/deleteReview.jpg" width="20px" height="20px"/></div>`
        }

        commentList.appendChild(comment);

        document.getElementsByClassName("comment-input")[0].value = "";
        document.getElementsByClassName("input-file")[0].value = null;
    }
 }

 async function searchFunction() {
     let ev = document.getElementsByClassName("event");
     while(ev[0]) ev[0].remove();

     let searchText = document.getElementsByClassName("search-input")[0].value;
     let form = new FormData(document.forms.search);

     const getEvents = await fetch("http://localhost:3000/api/events/search", {
         method: 'POST',
         headers: {
             "Authorization": `Bearer ${localStorage.getItem("token")}`
         },
         body: form
     });
     const events = await getEvents.json();
     generateEvents(events);

     document.getElementsByClassName("events-description")[0].classList.add("visibility-hidden");
 }

 async function refreshFunction() {
    let ev = document.getElementsByClassName("event");
    while(ev[0]) ev[0].remove();

     const getEvents = await fetch("http://localhost:3000/api/events", {
         method: 'GET',
         headers: {
             "Authorization": `Bearer ${localStorage.getItem("token")}`
         }
     });
     const events = await getEvents.json();
     generateEvents(events);

     document.getElementsByClassName("search-input")[0].value = "";
     document.getElementsByClassName("events-description")[0].classList.add("visibility-hidden");
}

async function delE() {
    let event = document.getElementsByClassName("highlight")[0];
    if(event == null) alert("Choose event");
    else {
        let idE = Number(event.firstChild.innerText.substr(7));
        const deleteEvent = await fetch(`http://localhost:3000/api/admin/events/${idE}`, {
            method: 'DELETE',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            }
        });
        if(deleteEvent.ok) {
            window.location.reload();
            alert("Deleted");
        }
        else alert("Delete is not success");
    }
}

async function crE() {
    let listAthlete = document.getElementsByClassName("list-athletes")[0];

    const getAthletes = await fetch(`http://localhost:3000/api/athletes`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const athletes = await getAthletes.json();

    for(let i = 0; i < athletes.length; i++) {
        listAthlete.innerHTML += `${athletes[i].name} <input class='athlete-checkbox' type='checkbox' name='atlhete' value='${athletes[i].id}'/><br/>`;
    }

    document.getElementsByClassName("event-popup")[0].classList.remove("display-none");
}

async function addE() {
    let article = document.getElementsByClassName("input-add-article")[0].value;
    let desc = document.getElementsByClassName("input-add-description")[0].value;
    let reg = document.getElementsByClassName("input-add-region")[0].value;
    let dt = document.getElementsByClassName("input-add-date")[0].value;

    let objEvent = {
        name: article,
        description: desc,
        region: reg,
        date: dt
    }

    const addEvent = await fetch(`http://localhost:3000/api/admin/events`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(objEvent)
    });

    if(!addEvent.ok) {
        let error = await addEvent.json();
        alert(error.errorMessage);
    }
    else {
        let ev = await addEvent.json();
        let event = new Array([
            ev
        ]);

        let checkboxAthletes = document.getElementsByClassName("athlete-checkbox");
        let objAthletes = [];
        let count = 0;
        for (let i = 0; i < checkboxAthletes.length; i++) {
            if (checkboxAthletes[i].checked) {
                objAthletes[count] = {
                    idEvent: ev.id,
                    idAthlete: checkboxAthletes[i].value
                }
                count++;
            }
        }

        const addAthletes = await fetch(`http://localhost:3000/api/admin/athletes`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(objAthletes)
        });
        let athletes = await addAthletes.json();

        if (addEvent.ok && addAthletes.ok) {
            alert("Added");
            generateEvents(event);
            window.location.reload();
            nullInput();
        } else alert("Add is not success");
    }
}

function backCE() {
    document.getElementsByClassName("event-popup")[0].classList.add("display-none");
    nullInput();
}

function nullInput() {
    document.getElementsByClassName("list-athletes")[0].innerHTML = "";
    document.getElementsByClassName("input-add-article")[0].value = "";
    document.getElementsByClassName("input-add-description")[0].value = "";
    document.getElementsByClassName("input-add-date")[0].value = null;
    document.getElementsByClassName("input-add-region")[0].value = "";
}

async function chE() {
    let event = document.getElementsByClassName("highlight")[0];
    if(event == null) alert("Choose event");
    else {
        let listAthlete = document.getElementsByClassName("list-athletes")[1];

        const getAthletes = await fetch(`http://localhost:3000/api/athletes`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            }
        });
        const athletes = await getAthletes.json();

        let eventId = Number(selectedDiv.firstChild.innerText.substr(7));

        const getEvent = await fetch(`http://localhost:3000/api/events/${eventId}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            }
        });
        const event = await getEvent.json();

        document.getElementsByClassName("list-athletes")[1].innerHTML = "";
        document.getElementsByClassName("input-add-article")[1].value = event.name;
        document.getElementsByClassName("input-add-description")[1].value = event.description;
        document.getElementsByClassName("input-add-date")[1].value = event.date;
        document.getElementsByClassName("input-add-region")[1].value = event.region;

        let arrId = new Array();
        let arrId2 = new Array();
        for(let i = 0; i < athletes.length; i++) {
            arrId[i] = athletes[i].id;
        }

        for(let i = 0; i < event.athletesSet.length; i++) {
            arrId2[i] = event.athletesSet[i].id;
        }

        for (let i = 0; i < arrId.length; i++) {
            if(arrId2.includes(arrId[i]))
                listAthlete.innerHTML += `${athletes[i].name} <input class='athlete-checkbox' checked='checked' type='checkbox' name='atlhete' value='${arrId[i]}'/><br/>`;
            else listAthlete.innerHTML += `${athletes[i].name} <input class='athlete-checkbox' type='checkbox' name='atlhete' value='${arrId[i]}'/><br/>`;
        }

        document.getElementsByClassName("event-popup")[1].classList.remove("display-none");
    }
}

async function changeE() {
    let eventId = Number(selectedDiv.firstChild.innerText.substr(7));
    let athletes = document.getElementsByClassName("athlete-checkbox");
    let athletesSet = new Array();
    for(let i = 0; i < athletes.length; i++) {
        if(athletes[i].checked) {
            const getAthlete = await fetch(`http://localhost:3000/api/athletes/${athletes[i].value}`, {
                method: 'GET',
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem("token")}`
                }
            });
            athletesSet[i] = await getAthlete.json();
        }
    }

    let objEvent = {
        id: eventId,
        name: document.getElementsByClassName("input-add-article")[1].value,
        description: document.getElementsByClassName("input-add-description")[1].value,
        region: document.getElementsByClassName("input-add-region")[1].value,
        date: document.getElementsByClassName("input-add-date")[1].value,
        athletesSet: athletesSet
    }

    const changeEvent = await fetch(`http://localhost:3000/api/admin/events`, {
        method: 'PUT',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objEvent)
    });
    if(!changeEvent.ok) {
        let error = await changeEvent.json();
        alert(error.errorMessage);
    }
    else {
        alert("Changed");
        window.location.reload();
    }
}

function backChE() {
    document.getElementsByClassName("event-popup")[1].classList.add("display-none");
}

async function deleteRev(div) {
    let id = Number(div.classList[0]);
    const changeEvent = await fetch(`http://localhost:3000/api/admin/reviews/${id}`, {
        method: 'DELETE',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    if(changeEvent.ok) {
        div.parentNode.remove();
    }
    else alert("Deleting comment is not success");
}