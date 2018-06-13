import { BaseEntity } from '../shared';


export class Anexo implements BaseEntity {

  constructor(
    public id?: number,
    public dataCriacao?: any,
    public nomeArquivo?: string,
    public tamanhoArquivo?: number,
    public arquivoAnexoContentType?: string,
    public arquivoAnexo?: any,
  ) {}
}
