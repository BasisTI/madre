import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, BrowserXhr } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { NgProgressModule } from '@ngx-progressbar/core';
import { NgProgressHttpModule } from '@ngx-progressbar/http';
import { ConfirmationService } from 'primeng/primeng';
import { PRIMENG_IMPORTS } from './primeng-imports';
import 'rxjs/add/operator/toPromise';
import {
  DatatableModule, SharedModule, HttpService, SecurityModule, AccessbilityModule,
  ErrorStackModule, AuthService, AuthConfig, AUTH_CONFIG, PageNotificationModule,
  BlockUiModule
} from '@basis/angular-components';

import { environment } from '../environments/environment';
import { SharedModule as AppSharedModule } from './shared/shared.module';
import { AppRoutes } from './app.routes';
import { AppComponent } from './app.component';
import { AppMenuComponent, AppSubMenuComponent } from './app.menu.component';
import { AppTopBarComponent } from './app.topbar.component';
import { AppFooterComponent } from './app.footer.component';
import { AppRightPanelComponent } from './app.rightpanel.component';
import { InlineProfileComponent } from './app.profile.component';
import { AppBreadcrumbComponent } from './breadcrumb/app.breadcrumb.component';
import { BreadcrumbService } from './breadcrumb/breadcrumb.service';
import { LoginComponent } from './login/login.component';
import { LoginSuccessComponent } from './login-success/login-success.component';
import { LogoutComponent } from './logout/logout.component';
import { authServiceFactory } from './auth-service-factory';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutes,
    HttpModule,
    BrowserAnimationsModule,
    PRIMENG_IMPORTS,
    NgProgressModule.forRoot(),
    NgProgressHttpModule,
    DatatableModule.forRoot(),
    SharedModule.forRoot(),
    ErrorStackModule.forRoot(),
    SecurityModule.forRoot(),
    AppSharedModule.forRoot(),
    AccessbilityModule.forRoot(),
    PageNotificationModule.forRoot(),
    BlockUiModule.forRoot()
    /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
  ],
  declarations: [
    AppComponent,
    AppMenuComponent,
    AppSubMenuComponent,
    AppTopBarComponent,
    AppFooterComponent,
    AppRightPanelComponent,
    AppBreadcrumbComponent,
    InlineProfileComponent,
    LoginComponent,
    LoginSuccessComponent,
    LogoutComponent,
    DiarioErrosComponent
  ],
  providers: [
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: AUTH_CONFIG, useValue: environment.auth },
    { provide: AuthService, deps: [HttpService, AUTH_CONFIG], useFactory: authServiceFactory },
    BreadcrumbService,
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
