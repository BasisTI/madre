import {
    CrudModule,
    CrudResolveGuard,
    DatatableModule,
    PageNotificationModule,
} from '@nuvem/primeng-components';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModuleWithProviders, NgModule } from '@angular/core';

import { JhiDateUtils } from './date-util.service';
import { MessageComponent } from './message.component';
import { PRIMENG_IMPORTS } from '../primeng-imports';
import { TriStateCheckboxModule } from 'primeng/tristatecheckbox';

/* jhipster-needle-add-shared-service-import - JHipster will add shared services imports here */

@NgModule({
    imports: [
        ReactiveFormsModule,
        FormsModule,
        PRIMENG_IMPORTS,
        DatatableModule,
        TriStateCheckboxModule,
        PageNotificationModule,
        CrudModule,
    ],
    declarations: [MessageComponent],
    providers: [JhiDateUtils],
    exports: [
        ReactiveFormsModule,
        FormsModule,
        PRIMENG_IMPORTS,
        DatatableModule,
        TriStateCheckboxModule,
        PageNotificationModule,
        CrudModule,
        MessageComponent,
    ],
})
export class SharedModule {}
