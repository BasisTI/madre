import { BaseEntity } from '../shared';


export class Acao implements BaseEntity {

  constructor(
    public id?: number,
    public nmAcao?: string,
    public cdAcao?: string,
  ) {}
}
