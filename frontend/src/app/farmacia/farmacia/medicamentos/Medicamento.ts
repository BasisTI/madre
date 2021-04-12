import { BaseEntity } from "@shared/base-entity";

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

export class Medicamentos implements BaseEntity {
    constructor(
        public id?: number,
        public codigo?: string,
        public nome?: string,
        public descricao?: string,
        public concentracao?: string,
        public ativo?: boolean,

        public apresentacao?: {
            nome: string;
        },
        public unidade?: {
            nome: string;
        },
        public tipo?: {
            nome: string;
        },
    )
    {}
}
