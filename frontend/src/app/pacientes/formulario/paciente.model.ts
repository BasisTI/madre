export class Telefone {
    constructor(
        public id: number,
        public ddd: string,
        public numero: string,
        public tipo: string,
        public observacao: string,
    ) {}
}

export class Endereco {
    constructor(
        public id: number,
        public cep: string,
        public logradouro: string,
        public numero: string,
        public complemento: string,
        public bairro: string,
        public correspondencia: boolean,
        public tipoDoEndereco: string,
        public municipioId: number,
    ) {}
}

export class PacienteGenitor {
    constructor(
        public id: number,
        public prontuarioDaMae: string,
        public nomeDaMae: string,
        public nomeDoPai: string,
    ) {}
}

export class PacienteCartaoSUS {
    constructor(
        public id: number,
        public numero: string,
        public documentoDeReferencia: string,
        public cartaoNacionalSaudeMae: string,
        public dataDeEntradaNoBrasil: Date,
        public dataDeNaturalizacao: Date,
        public portaria: string,
        public justificativaId: number | any,
        public motivoDoCadastroId: number | any,
    ) {}
}

export class PacienteResponsavel {
    constructor(
        public id: number,
        public nomeDoResponsavel: string,
        public telefones: Telefone[],
        public observacao: string,
        public grauDeParentescoId: number | any,
    ) {}
}

export class PacienteDocumento {
    constructor(
        public id: number,
        public numeroDaIdentidade: string,
        public data: Date,
        public cpf: string,
        public pisPasep: string,
        public cnh: string,
        public validadeDaCnh: Date,
        public orgaoEmissorId: number | any,
        public ufId: number | any,
    ) {}
}

export class PacienteCertidao {
    constructor(
        public id: number,
        public registroDeNascimento: string,
        public tipoDaCertidao: string,
        public nomeDoCartorio: string,
        public livro: string,
        public folhas: string,
        public termo: string,
        public dataDeEmissao: Date,
        public numeroDaDeclaracaoDeNascimento: string,
    ) {}
}

export class Paciente {
    constructor(
        public id: number,
        public nome: string,
        public nomeSocial: string,
        public dataDeNascimento: Date,
        public horaDeNascimento: Date,
        public email: string,
        public observacao: string,
        public ocupacaoId: number | any,
        public religiaoId: number | any,
        public ufId: number | any,
        public naturalidadeId: number | any,
        public etniaId: number | any,
        public nacionalidadeId: number | any,
        public racaId: number | any,
        public estadoCivilId: number | any,
        public grauDeInstrucao: string,
        public sexo: string,
        public telefones: Telefone[],
        public enderecos: Endereco[],
        public genitores: PacienteGenitor,
        public cartaoSUS: PacienteCartaoSUS,
        public responsavel: PacienteResponsavel,
        public documento: PacienteDocumento,
        public certidao: PacienteCertidao,
        public prontuario: number | any,
    ) {}
}
