import { OpcaoDropdown } from '@shared/dropdown/opcao.dropdown';
import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';

export const PrioridadeDropdown: OpcaoDropdown[] = [
    OPCAO_SELECIONE,
    {
        label: 'Eletiva',
        value: 'ELETIVA',
    },
    {
        label: 'Urgência',
        value: 'URGENCIA',
    },
];
