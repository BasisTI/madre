import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'cartaoSUS',
})
export class CartaoSusPipe implements PipeTransform {
  transform(value: string, ...args: any[]): string {
    const first = `${value[0]}${value[1]}${value[2]}`;
    const second = `${value[3]}${value[4]}${value[5]}${value[6]}`;
    const third = `${value[7]}${value[8]}${value[9]}${value[10]}`;
    const fourth = `${value[11]}${value[12]}${value[13]}${value[14]}`;
    return `${first} ${second} ${third} ${fourth}`;
  }
}
