import { BaseEntity } from '../shared';
import { Perfil } from '../perfil';
import { Especialidade } from '../especialidade';
import { UnidadeHospitalar } from '../unidade-hospitalar';


export class Usuario implements BaseEntity {

  constructor(
    public id?: number,
    public nome?: string,
    public login?: string,
    public senha?: string,
    public email?: string,
    public ativo?: boolean,
    public perfil?: Perfil,
    public especialidade?: Especialidade,
    public unidadeHospitalar?: Array<UnidadeHospitalar>,
  ) {
    unidadeHospitalar = new Array<UnidadeHospitalar>();
  }
}
