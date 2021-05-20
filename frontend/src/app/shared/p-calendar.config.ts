/*
 * Oções de configuração
 * https://www.primefaces.org/primeng-8.1.5/#/calendar
 */

import { LocaleSettings } from "primeng/calendar";

export interface ConfiguracaoParaCalendarioPrimeNG {
    dataMaxima?: Date;
    dataMinima?: Date;
    localidade?: LocaleSettings;
    formatoDeData?: string;
    anosDisponiveis?: string;
}
