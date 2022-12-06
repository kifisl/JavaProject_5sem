async function isProjectExist(data , token) {
    return await fetch("/admin/isProjectExist", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function isProjectExistUser(data , token) {
    return await fetch("/user/isProjectExist", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}


async function deleteProjectByName(data , token)
{
    return await fetch("/admin/deleteProjectByName", {
        method: "DELETE",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function createProject(data , token)
{
    return await fetch("/admin/createProject", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function createComment(data , token)
{
    return await fetch("/user/createProject", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function updateProject(data , token)
{
    return await fetch("/admin/updateProduct", {
        method: "PUT",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function getProjectByName(data , token)
{
    return await fetch(`/admin/getProductByName/${data}`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }).then(function (res) {
        return res.json();
    }).then(function (data) {
        return data;
    });
}

async function getAllProjectsForAdmin(token)
{
    return await fetch(`/admin/getAllProjectsAdmin`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }).then(function (res) {
        return res.json();
    }).then(function (data) {
        return data;
    });
}

async function getAllProjectsForUser(token)
{
    return await fetch(`/user/getAllProjectsUser`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }).then(function (res) {
        return res.json();
    }).then(function (data) {
        return data;
    });
}


