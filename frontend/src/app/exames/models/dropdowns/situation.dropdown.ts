import { OpcaoDropdown } from '@shared/dropdown/opcao.dropdown';
import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';

export const SituationDropdown: OpcaoDropdown[] = [
    OPCAO_SELECIONE,
    {
        label: 'A Coletar',
        value: '1',
    },
    {
        label: 'Area Executora',
        value: '2',
    },
    {
        label: 'Coletado',
        value: '3',
    },
];
