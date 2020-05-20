import { TipoAprazamento } from './tipoAprazamento';
import { UnidadeDose } from './unidadeDose';
import { UnidadeInfusao } from './unidadeInfusao';
import { Diluente } from './diluente';
import { ViasAdministracao } from './viasAdministracao';
export class ItemPrescricaoMedicamento {
    constructor(
        public id?: number,
        public idMedicamento?: number,
        public idListaMedicamento?: number,
        public dose?: number,
        public frequencia?: number,
        public todasVias?: boolean,
        public bombaInfusao?: boolean,
        public velocidadeInfusao?: number,
        public tempoInfusao?: number,
        public unidadeTempo?: string,
        public inicioAdministracao?: Date,
        public condicaoNecessaria?: boolean,
        public observacaoCondicao?: string,
        public viasAdministracaoId?: number,
        public diluenteId?: number,
        public unidadeInfusaoId?: number,
        public unidadeDoseId?: number,
        public tipoAprazamentoId?: number

    ) {}
}
