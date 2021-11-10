export class DarAltaAoPaciente {
    constructor(
        public id?: number,
        public dataDaInternacao?: Date,
        public dataDaAlta?: Date,
        public previsaoDeAlta?: Date,
        public leitoId?: number,
        public leitoNome?: string,
        public unidadeFuncionalId?: number,
        public unidadeFuncionalNome?: string,
        public especialidadeId?: number,
        public especialidadeNome?: string,
        public convenioId?: number,
        public convenioNome?: string,
        public pacienteId?: number,
        public pacienteNome?: string,
        public prontuarioId?: number,
        public pacienteProntuario?: string,
        public ativo?: boolean,
        public tipoDeAlta?: string,
        public prioridade?: string,
        public justificativa?: string,
    ) {}
}
