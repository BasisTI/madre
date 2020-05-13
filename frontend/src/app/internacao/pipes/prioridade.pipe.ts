import { OpcaoCombo } from './../../pacientes/models/dropdowns/opcao-combo';
import { Pipe, PipeTransform } from '@angular/core';
import { PrioridadeDropdown as prioridadeDropdown } from '@internacao/models/dropdowns/prioridades.dropdown';

@Pipe({
    name: 'prioridade',
})
export class PrioridadePipe implements PipeTransform {
    transform(prioridade: string, ...args: any[]): string {
        return prioridadeDropdown.find((opcao: OpcaoCombo) => {
            return opcao.value === prioridade;
        }).label;
    }
}
