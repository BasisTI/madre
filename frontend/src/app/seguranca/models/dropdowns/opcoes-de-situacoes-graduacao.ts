
import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';
import { OpcaoSituacao } from '../opcoes-servidor-model';

export const OPCOES_DE_SITUACOES_GRADUACAO: OpcaoSituacao[] = [
    OPCAO_SELECIONE,
    {
        label: 'Em Andamento',
        value: 'EM_ANDAMENTO',
    },
    {
        label: 'Concluída',
        value: 'CONCLUIDA',
    },
    {
        label: 'Interrompida',
        value: 'INTERROMPIDA',
    },
];
