import { ConvenioDeSaude } from './convenio-de-saude';
import { Especialidade } from './especialidade';
import { Internacao } from './internacao';
import { ISituacaoDeLeito, SituacaoDeLeito } from './situacao-de-leito';
import { UnidadeFuncional } from './unidade-funcional';

export interface IGradeTransferirPacientes {
    id?: number;
    nomeId?: string;
    internacaoId?: number;
    situacaoId?: ISituacaoDeLeito;
    quartoId?: number;
    unidadeFuncionalId?: UnidadeFuncional;
    especialidadeId?: Especialidade;
    convenioId: ConvenioDeSaude;
}

export class GradeTransferirPacientes {
    constructor(
        public id?: number,
        public internacaoId?: Internacao,
        public transferenciaId?: number,
        public situacaoId?: SituacaoDeLeito,
        public quartoId?: number,
        public unidadeFuncionalId?: UnidadeFuncional,
        public especialidadeId?: Especialidade,
        public ConvenioDeSaudeId?: ConvenioDeSaude
    ) { }
}