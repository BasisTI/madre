export interface IRecomendacoesExames {
        id?: number,
        descricao?: string,
        responsavel?: string, 
        abrangencia?: string, 
        avisoResponsavel?: boolean;
}


export class RecomendacoesExames implements IRecomendacoesExames{
    constructor(
        public id?: number,
        public descricao?: string,
        public responsavel?: string, 
        public abrangencia?: string, 
        public avisoResponsavel?: boolean) {}
}