export interface PacienteResumo {
    prontuario: number;
    nome: string;
    dataDeNascimento: string;
    genitores: {
        nomeDaMae: string;
    };
    cartaoSUS: {
        numero: string;
    };
}
