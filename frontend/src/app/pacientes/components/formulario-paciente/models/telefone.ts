export class Telefone {
    constructor(
        public id?: number,
        public ddd?: string,
        public numero?: string,
        public tipo?: string,
        public observacao?: string,
        public pacienteId?: number,
        public responsavelId?: number,
    ) {}
}
