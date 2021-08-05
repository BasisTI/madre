
import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';
import { OpcaoSituacao } from '../opcoes-servidor-model';

export const OPCOES_DE_TIPO_DE_REMUNERACAO: OpcaoSituacao[] = [
    OPCAO_SELECIONE,
    {
        label: 'Mensalista',
        value: 'MENSALISTA',
    },
    {
        label: 'Horista',
        value: 'HORISTA',
    },
];
