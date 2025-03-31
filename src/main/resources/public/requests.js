// const rootDir = "http://192.168.1.90:8080/bibliothek";
const rootDir = window.location.origin + '/bibliothek';
// const rootDir = 'http://localhost:8080/bibliothek';

/**
 * Function to send request with HTTP GET Method
 * @param {string} url URL
 * @param {Function} consumerFunction Function which consumes the retrieved data
 * @version 1.0.0
 * @author Simon Fäs
 */
function httpGet(url, consumerFunction) {
    fetch(url, {
        method: "GET",
        credentials: "include"
    })
        .then(response => {
            if (response.headers.get("Content-Type")?.includes("text/html")) {
                errorHandler(response);
            } else if (response.headers.get("Content-Type")?.includes("text/plain")) {
                response.text().then(data => consumerFunction(data));
            } else {
                response.json().then(data => consumerFunction(data))
            }
        })
        .catch(error => console.error(error));
}

/**
 * Function to send request with HTTP POST Method
 * @param {string} url URL
 * @param {any} data Content of the body
 * @version 1.0.0
 * @author Simon Fäs
 */
async function httpPost(url, data = {}) {
    const response = await fetch(url, {
        method: "POST",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    });
    if (response.headers.get("Content-Type")?.includes("text/html")) {
        errorHandler(response);
    } else {
        return response.json(); // parses JSON response into native JavaScript objects
    }
}

/**
 * Function to send request with HTTP PUT Method
 * @param {string} url URL
 * @param {any} data Content of the body
 * @version 1.0.0
 * @author Simon Fäs
 */
async function httpPut(url, data = {}) {
    const response = await fetch(url, {
        method: "PUT",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    });
    if (response.headers.get("Content-Type")?.includes("text/html")) {
        errorHandler(response);
    } else {
        return response.json(); // parses JSON response into native JavaScript objects
    }
}

/**
 * Function to send request with HTTP DELETE Method
 * @param {string} url URL
 * @param {number|string} id ID of the to be deleted element
 * @version 1.0.0
 * @author Simon Fäs
 */
function httpDelete(url, id) {
    return fetch(`${url}/${id}`, {
        method: "DELETE",
        credentials: "include",
    });
}

/**
 * Function to handle HTTP-Errors
 * @param {Response} error Response-Object with the needed information
 */
function errorHandler(error = new Response()) {
    if (error.status < 400) {
        location.reload();
    } else if (error?.statusText?.trim()?.length === 0) {
        let text = "";
        if (error.status === 404) {
            text = "Objekt nicht gefunden";
        } else if (error.status === 409) {
            text = "Konflikt. Objekt besteht bereits";
        } else if (error.status === 500) {
            text = "Interner Server-Error";
        } else if (error.status === 401 || error.status === 403) {
            text = "Keine Berechtigung"
        }
        window.alert(`HTTP-Code ${error.status}: ${text}`);
    } else {
        window.alert(`HTTP-Code ${error.status}: ${error.statusText}`);
    }
}