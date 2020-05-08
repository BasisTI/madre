export interface IModalidadeAssistencial {
    id?: number;
    nome?: string;
}

export class ModalidadeAssistencial implements IModalidadeAssistencial {
    public id: number;
    public nome: string;
}
