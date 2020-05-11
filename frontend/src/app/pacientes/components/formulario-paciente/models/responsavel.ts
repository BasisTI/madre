import { GrauDeParentesco } from './../../../models/dropdowns/types/grau-de-parentesco';
import { Telefone } from './telefone';

export class Responsavel {
    constructor(
        public id?: number,
        public nomeDoResponsavel?: string,
        public telefone?: Telefone[],
        public grauDeParentescoId?: number,
        public observacao?: string,
    ) {}
}