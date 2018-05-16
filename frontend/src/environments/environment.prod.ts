export const environment = {
  production: true,
  apiUrl: 'http://madre.tst.basis.com.br:8080/api',
  auth: {
    detailsUrl: '/api/user/details',
    loginUrl: '/api/login',
    logoutUrl: '/api/logout',
    userStorage: localStorage,
    userStorageIndex: 'user'
  }
};
