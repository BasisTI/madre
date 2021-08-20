export interface ISinonimos {
    id?: number ;
    nome?: String ;
    situacao?: String ;
}
export class Sinonimos implements ISinonimos{
    public id: number ;
    public nome: String ;
    public situacao: String ;
}