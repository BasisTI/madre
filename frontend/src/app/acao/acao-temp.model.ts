import { BaseEntity } from '../shared';


export class AcaoTemp implements BaseEntity {

  constructor(
    public id?: number,
    public idFuncionalidade?: number,
    public nmFuncionalidade?: string,
    public cdFuncionalidade?: string,
    public nmAcao?: string,
    public cdAcao?: string,
  ) {}
}
