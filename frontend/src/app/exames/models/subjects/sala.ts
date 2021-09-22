
export class Sala {
    constructor(
        public id?: number,
        public codigoDaSala?: number,
        public identificacaoDaSala?: string, 
        public localizacaoDaSala?: string, 
        public ativo?: boolean, 
        public unidadeExecutoraId?: number) {}
}