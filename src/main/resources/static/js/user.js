async function subUser(listProjectElement)
{
    let token = localStorage.getItem('token');
    let userData = await getUserByToken(token);
    let text = await userData.text();
    let textUserData = JSON.parse(text);
    let errMes = document.getElementById('errMes');
    let isNotSubed = await isSubscribed({user:textUserData , project:listProjectElement},token);


    console.log(listProjectElement);

    if(isNotSubed.ok)
    {
        await createUserProject({user:textUserData , project:listProjectElement},token);
        errMes.setAttribute('style', 'color: green;');
        errMes.innerHTML = "ticket has been ordered -" + listProjectElement['name'];
    }
    else
    {
        let user = await getUser();
        let userProductsList = await getUserProductListByUserName(user['login'], token);

        console.log(userProductsList);

        let flag = false;

        for (let i = 0; i < userProductsList.length; i++)
        {
            let project = userProductsList[i]['project'];

            if(project['id'] === listProjectElement['id'] && userProductsList[i]['denide'] === true)
            {
                let mes = await updateUserProduct({
                    id: userProductsList[i]['id'],
                    denide: false,
                    set: true
                }, token);
                console.log(mes);
                flag = true;
            }
        }

        if(!flag)
        {
            errMes.setAttribute('style', 'color: red;');
            errMes.innerHTML = "You have already ordered this ticket - " + listProjectElement['name'];
        }
        else
        {
            errMes.setAttribute('style', 'color: green;');
            errMes.innerHTML = "Ticket ordered successfully - " + listProjectElement['name'];
        }
    }
}
async function genUserInfo()
{
    let token = localStorage.getItem('token');
    let user = await getUser();
    let userProductsList = await getUserProductListByUserName(user['login'], token);
    console.log(userProductsList);

    let info = document.getElementById('UserProjectsTable');

    if(info != null)
    {
        let table = document.createElement('table');
        table.setAttribute('class' , 'table table-bordered table-striped table-hover');

        info.innerHTML = '';

        let thead = document.createElement('thead');
        let tr = document.createElement('tr');

        let th1 = document.createElement('th');
        th1.innerHTML = 'Stage project name';
        th1.setAttribute('style', 'width: 30%; text-align: center');

        let th2 = document.createElement('th');
        th2.innerHTML = 'Stage project description';
        th2.setAttribute('style', 'width: 50%; text-align: center');

        let th3 = document.createElement('th');
        th3.innerHTML = 'Options';
        th3.setAttribute('style', 'width: 20%; text-align: center');

        tr.appendChild(th1);
        tr.appendChild(th2);
        tr.appendChild(th3);

        thead.appendChild(tr);
        table.appendChild(thead);

        let tbody = document.createElement('tbody');


        for (let i = 0; i < userProductsList.length; i++)
        {
            if(userProductsList[i]['denide'] === false)
            {
                let tr = document.createElement('tr');

                let td1 = document.createElement('td');
                let td2 = document.createElement('td');
                let td3 = document.createElement('td');

                let project = userProductsList[i]['project'];

                td1.innerHTML = project['name'];
                td2.innerHTML = project['description'];

                let buttonCancel = document.createElement('button');
                buttonCancel.innerHTML = 'Cancel';
                buttonCancel.setAttribute('class', 'btn btn-danger');

                buttonCancel.onclick = async () =>
                {
                    await updateUserProduct({
                        id: userProductsList[i]['id'],
                        denide: true,
                        set: true
                    }, token);

                    await genUserInfo();
                };

                td3.appendChild(buttonCancel);

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tbody.appendChild(tr);
            }
        }
        table.appendChild(tbody);
        info.appendChild(table);
    }
}
async function genListOfProjectsForUser()
{
    let token = localStorage.getItem('token');
    let table = document.createElement('table');

    table.setAttribute('class' , 'table table-bordered table-striped table-hover');
    let ProjectsTable = document.getElementById('ProjectsTable');

    if(ProjectsTable != null)
    {
        ProjectsTable.innerHTML = '';
        let listProject = await getAllProjectsForUser(token);

        let thead = document.createElement('thead');
        let tr = document.createElement('tr');

        let th1 = document.createElement('th');
        th1.innerHTML = 'Stage project name';
        th1.setAttribute('style', 'width: 30%; text-align: center');

        let th2 = document.createElement('th');
        th2.innerHTML = 'Stage project description';
        th2.setAttribute('style', 'width: 50%; text-align: center');

        let th3 = document.createElement('th');
        th3.innerHTML = 'Options';
        th3.setAttribute('style', 'width: 20%; text-align: center');

        tr.appendChild(th1);
        tr.appendChild(th2);
        tr.appendChild(th3);

        thead.appendChild(tr);
        table.appendChild(thead);

        let tbody = document.createElement('tbody');

        for (let i = 0; i < listProject.length; i++)
        {
            if(!listProject[i]['name'].startsWith('C '))
            {
                let tr = document.createElement('tr');

                let td1 = document.createElement('td');
                let td2 = document.createElement('td');
                let td3 = document.createElement('td');

                td1.innerHTML = listProject[i]['name'];
                td2.innerHTML = listProject[i]['description'];

                let buttonBuy = document.createElement('button');
                buttonBuy.innerHTML = 'participate';
                buttonBuy.setAttribute('class', 'btn btn-info');

                buttonBuy.onclick = async () => {
                    await subUser(listProject[i]);
                };

                td3.appendChild(buttonBuy);

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tbody.appendChild(tr);
            }
        }

        table.appendChild(tbody);
        ProjectsTable.appendChild(table);
    }
}

async function genListOfComments()
{
    let token = localStorage.getItem('token');
    let table = document.createElement('table');

    table.setAttribute('class' , 'table table-bordered table-striped table-hover');
    let CommentsTable = document.getElementById('CommentsTable');

    if(CommentsTable != null)
    {
        CommentsTable.innerHTML = '';
        let listProject = await getAllProjectsForUser(token);

        let thead = document.createElement('thead');
        let tr = document.createElement('tr');

        let th1 = document.createElement('th');
        th1.innerHTML = 'Commented project name';
        th1.setAttribute('style', 'width: 20%; text-align: center');

        let th2 = document.createElement('th');
        th2.innerHTML = 'Comment text';
        th2.setAttribute('style', 'width: 80%; text-align: center');

        tr.appendChild(th1);
        tr.appendChild(th2);

        thead.appendChild(tr);
        table.appendChild(thead);

        let tbody = document.createElement('tbody');

        for (let i = 0; i < listProject.length; i++)
        {
            if(listProject[i]['name'].startsWith('C '))
            {
                let tr = document.createElement('tr');

                let td1 = document.createElement('td');
                let td2 = document.createElement('td');

                let name = listProject[i]['name'].toString();

                for (let y = 2; y < name.length; y++)
                {
                    td1.innerHTML += name[y];
                }
                td2.innerHTML = listProject[i]['description'];

                tr.appendChild(td1);
                tr.appendChild(td2);
                tbody.appendChild(tr);
            }
        }

        table.appendChild(tbody);
        CommentsTable.appendChild(table);
    }
}