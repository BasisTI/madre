export class Prescricao {
    constructor(
        public id?: number,
        public horarioValidade?: Date,
        public tempoAdiantamento?: Date,
        public unidadeTempo?: string,
        public numeroVias?: number,
    ) {}
}
