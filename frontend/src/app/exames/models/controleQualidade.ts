export interface IControleQualidade {
    Codigo?: number ;
    Material?: String ;
    CodigoConvenio?: String ;
    CodigoPlano?: String ;
}
export class ControleQualidade implements IControleQualidade{
    public Codigo: number ;
    public Material: String ;
    public CodigoConvenio: String ;
    public CodigoPlano: String ;
}