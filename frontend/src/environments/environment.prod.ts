import { version } from 'package.json';

export const environment = {
    appVersion: version,
    production: true,
    apiUrl: '/api',
    auth: {
        detailsUrl: '/api/user/details',
        loginUrl: '/#/login',
        resetPasswordUrl: '/#/reset/finish?key=',
        logoutUrl: '/',
        userStorage: localStorage,
        userStorageIndex: 'user',
        publicUrls: ['/api/authenticate'],
        baseUrl: '',
        tokenValidationUrl: '/api/token/validate',
        storage: localStorage,
        loginSuccessRoute: '/#/login-success'
    },
};
