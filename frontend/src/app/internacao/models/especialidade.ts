export interface IEspecialidade {
    id?: number;
    sigla?: string;
    especialidade?: string;
}

export class Especialidade implements IEspecialidade {
    public id: number;
    public sigla: string;
    public especialidade: string;
}
