import { Anticoagulante } from "./anticoagulante";

export interface AmostraDeMaterialI {
    id?: Number;
    nome?: String;
    origem: String;
    numeroDeAmostras?: Number; 
    volumeDaAmostra?: Number;
    unidadeDeMedida?: String;
    responsavel?: String;
    congelado?: Boolean;
    unidadeFuncionalId?: Number;
    amostraRecipienteId?: Number;
    amostraRecipienteNome?: String;
    amostraAnticoagulanteId?: Number;
    amostraAnticoagulanteNome?: String;
    amostraMaterialId?: Number;
    amostraMaterialNome?: String;
    materialDeExameId?: Number;
    }

export class AmostraDeMaterial implements AmostraDeMaterialI {
    public id?: Number;
    public nome?: String;
    public origem: String;
    public numeroDeAmostras?: Number; 
    public volumeDaAmostra?: Number;
    public unidadeDeMedida?: String;
    public responsavel?: String;
    public congelado?: Boolean;
    public unidadeFuncionalId?: Number;
    public amostraRecipienteId?: Number;
    public amostraRecipienteNome?: String;
    public amostraAnticoagulanteId?: Number;
    public amostraAnticoagulanteNome?: String;
    public amostraMaterialId?: Number;
    public amostraMaterialNome?: String;
    public materialDeExameId?: Number;
}