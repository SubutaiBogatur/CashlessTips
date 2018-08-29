import React from 'react';
import Cookies from 'js-cookie';

function key(key) {
    return 'cashlesstips_' + key;
}

export class Auth {

    static isLogged() {
        return localStorage.getItem(key('username')) != null;
    }

    static saveCredentials(inn, token) {
        localStorage.setItem(key('username'), inn);
        localStorage.setItem(key('token'), token);
    }

    static getUsername() {
        return localStorage.getItem(key('username'))
    }

    static withAuth(headers) {
        headers['Authorization'] = 'Token ' + localStorage.getItem(key('token'));
        return headers;
    }

    static withCsrf(headers) {
        headers['X-CSRFToken'] = Cookies.get('csrftoken');
        return headers;
    }

    static logout(history) {
        localStorage.removeItem(key('username'));
        localStorage.removeItem(key('token'));
        history.push('/login')
    }
}