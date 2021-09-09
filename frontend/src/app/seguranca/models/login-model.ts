export interface ILogin {
    login?: string;
    id?: number;
}

export class Login implements ILogin {
    public login: string;
    public id: number;
}