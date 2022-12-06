async function genListOfProjectsForAdmin()
{
    let token = localStorage.getItem('token');

    let errMes = document.getElementById('errMes');

    let table = document.createElement('table');
    table.setAttribute('class' , 'table table-bordered table-striped table-hover');
    let ProjectsTable = document.getElementById('ProjectsTable');

    if(ProjectsTable != null)
    {
        ProjectsTable.innerHTML = '';
        let listProject = await getAllProjectsForAdmin(token);

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

                let buttonEdit = document.createElement('button');
                buttonEdit.innerHTML = 'Edit';
                buttonEdit.setAttribute('class', 'btn btn-info');
                buttonEdit.setAttribute('style', 'margin-left: 20px;');

                let buttonDelete = document.createElement('button');
                buttonDelete.innerHTML = 'Delete';
                buttonDelete.setAttribute('class', 'btn btn-danger');
                buttonDelete.setAttribute('style', 'margin-left: 20px;');

                buttonEdit.onclick = async () => {
                    await getCertainProject(listProject[i]);
                };

                buttonDelete.onclick = async () => {
                    let result = await deleteProjectByName({name: listProject[i]['name']}, token);

                    if (result.ok) {
                        errMes.setAttribute('style', 'color: green;');
                        errMes.innerHTML = `Deleted successfuly - ${listProject[i]['name']}`;
                        await genListOfProjectsForAdmin();
                    } else {
                        errMes.setAttribute('style', 'color: red;');
                        errMes.innerHTML = `Concert that already ordered cant be deleted - ${listProject[i]['name']}`;
                    }
                };

                td3.appendChild(buttonEdit);
                td3.appendChild(buttonDelete);

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


async function getCertainProject(softObj)
{
    console.log(softObj);
    let token = localStorage.getItem('token');
    let soft = await getProjectByName(softObj['name'], token);

    document.getElementById('name').value = soft['name'];
    document.getElementById('description').value = soft['description'];
}

async function genProject()
{
    let info = document.querySelector(".personalInfo");
    if(info != null)
    {
        let name = input('text', 'name', 'Name', '');
        let description = input('text', 'description', 'Description', '');

        info.appendChild(name);
        info.appendChild(description);
    }
}

async function genAdminCreate() {

    let create = document.getElementById('createButton');
    if(create != null)
    {
        let createButton = button(await genAdminCreateButton, 'Create');
        createButton.id = 'docCreateButton';
        createButton.className = 'btn btn-success';
        create.appendChild(createButton);
    }
}

async function genAdminDelete() {

    let create = document.querySelector('.create');
    if(create != null)
    {
        let deleteButton = button(genDeleteProject, 'Delete');
        create.appendChild(deleteButton);
    }
}

async function genAdminCreateButton()
{
    let token = localStorage.getItem('token');
    let errMes = document.getElementById('errMes');

    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;

    let isNotExist = await isProjectExist({name: name}, token);

    if (validateDocument() && await isAuth() && isNotExist.ok)
    {
        let data =
        {
            name: name,
            description: description,
        };

        await createProject(data, token);
        console.log(data);
        errMes.setAttribute('style', 'color: green');
        errMes.innerHTML = `Created successfully - ${name}`;
        await genListOfProjectsForAdmin();
    }
    else
    {
        errMes.setAttribute('style', 'color: red');
        errMes.innerHTML = 'Not all fields are correct or project exist';
    }
}

async function genDeleteProject()
{
    let errMes = document.getElementById('errMes');
    let token = localStorage.getItem('token');
    let name = document.getElementById('name').value;
    let isNotExist = await isProjectExist({name: name}, token);
    if (await isAuth() && !isNotExist.ok)
    {

        let project = await getProjectByName( name, token);
        let isNotExist = await isUserProjectExistByProjectId({id: project['id']}, token);
        if (isNotExist.ok)
        {
            let result = await deleteProjectByName({name: name}, token);

            if (result.ok)
            {
                errMes.innerHTML = 'deleted';
                await genListOfProjectsForAdmin();
            }
            else
            {
                errMes.innerHTML = 'smt wrong';
            }

        }
        else
        {
            errMes.innerHTML = 'u cant delete this stage project while project had bought users';
        }

    }
    else
    {
        errMes.innerHTML = 'nothing to delete';
    }
}

function validateDocument()
{
    let nameL = document.getElementById('name').value.length;
    let descriptionL = document.getElementById('description').value.length;

    if (!(nameL >= 2 && nameL <= 16))
    {
        return false;
    }
    if (!(descriptionL >= 4))
    {
        return false;
    }
    return true;

}

async function isAdmin()
{
    let user = await getUser();
    return user['role'] === 'ROLE_ADMIN';
}

async function genUpdateProject()
{
    let errMes = document.getElementById('errMes');
    let token = localStorage.getItem('token');
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let isNotExist = await isProjectExist({name: name}, token);

    if (await isAuth() && !isNotExist.ok)
    {

        let res = await getProjectByName(name, token);

        if (validateDocument())
        {

            await updateProject({
                id: res['id'],
                name: name,
                description: description,

            }, token);

            errMes.setAttribute('style', 'color: green');
            errMes.innerHTML = `Updated successfully - ${name}`;
            await genListOfProjectsForAdmin();
        }
        else
        {
            errMes.setAttribute('style', 'color: red');
            errMes.innerHTML = 'not validate data';
        }

    }
    else
    {
        errMes.setAttribute('style', 'color: red');
        errMes.innerHTML = 'nothing to update';
    }
}

async function genAdminUpdate()
{
    let create = document.getElementById('editButton');
    if(create != null)
    {
        let deleteButton = button(genUpdateProject, 'update');
        deleteButton.className = "btn btn-warning";
        create.appendChild(deleteButton);
    }
}

async function accept(userProductsListElement, token)
{
    await updateUserProduct({
        id: userProductsListElement['id'],
        denide: false,
        set: true
    }, token);
    await genAdminInfo();
}

async function denide(userProductsListElement, token)
{
    await updateUserProduct({
        id: userProductsListElement['id'],
        denide: true,
        set: true
    }, token);
    await genAdminInfo();
}

async function genAdminInfo()
{
    let token = localStorage.getItem('token');
    let userProductsList = await getAllUserProduct(token);

    console.log(userProductsList);
    let info = document.querySelector('.neededInfo');
    let table = document.createElement('table');

    if(info != null)
    {
        info.innerHTML = '';
        table.setAttribute('class', 'table');

        for (let i = 0; i < userProductsList.length; i++) {

            if (i === 0)
            {
                let tr = document.createElement('tr');
                let th1 = document.createElement('th');
                th1.innerHTML = 'Stage project name';
                let th2 = document.createElement('th');
                th2.innerHTML = 'Stage project description';
                let th3 = document.createElement('th');
                th3.innerHTML = 'User name';
                let th4 = document.createElement('th');
                th4.innerHTML = 'Accept';
                let th5 = document.createElement('th');
                th5.innerHTML = 'Denide';

                tr.appendChild(th1);
                tr.appendChild(th2);
                tr.appendChild(th3);
                tr.appendChild(th4);
                tr.appendChild(th5);
                table.appendChild(tr);
            }
            let isSet = userProductsList[i]['set'];
            if (isSet === false) {
                let tr = document.createElement('tr');
                for (let y = 0; y < 7; y++) {
                    let th = document.createElement('th');
                    let user = userProductsList[i]['user'];
                    let project = userProductsList[i]['project'];

                    switch (y) {
                        case 0: {
                            th.innerHTML = project['name'];
                            break;
                        }
                        case 1: {
                            th.innerHTML = project['description'];
                            break;
                        }
                        case 2: {
                            th.innerHTML = user['login'];
                            break;
                        }
                        case 3: {
                            let subButton = buttonWithParams('Accept');
                            subButton.onclick = async () => {
                                await accept(userProductsList[i], token);
                            };
                            th.appendChild(subButton);
                            break;
                        }
                        case 4: {
                            let subButton = buttonWithParams('Denide');
                            subButton.onclick = async () => {
                                await denide(userProductsList[i], token);
                            };
                            th.appendChild(subButton);
                            break;
                        }
                    }
                    tr.appendChild(th);

                }
                table.appendChild(tr);
            }

        }
        info.appendChild(table);
    }
}