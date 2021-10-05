import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'duration'
})
export class DurationPipe implements PipeTransform {
  transform(value: string): string {
    const leftPad = x => String(x).length >= 2 ? x : leftPad(`0${x}`);
    const [ _, hours, mins ] = value.match(/PT(?:(\d+)H)?(?:(\d+)M)?/);
    return [hours || 0, mins || 0].map(leftPad).join(':');
  }
}