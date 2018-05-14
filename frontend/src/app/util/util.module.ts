import {CpfCnpjPipe} from './pipe/cpf-cnpj.pipe';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
@NgModule({
    imports: [
    ],
    declarations: [
        CpfCnpjPipe,
        CpfCnpjPipe,
    ],
    providers: [],
    exports: [
        CpfCnpjPipe,
        CpfCnpjPipe,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UtilModule { }
