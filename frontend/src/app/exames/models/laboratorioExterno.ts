export interface ILaboratorioExterno {
    Codigo?: number; 
    Nome?: String; 
    Sigla?: String; 
    Endereco?: String; 
    Municipio?: String;
    Cep?: number; 
    Telefone?: number; 
    Fax?: number; 
    Email?: String; 
    Cgc?: String; 
    CodigoConvenio?: String; 
    CodigoPlano?: String;
}

export class LaboratorioExterno implements ILaboratorioExterno{
    public Codigo: number; 
    public Nome: String; 
    public Sigla: String; 
    public Endereco: String; 
    public Municipio: String;
    public Cep: number; 
    public Telefone: number; 
    public Fax: number; 
    public Email: String; 
    public Cgc: String; 
    public CodigoConvenio: String; 
    public CodigoPlano: String;

}