import { ConfirmDialogModule } from 'primeng/confirmdialog';
import {
    AccessbilityModule,
    ClipboardModule,
    ErrorModule,
    SecurityModule,
    VersionTagModule,
} from '@nuvem/angular-base';
import {
    BlockUiModule,
    BreadcrumbModule,
    DatatableModule,
    ErrorStackModule,
    MenuModule,
    PageNotificationModule,
} from '@nuvem/primeng-components';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppFooterComponent } from './app.footer.component';
import { AppInlineProfileComponent } from './app.profile.component';
import { AppRightpanelComponent } from './app.rightpanel.component';
import { AppRoutes } from './app.routes';
import { AppTopbarComponent } from './app.topbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { FarmaciaModule } from './farmacia/farmacia/farmacia.module';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { PreCadastroComponent } from './pacientes/components/pre-cadastro/pre-cadastro.component';
import { PrescricaoMedicaModule } from './prescricao-medica/prescricao-medica.module';
import { PrescricaoMedicaService } from './prescricao-medica/prescricao-medica.service';
import { SharedModule } from './shared/shared.module';
import { SuprimentosModule } from './suprimentos/suprimentos.module';
import { environment } from '../environments/environment';
import { ConsultaModule } from './consulta/consulta.module';
import { PacienteModule } from './pacientes/formulario/paciente.module';
import { ExamesModule } from './exames/exames.module';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutes,
        HttpClientModule,
        BrowserAnimationsModule,
        AccessbilityModule,
        VersionTagModule.forRoot(environment),
        SharedModule,
        BlockUiModule,
        PageNotificationModule,
        ErrorStackModule,
        ClipboardModule,
        ErrorModule,
        PrescricaoMedicaModule,
        DatatableModule,
        FarmaciaModule,
        PrescricaoMedicaModule,
        SuprimentosModule,
        SecurityModule.forRoot(environment.auth),
        BreadcrumbModule,
        MenuModule,
        ConsultaModule,
        PacienteModule,
        ConfirmDialogModule,
        ExamesModule
    ],
    declarations: [
        AppComponent,
        AppTopbarComponent,
        AppFooterComponent,
        AppRightpanelComponent,
        AppInlineProfileComponent,
        DiarioErrosComponent,
        PreCadastroComponent,
    ],
    providers: [
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy,
        },
        PrescricaoMedicaService,
    ], 
    bootstrap: [AppComponent],
})
export class AppModule {}
