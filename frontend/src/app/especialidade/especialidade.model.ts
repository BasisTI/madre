import { BaseEntity } from '../shared';


export class Especialidade implements BaseEntity {

  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
  ) {}
}
