import { Caracteristica } from './dropwdowns/caracteristicas';
import { Cirurgia } from './Cirurgia';
import { Prescricao } from './Prescricao';
export class Unidade {
    constructor(
        public id?: number,
        public descricao?: string,
        public sigla?: string,
        public situacao?: string,
        public controleDeEstoque?: boolean,
        public idAlmoxarifado?: number,
        public alaId?: number,
        public clinicaId?: number,
        public andar?: number,
        public capacidade?: number,
        public horarioInicio?: Date,
        public horarioFim?: Date,
        public localExame?: string,
        public rotinaDeFuncionamento?: string,
        public anexoDocumento?: boolean,
        public setor?: number,
        public idCentroDeAtividade?: number,
        public idChefia?: number,
        public unidadePaiId?: number,
        public tipoUnidadeId?: number,
        public prescricaoEnfermagem?: Prescricao,
        public prescricaoMedica?: Prescricao,
        public cirurgia?: Cirurgia,
        public caracteristicas?: Caracteristica[],
    ) {}
}
