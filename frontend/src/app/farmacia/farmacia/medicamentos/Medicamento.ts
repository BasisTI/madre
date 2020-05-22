export interface Medicamento {
    codigo: string;
    descricao: string;
    concentracao: {
        nome: string;
    };
    unidade: {
        nome: string;
    };
    tipo: {
        nome: string;
    };
    situacao: boolean;
}
