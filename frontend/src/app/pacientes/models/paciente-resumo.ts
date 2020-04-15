export interface PacienteResumo {
    nome: string;
    dataDeNascimento: string;
    genitores: {
        nomeDaMae: string;
    };
    cartaoSUS: {
        numero: string;
    };
}
