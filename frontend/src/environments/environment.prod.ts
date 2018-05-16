export const environment = {
  production: true,
  apiUrl: 'http://madre.tst.basis.com.br/api',
  auth: {
    detailsUrl: '/api/user/details',
    loginUrl: '/api/login',
    logoutUrl: '/api/logout',
    userStorage: localStorage,
    userStorageIndex: 'cadastrosbasicos'
  }
};
