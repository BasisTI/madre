export const environment = {
  production: true,
  apiUrl: 'cadastrosbasicos/api',
  auth: {
    detailsUrl: '/api/user/details',
    loginUrl: '/api/login',
    logoutUrl: '/api/logout',
    userStorage: localStorage,
    userStorageIndex: 'user'
  }
};
