import { FarmaciaComponent } from './farmacia.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Farmacia } from './farmacia.router';

@NgModule({
    declarations: [FarmaciaComponent],
    imports: [CommonModule, SharedModule, RouterModule.forChild(Farmacia)],
})
export class FarmaciaModule {}
