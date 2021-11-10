export class TransferirPacientes {
    constructor(
        public id?: number,
        public pacienteId?: number,
        public internacaoId?: Date,
        public transferencia?: Date,
        public leito?: number,
        public quartoId?: number,
        public unidadeFuncionalId?: number,
        public especialidadeId?: number,
        public ConvenioDeSaudeId?: number,
    ) { }
}