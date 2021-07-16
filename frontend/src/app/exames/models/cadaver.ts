export interface ICadaver {
    codigo?:number;
    nome?:string;
    data_nascimento?:number;
    raca?:string;
    grupo_sanguineo?:string;
    data_remocao?:number;
    causa_obito?:string;
    realizado_por?:string;
    lido_por?:string;
    procedencia?:string;
    retirada?:string;
    codigo_plano?:string;
    convenio_plano?:string;
    observacao?:string;
}
export class Cadaver implements ICadaver{
    public codigo:number;
    public nome:string;
    public data_nascimento:number;
    public raca:string;
    public grupo_sanguineo:string;
    public data_remocao:number;
    public causa_obito:string;
    public realizado_por:string;
    public lido_por:string;
    public procedencia:string;
    public etirada:string;
    public codigo_plano:string;
    public convenio_plano:string;
    public observacao:string;
}