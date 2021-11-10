import { OpcaoDropdown } from '@shared/dropdown/opcao.dropdown';

export const TipoDeAltaDropdown: OpcaoDropdown[] = [
    {
        label: 'Ôbito',
        value: 'OBITO',
    },
    {
        label: 'Alta médica',
        value: 'ALTA_MEDICA',
    },
    {
        label:'Transferência para outro hospital',
        value: 'TRANSFERENCIA_PARA_OUTRO_HOSPITAL',
    },
    {
        label: 'Desistência do tratamento',
        value: 'DESISTENCIA_DO_TRATAMENTO',
    },
    {
        label: 'Evasão',
        value: 'EVASAO',
    },
    {
        label: 'Indisciplina',
        value: 'INDISCIPLINA',
    },
    {
        label: 'Permanência',
        value: 'PERMANENCIA',
    },
];
