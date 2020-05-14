import { CID } from '@internacao/models/cid';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'cid',
})
export class CidPipe implements PipeTransform {
    transform(cid: CID, ...args: any[]): any {
        return `${cid.codigo} - ${cid.descricao}`;
    }
}
