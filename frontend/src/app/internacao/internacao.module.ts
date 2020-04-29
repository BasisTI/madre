import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from './../shared/shared.module';
import { routes } from './internacao.routes';

@NgModule({
    declarations: [],
    imports: [CommonModule, SharedModule, RouterModule.forChild(routes)],
})
export class InternacaoModule {}
