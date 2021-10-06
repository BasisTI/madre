export interface IFormularioTransferirPaciente {
    id?: number;
    descricao?: string;
}

export class FormularioTransferirPaciente implements IFormularioTransferirPaciente {
    public id?: number;
    public descricao?: string;
}