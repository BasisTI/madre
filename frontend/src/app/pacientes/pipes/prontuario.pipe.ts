import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'prontuario',
})
export class ProntuarioPipe implements PipeTransform {
    transform(value: string, ...args: any[]): string {
        return `${value[0]}${value[1]}${value[2]}${value[3]}${value[4]}${value[5]}${value[6]}/${value[7]}`;
    }
}
