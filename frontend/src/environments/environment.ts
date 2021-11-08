// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.
import { version } from 'package.json';

export const environment = {
    appVersion: version,
    production: false,
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
