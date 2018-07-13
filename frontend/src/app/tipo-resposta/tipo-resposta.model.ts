import { BaseEntity } from '../shared';


export class TipoResposta implements BaseEntity {

  constructor(
    public id?: number,
    public resposta?: string,
  ) {}
}
