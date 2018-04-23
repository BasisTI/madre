import { HttpService, AuthConfig, AuthService } from '@basis/angular-components';
import { Usuario } from './usuario/usuario';

export function authServiceFactory(http: HttpService, config: AuthConfig) {
  return new AuthService<Usuario>(http, config);
}
