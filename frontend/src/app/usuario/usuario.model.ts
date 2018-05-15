import { BaseEntity } from '../shared';


export class Usuario implements BaseEntity {

  constructor(
    public id?: number,
    public nome?: string,
    public login?: string,
    public senha?: string,
    public email?: string,
    public perfil?: string,
    public unidadeDeSaude?: string,
    public ativo?: boolean,
  ) {}
}
