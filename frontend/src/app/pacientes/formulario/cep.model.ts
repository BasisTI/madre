export class CEP {
    id: number;
    cep: string;
    logradouro: string;
    bairro: string;
    municipioId: number;
    ufId: number;

    constructor(
        id: number,
        cep: string,
        logradouro: string,
        bairro: string,
        municipioId: number,
        ufId: number,
    ) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.municipioId = municipioId;
        this.ufId = ufId;
    }
}
