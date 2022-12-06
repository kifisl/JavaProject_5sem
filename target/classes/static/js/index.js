async function genSearchUser()
{
    let searchN = document.getElementById('sname');
    let searchD = document.getElementById('sdescription');

    if(searchN != null && searchD)
    {
        let searchButtonN = button(await genSearchButtonN, 'Search');
        let searchButtonD = button(await genSearchButtonD, 'Search');

        searchButtonN.id = 'searchButtonN';
        searchButtonN.className = 'btn btn-secondary';

        searchButtonD.id = 'searchButtonD';
        searchButtonD.className = 'btn btn-secondary';

        searchN.appendChild(searchButtonN);
        searchD.appendChild(searchButtonD);
    }
}

async function genSearchButtonN()
{
    let token = localStorage.getItem('token');
    let someList = document.querySelector('.someList');
    someList.innerHTML = '';
    let listProject = await getAllProjectsForUser(token);

    if (document.getElementById('name').value.length === 0)
    {
        errMes.innerHTML='';
        await genListOfProjectsForUser();
    }
    else
    {
        let found = false;
        let newListProject = new Array();

        for (let i = 0; i < listProject.length; i++)
        {
            if (listProject[i]['name'].includes(document.getElementById('name').value))
            {
                if(document.getElementById('description').value.length != 0)
                {
                    if(listProject[i]['description'].includes(document.getElementById('description').value))
                    {
                        found = true;
                        newListProject.push(listProject[i]);
                    }
                }
                else
                {
                    found = true;
                    newListProject.push(listProject[i]);
                }
            }
        }
        if(!found)
        {
            let errMes = document.getElementById('errMes');
            errMes.setAttribute('style', 'color: red');
            errMes.innerHTML='nothing found (to return full list of projects leave this field empty)'
        }
        else
        {
            errMes.innerHTML='';
            let table = document.createElement('table');
            table.setAttribute('class' , 'table table-bordered table-striped table-hover');
            let ProjectsTable = document.getElementById('ProjectsTable');

            if(ProjectsTable != null)
            {
                ProjectsTable.innerHTML = '';

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

                for (let i = 0; i < newListProject.length; i++)
                {
                    let tr = document.createElement('tr');

                    let td1 = document.createElement('td');
                    let td2 = document.createElement('td');
                    let td3 = document.createElement('td');

                    td1.innerHTML = newListProject[i]['name'];
                    td2.innerHTML = newListProject[i]['description'];

                    let buttonBuy = document.createElement('button');
                    buttonBuy.innerHTML = 'buy';
                    buttonBuy.setAttribute('class', 'btn btn-info');

                    buttonBuy.onclick = async () =>
                    {
                        await subUser(newListProject[i]);
                    };

                    td3.appendChild(buttonBuy);

                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tbody.appendChild(tr);
                }

                table.appendChild(tbody);
                ProjectsTable.appendChild(table);
            }
        }
    }
}

async function genSearchButtonD()
{
    let token = localStorage.getItem('token');
    let someList = document.querySelector('.someList');
    someList.innerHTML = '';
    let listProject = await getAllProjectsForUser(token);

    if (document.getElementById('description').value.length === 0)
    {
        errMes.innerHTML='';
        await genListOfProjectsForUser()
    }
    else
    {
        let found = false;
        let newListProject = new Array();

        for (let i = 0; i < listProject.length; i++)
        {
            if (listProject[i]['description'].includes(document.getElementById('description').value))
            {
                if(document.getElementById('name').value.length != 0)
                {
                    if(listProject[i]['name'].includes(document.getElementById('name').value))
                    {
                        found = true;
                        newListProject.push(listProject[i]);
                    }
                }
                else
                {
                    found = true;
                    newListProject.push(listProject[i]);
                }
            }
        }
        if(!found)
        {
            let errMes = document.getElementById('errMes');
            errMes.setAttribute('style', 'color: red');
            errMes.innerHTML='nothing found (to return full list of projects leave this field empty)'
        }
        else
        {
            errMes.innerHTML='';
            let table = document.createElement('table');
            table.setAttribute('class' , 'table table-bordered table-striped table-hover');
            let ProjectsTable = document.getElementById('ProjectsTable');

            if(ProjectsTable != null)
            {
                ProjectsTable.innerHTML = '';

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

                for (let i = 0; i < newListProject.length; i++)
                {
                    let tr = document.createElement('tr');

                    let td1 = document.createElement('td');
                    let td2 = document.createElement('td');
                    let td3 = document.createElement('td');

                    td1.innerHTML = newListProject[i]['name'];
                    td2.innerHTML = newListProject[i]['description'];

                    let buttonBuy = document.createElement('button');
                    buttonBuy.innerHTML = 'buy';
                    buttonBuy.setAttribute('class', 'btn btn-info');

                    buttonBuy.onclick = async () =>
                    {
                        await subUser(newListProject[i]);
                    };

                    td3.appendChild(buttonBuy);

                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tbody.appendChild(tr);
                }

                table.appendChild(tbody);
                ProjectsTable.appendChild(table);
            }
        }
    }
}

async function genCommentUser()
{
    let comment = document.getElementById('commentButton');

    if(comment != null)
    {
        let commentButton = button(await genCommentButton, 'Comment');

        commentButton.id = 'commentButton';
        commentButton.className = 'btn btn-success';

        comment.appendChild(commentButton);
    }
}

async function genCommentButton()
{
    let token = localStorage.getItem('token');
    let errMes = document.getElementById('errMes');

    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;

    let isNotExist = await isProjectExistUser({name: name}, token);

    if (await isAuth() && !isNotExist.ok)
    {
        let data =
            {
                name: ('C ' + name),
                description: description,
            };

        console.log(await createComment(data, token));
        console.log(data);
        errMes.setAttribute('style', 'color: green');
        errMes.innerHTML = `Commented successfully - ${name}`;
        await genListOfComments(); //users privilegies
    }
    else
    {
        errMes.setAttribute('style', 'color: red');
        errMes.innerHTML = 'Not all fields are correct or project doesnt exist';
    }
}

async function load()
{
    let result = document.querySelector('.results');
    await generateListOfUsers(result);
    genPrev();
    await printGreeting();
    await genNext();

    if (await isAuth())
    {
        genLogout();
        if (await isAdmin())
        {
            await genProject();
            await genAdminCreate();
            await genAdminUpdate();
            await genAdminDelete();
            await genListOfProjectsForAdmin();
        }
        else
        {
            await genListOfProjectsForUser();
            await genListOfComments();
            await genCommentUser();
            await genUserInfo();
            await genSearchUser();
        }
    }
    else
    {
        genLogReg(result);
    }

}
