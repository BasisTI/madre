export class Cirurgia {
    constructor(
        public id?: number,
        public tempoMax?: Date,
        public tempoMin?: Date,
        public limiteDias?: number,
        public limteDiasConvenios?: number,
        public intervalocirurgia?: number,
        public intervaloProcedimento?: number,
    ) {}
}
