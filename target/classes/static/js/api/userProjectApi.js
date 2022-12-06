async function createUserProject(data , token) {
    return await fetch("/user/createUserProject", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function updateUserProduct(data , token) {
    return await fetch("/user/updateUserProduct", {
        method: "PUT",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function isSubscribed(data , token)
{
    return await fetch("/user/isSubscribed", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function getUserProductListByUserName(data , token)
{
    return await fetch(`/user/getUserProductListByUserName/${data}`, {
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

async function isUserProjectExistByProjectId(data , token)
{
    return await fetch(`/admin/isUserProjectExistByProjectId`, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    });
}

async function getAllUserProduct(token) {
    return await fetch(`/admin/getAllUserProduct`, {
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