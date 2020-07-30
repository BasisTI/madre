export interface IInformacaoTransferenciaDTO {
    id?: number;
    ativa?: boolean;
    efetivada?: boolean;
    classificacaoMaterialId?: number;
    centroDeAtividadeId?: number;
}

export class InformacaoTransferenciaDTO implements IInformacaoTransferenciaDTO {
    constructor(
        public id?: number,
        public ativa?: boolean,
        public efetivada?: boolean,
        public classificacaoMaterialId?: number,
        public centroDeAtividadeId?: number,
    ) {}
}
