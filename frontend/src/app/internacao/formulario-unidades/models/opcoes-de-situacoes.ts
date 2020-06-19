import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';
import { OpcaoCombo } from './../../../pacientes/models/dropdowns/opcao-combo';

export const OPCOES_DE_SITUACOES: OpcaoCombo[] = [
    OPCAO_SELECIONE,
    {
        label: 'Ativo',
        value: 'ATIVO',
    },
    {
        label: 'Inativo',
        value: 'INATIVO',
    },
];
