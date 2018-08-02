export const environment = {
  production: true,
  apiLocalhost:'/api',
  apiCadastrosBasicos: 'cadastrosbasicos/api',
  apiPaciente: 'pacientes/api',
  auth: {
    detailsUrl: '/api/user/details',
    loginUrl: '/api/login',
    logoutUrl: '/api/logout',
    userStorage: localStorage,
    userStorageIndex: 'user'
  }
};
