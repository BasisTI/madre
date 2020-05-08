import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AppRoutes } from './app.routes';

import { AppComponent } from './app.component';
import { AppTopbarComponent } from './app.topbar.component';
import { AppFooterComponent } from './app.footer.component';

import { AppRightpanelComponent } from './app.rightpanel.component';
import { AppInlineProfileComponent } from './app.profile.component';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { SharedModule } from './shared/shared.module';
import { PrescricaoMedicaModule } from './prescricao-medica/prescricao-medica.module';

import {
    SecurityModule,
    AccessbilityModule,
    VersionTagModule,
    ClipboardModule,
    ErrorModule,
} from '@nuvem/angular-base';

import {
    PageNotificationModule,
    BlockUiModule,
    DatatableModule,
    ErrorStackModule,
    MenuModule,
    BreadcrumbModule
} from '@nuvem/primeng-components';

import { environment } from '../environments/environment';
import { PrescricaoMedicaService } from './prescricao-medica/prescricao-medica.service';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutes,
        HttpClientModule,
        BrowserAnimationsModule,
        AccessbilityModule,
        VersionTagModule,
        SharedModule,
        BlockUiModule,
        PageNotificationModule,
        ErrorStackModule,
        ClipboardModule,
        ErrorModule,
        PrescricaoMedicaModule,
        DatatableModule,
        PrescricaoMedicaModule,
        SecurityModule.forRoot(environment.auth),
        BreadcrumbModule,
        MenuModule
    ],
    declarations: [
        AppComponent,
        AppTopbarComponent,
        AppFooterComponent,
        AppRightpanelComponent,
        AppInlineProfileComponent,
        DiarioErrosComponent,
    ],
    providers: [
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy,
        },
        PrescricaoMedicaService
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
