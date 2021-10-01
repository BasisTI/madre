export interface IRecomendacoesExames {
        id?: number,
        descricaoId?: string,
        responsavelId?: number, 
        abrangenciaId?: number, 
        ativo?: boolean;
}


export class RecomendacoesExames implements IRecomendacoesExames{
    constructor(
        public id?: number,
        public descricaoId?: string,
        public responsavelId?: number, 
        public abrangenciaId?: number, 
        public ativo?: boolean) {}
}