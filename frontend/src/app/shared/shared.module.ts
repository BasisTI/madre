import { NgModule, ModuleWithProviders } from '@angular/core';

import { JhiDateUtils } from './date-util.service';
/* jhipster-needle-add-shared-service-import - JHipster will add shared services imports here */

@NgModule({})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [
        JhiDateUtils,
        /* jhipster-needle-add-shared-services - JHipster will add shared services here */
      ]
    };
  }
}
