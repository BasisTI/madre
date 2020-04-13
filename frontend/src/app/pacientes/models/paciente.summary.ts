export interface PacienteSummary {
    nome: string;
    dataDeNascimento: string;
    genitores: {
        nomeDaMae: string;
    };
    cartaoSUS: {
        numero: string;
    };
}
