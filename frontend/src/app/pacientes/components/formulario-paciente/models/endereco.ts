export class Endereco {
    constructor(
        public id?: number,
        public cep?: string,
        public logradouro?: string,
        public numero?: string,
        public complemento?: string,
        public bairro?: string,
        public correspondencia?: string,
        public tipoDoEndereco?: string,
        public municipioId?: number,
        public pacienteId?: number,
    ) {}
}
