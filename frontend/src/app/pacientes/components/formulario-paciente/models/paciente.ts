import { Genitores } from './genitores';
import { CartaoSUS } from './cartaoSUS';
import { Responsavel } from './responsavel';
import { Telefone } from './telefone';
import { Endereco } from './endereco';
import { Documento } from './documento';
import { Certidao } from './certidao';

export class Paciente {
    constructor(
        public id?: number,
        public nome?: string,
        public nomeSocial?: string,
        public dataDeNascimento?: Date,
        public horaDeNascimento?: Date,
        public email?: string,
        public observacao?: string,
        public grauDeInstrucao?: string,
        public sexo?: string,
        public telefones?: Telefone[],
        public enderecos?: Endereco[],
        public genitores?: Genitores,
        public cartaoSUS?: CartaoSUS,
        public responsavel?: Responsavel,
        public documento?: Documento,
        public certidao?: Certidao,
        public ocupacaoId?: number,
        public religiaoId?: number,
        public naturalidadeId?: number,
        public etniaId?: number,
        public genitoresId?: number,
        public nacionalidadeId?: number,
        public racaId?: number,
        public estadoCivilId?: number,
    ) {}
}
