
import { OPCAO_SELECIONE } from '@shared/dropdown/opcao.selecione';
import { TipoDeRemuneracao } from '../tipo-de-remuneracao-model';

export const TIPO_DE_REMUNERACAO: TipoDeRemuneracao[] = [
    OPCAO_SELECIONE,
    {
        label: 'Mensalista',
        value: 'MENSALISTA',
    },
    {
        label: 'Horista',
        value: 'HORISTA',
    }
];
