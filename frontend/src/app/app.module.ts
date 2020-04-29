import { FarmaciaModule } from './farmacia/farmacia/farmacia.module';
import { PRIMENG_IMPORTS } from './primeng-imports';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AppRoutes } from './app.routes';

import { AppComponent } from './app.component';
import { AppMenuComponent, AppSubMenuComponent } from './app.menu.component';
import { AppTopbarComponent } from './app.topbar.component';
import { AppFooterComponent } from './app.footer.component';

import { AppBreadcrumbComponent } from './breadcrumb/app.breadcrumb.component';
import { BreadcrumbService } from './breadcrumb/breadcrumb.service';
import { AppRightpanelComponent } from './app.rightpanel.component';
import { AppInlineProfileComponent } from './app.profile.component';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { SharedModule } from './shared/shared.module';
import { PrescricaoMedicaModule } from './prescricao-medica/prescricao-medica.module';

import {
    SecurityModule,
    AccessbilityModule,
    VersionTagModule,
    AUTH_CONFIG,
    LoginService,
    JWTTokenService,
    AuthorizationService,
    AbstractLogin,
    AbstractToken,
    AuthenticationService,
    AbstractAuthorization,
    AbstractAuthentication,
    ClipboardModule,
    ErrorModule,
} from '@nuvem/angular-base';

import {
    PageNotificationModule,
    BlockUiModule,
    DatatableModule,
    ErrorStackModule,
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
        PRIMENG_IMPORTS,
        AccessbilityModule.forRoot(),
        VersionTagModule.forRoot(),
        SharedModule.forRoot(),
        BlockUiModule.forRoot(),
        PageNotificationModule,
        ErrorStackModule.forRoot(),
        ClipboardModule.forRoot(),
        ErrorModule.forRoot(),
        SecurityModule.forRoot(),
        PrescricaoMedicaModule,
        DatatableModule.forRoot(),
        FarmaciaModule,
        PrescricaoMedicaModule,
    ],
    declarations: [
        AppComponent,
        AppMenuComponent,
        AppSubMenuComponent,
        AppTopbarComponent,
        AppFooterComponent,
        AppBreadcrumbComponent,
        AppRightpanelComponent,
        AppInlineProfileComponent,
        DiarioErrosComponent,
    ],
    providers: [
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy,
        },

        { provide: AUTH_CONFIG, useValue: environment.auth },
        { provide: LoginService, deps: [HttpClient, AUTH_CONFIG] },
        { provide: AbstractLogin, useClass: LoginService },
        { provide: JWTTokenService, deps: [AUTH_CONFIG] },
        { provide: AuthorizationService, deps: [HttpClient, AUTH_CONFIG] },
        { provide: AbstractToken, useClass: JWTTokenService },
        { provide: AuthenticationService, deps: [AUTH_CONFIG] },
        { provide: AbstractAuthorization, useClass: AuthorizationService },
        { provide: AbstractAuthentication, useClass: AuthenticationService },

        BreadcrumbService,
        PrescricaoMedicaService,
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
