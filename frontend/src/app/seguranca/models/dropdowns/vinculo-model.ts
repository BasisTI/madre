export interface IVinculo {
    descricao?: string;
    id?: number;
    matricula?: number;
}

export class Vinculo {
    constructor(
        public descricao?: string,
        public id?: number,
        public matricula?: number,
    ) {}
}
