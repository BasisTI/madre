export class VinculoModel {
    constructor(
        public id?: number,

        public codigo?: number,

        public descricao?: string,
        
        public situacao?: boolean,
        
        public infDependente?: boolean,
        
        public exTermino?: boolean,
        
        public geraMatricula?: boolean,
        
        public matricula?: number,

        public exCentroAtividade?: boolean,
        
        public exOcupacao?: boolean,
        
        public transfereStarh?: boolean,
        
        public permiteDuplos?: boolean,
        
        public exCpfRg?: boolean,

        public gestapDesempenho?: boolean,

        public emiteContrato?: boolean,

        public numerosDeDiasAdmissao?: string,

        public tituloMasculino?: string,

        public tituloFeminino?: string,

    ) {}
}
