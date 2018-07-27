import { BaseEntity } from '../shared';
import { AcaoTemp } from '../acao/acao-temp.model';


export class Perfil implements BaseEntity {

  constructor(
    public id?: number,
    public nomePerfil?: string,
    public dsPerfil?: string,
    public acaoTemp?: AcaoTemp[]
  ) { }
}

