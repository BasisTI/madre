import { NgModule, ModuleWithProviders } from '@angular/core';
import { TriStateCheckboxModule } from 'primeng/tristatecheckbox';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { PRIMENG_IMPORTS } from '../primeng-imports';

import { JhiDateUtils } from './date-util.service';
import { DatatableModule } from '@nuvem/primeng-components';
/* jhipster-needle-add-shared-service-import - JHipster will add shared services imports here */

@NgModule({
    imports: [
        ReactiveFormsModule,
        FormsModule,
        PRIMENG_IMPORTS,
        DatatableModule,
        TriStateCheckboxModule,
    ],
    exports: [
        ReactiveFormsModule,
        FormsModule,
        PRIMENG_IMPORTS,
        DatatableModule,
        TriStateCheckboxModule,
    ],
})
export class SharedModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedModule,
            providers: [
                JhiDateUtils,
                /* jhipster-needle-add-shared-services - JHipster will add shared services here */
            ],
        };
    }
}
