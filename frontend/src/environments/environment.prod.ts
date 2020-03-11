export const environment = {
  production: true,
  apiUrl: '/api',
  auth: {
    baseUrl: '',
    authUrl: '/login/cas',
    loginUrl: '/login/cas',
    logoutUrl: '/cas/logout',
    detailsUrl: '/api/user/details',
    tokenValidationUrl: '/api/token/validate',
    storage: localStorage,
    tokenStorageIndex: 'token',
    userStorageIndex: 'user',
    loginSuccessRoute: ''
  }
};
