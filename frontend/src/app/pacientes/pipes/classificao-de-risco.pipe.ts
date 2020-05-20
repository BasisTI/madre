import { Pipe, PipeTransform } from '@angular/core';
import { CLASSIFICACAO_RISCO } from '../models/radioButton/classificacao-risco';

@Pipe({
    name: 'classificaoDeRisco',
})
export class ClassificaoDeRiscoPipe implements PipeTransform {
    CLASSIFICACAO_RISCO;

    transform(value: unknown, ...args: unknown[]): unknown {
        let output = '';

        CLASSIFICACAO_RISCO.forEach((classificacao) => {
            if (classificacao.value === value) {
                output = classificacao.label;
            }
        });

        return output;
    }
}
