import {
    CrudModule,
    CrudResolveGuard,
    DatatableModule,
    PageNotificationModule,
} from '@nuvem/primeng-components';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { JhiDateUtils } from './date-util.service';
import { MessageComponent } from './message.component';
import { PRIMENG_IMPORTS } from '../primeng-imports';
import { TriStateCheckboxModule } from 'primeng/tristatecheckbox';
import { CidComponent } from './cid/cid.component';
import { ArvoreComponent } from './cid/arvore-cid/arvore.component';

/* jhipster-needle-add-shared-service-import - JHipster will add shared services imports here */

@NgModule({
    imports: [
        ReactiveFormsModule,
        FormsModule,
        PRIMENG_IMPORTS,
        DatatableModule,
        TriStateCheckboxModule,
        CrudModule,
    ],
    declarations: [MessageComponent, CidComponent, ArvoreComponent],
    providers: [JhiDateUtils],
    exports: [
        ReactiveFormsModule,
        FormsModule,
        PRIMENG_IMPORTS,
        DatatableModule,
        TriStateCheckboxModule,
        CrudModule,
        MessageComponent,
        CidComponent,
        ArvoreComponent

    ],
})
export class SharedModule {}
