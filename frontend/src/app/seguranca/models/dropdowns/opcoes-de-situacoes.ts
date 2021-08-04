
import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';
import { OpcaoSituacao } from '../opcoes-servidor-model';

export const OPCOES_DE_SITUACOES_SERVIDOR: OpcaoSituacao[] = [
    OPCAO_SELECIONE,
    {
        label: 'Residente',
        value: 'RESIDENTE',
    },
    {
        label: 'Professor',
        value: 'PROFESSOR',
    },
];
