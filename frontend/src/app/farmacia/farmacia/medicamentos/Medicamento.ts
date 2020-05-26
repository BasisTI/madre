export interface Medicamento {
    id: number;
    codigo: string;
    nome: string;
    descricao: string;
    concentracao: string;
    ativo: boolean;

    apresentacao: {
        nome: string;
    };
    unidade: {
        nome: string;
    };
    tipo: {
        nome: string;
    };
}
