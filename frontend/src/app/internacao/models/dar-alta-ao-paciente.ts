
export class DarAltaAoPaciente {
    constructor(
   public id?: number,
   public dataDaInternacao?: Date,
   public dataDaAlta?: Date,
   public leitosId?: number,
   public leitoNome?: string,
   public unidadeFuncionalId?: number,
   public unidadeFuncionalNome?: string,
   public especialidadeId?: number,
   public especialidadeNome?: string,
   public conveniDeSaudeId?: number,
   public conveniDeSaudeNome?: string,
   public ativo?: boolean,
   public tipoDeAlta?: string,
    ) { }
}
