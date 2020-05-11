import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { AppComponent } from './app.component';
import { ScrollPanel } from 'primeng';

@Component({
    selector: 'app-rightpanel',
    template: `
        <div
            class="layout-rightpanel"
            [ngClass]="{ 'layout-rightpanel-active': app.rightPanelActive }"
            (click)="app.onRightPanelClick()"
        >
            <p-scrollPanel #scrollRightPanel [style]="{ height: '100%' }">
                <div class="layout-rightpanel-wrapper">
                    <div class="layout-rightpanel-header">
                        <div class="weather-day">Wednesday</div>
                        <div class="weather-date">Jan 26</div>
                    </div>

                    <div class="layout-rightpanel-content">
                        <h1>Weather</h1>
                        <h2>San Francisco, USA</h2>

                        <div class="weather-today">
                            <span class="weather-today-value">21&#8451;</span>
                            <img
                                src="assets/layout/images/dashboard/weather-icon-2.svg"
                                width="60"
                            />
                        </div>

                        <ul class="weekly-weather">
                            <li>
                                Thursday
                                <img src="assets/layout/images/dashboard/weather-icon-1.svg" />
                                <span class="weekly-weather-value">24</span>
                            </li>
                            <li>
                                Friday
                                <img src="assets/layout/images/dashboard/weather-icon-3.svg" />
                                <span class="weekly-weather-value">19</span>
                            </li>
                            <li>
                                Saturday
                                <img src="assets/layout/images/dashboard/weather-icon-4.svg" />
                                <span class="weekly-weather-value">15</span>
                            </li>
                            <li>
                                Sunday
                                <img src="assets/layout/images/dashboard/weather-icon-1.svg" />
                                <span class="weekly-weather-value">24</span>
                            </li>
                            <li>
                                Monday
                                <img src="assets/layout/images/dashboard/weather-icon-2.svg" />
                                <span class="weekly-weather-value">21</span>
                            </li>
                            <li>
                                Tuesday
                                <img src="assets/layout/images/dashboard/weather-icon-3.svg" />
                                <span class="weekly-weather-value">20</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </p-scrollPanel>
        </div>
    `,
})
export class AppRightpanelComponent implements AfterViewInit {
    @ViewChild('scrollRightPanel', { static: true }) rightPanelMenuScrollerViewChild: ScrollPanel;

    constructor(public app: AppComponent) {}

    ngAfterViewInit() {
        setTimeout(() => {
            this.rightPanelMenuScrollerViewChild.moveBar();
        }, 100);
    }
}
