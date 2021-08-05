export interface ILogin {
    login?: string;
    codigo?: number;
}

export class Login implements ILogin {
    public login: string;
    public codigo: number;
}