
import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { AppTopbarComponent } from './app.topbar.component';
import { AppRightpanelComponent } from './app.rightpanel.component';
import { AppInlineProfileComponent } from './app.profile.component';
import { AppFooterComponent } from './app.footer.component';
import { AppBreadcrumbComponent } from 'url/';
import { AppMenuComponent, AppSubMenuComponent } from 'url/';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import {BreadcrumbService} from "@nuvem/primeng-components";

describe('AppComponent', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [RouterTestingModule, ScrollPanelModule],
            declarations: [
                AppComponent,
                AppTopbarComponent,
                AppMenuComponent,
                AppSubMenuComponent,
                AppFooterComponent,
                AppBreadcrumbComponent,
                AppInlineProfileComponent,
                AppRightpanelComponent,
            ],
            providers: [BreadcrumbService],
        });
        TestBed.compileComponents();
    });

    it('should create the app', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));
});
