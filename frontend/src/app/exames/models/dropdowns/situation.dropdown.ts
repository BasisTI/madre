import { OpcaoDropdown } from '@shared/dropdown/opcao.dropdown';
import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';

export const SituationDropdown: OpcaoDropdown[] = [
    OPCAO_SELECIONE,
    {
        label: 'A Coletar',
        value: 'A_COLETAR',
    },
    {
        label: 'Area Executora',
        value: 'AREA_EXECUTORA',
    },
    {
        label: 'Coletado',
        value: 'COLETADO',
    },
];
