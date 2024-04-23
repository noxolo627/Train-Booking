allaboard_url = "https://allaboard.bbdgrad.com";

async function apiGet(endpoint) {
    return await fetch(allaboard_url+ endpoint, {
    method: "GET",
    credentials: "true",
    headers: {
        'Content-type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer ' + localStorage.getItem('token')
    }
});
}

async function apiPost(endpoint, body) {
    return await fetch(allaboard_url+ endpoint, {
    method: "POST",
    body: body,
    credentials: "true",
    headers: {
        'Content-type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
    }
});
}