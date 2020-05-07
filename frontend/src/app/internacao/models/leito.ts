import { IUnidadeFuncional, UnidadeFuncional } from './unidade-funcional';
import { ISituacaoDeLeito, SituacaoDeLeito } from './situacao-de-leito';

export interface ILeito {
    id?: number;
    nome?: string;
    ala?: number;
    andar?: number;
    situacao?: ISituacaoDeLeito;
    unidade?: IUnidadeFuncional;
}

export class Leito implements ILeito {
    public id?: number;
    public nome?: string;
    public ala?: number;
    public andar?: number;
    public situacao?: SituacaoDeLeito;
    public unidade?: UnidadeFuncional;
}
