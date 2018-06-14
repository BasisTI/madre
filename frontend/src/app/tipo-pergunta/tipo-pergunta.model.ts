import { BaseEntity } from '../shared';


export class TipoPergunta implements BaseEntity {

  constructor(
    public id?: number,
    public enunciadoPergunta?: string,
  ) {}
}
