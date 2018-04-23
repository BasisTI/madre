import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { MenuItem } from 'primeng/primeng';
import { AccessbilityService } from '@basis/angular-components';

import { AppComponent } from '../app.component';
import { BreadcrumbService } from './breadcrumb.service';

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './app.breadcrumb.component.html'
})
export class AppBreadcrumbComponent implements OnDestroy {

  subscription: Subscription;

  items: MenuItem[];

  highContrastEnabled = false;

  constructor(public breadcrumbService: BreadcrumbService, private accessibilityService: AccessbilityService) {
    this.subscription = breadcrumbService.itemsHandler.subscribe(response => {
      this.items = response;
    });
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  enableHighContrast() {
    this.highContrastEnabled = true;
    this.accessibilityService.enableHighContrast();
  }

  disableHighContrast() {
    this.highContrastEnabled = false;
    this.accessibilityService.disableHighContrast();
  }

  increaseFontSize() {
    this.accessibilityService.increaseFontSize();
  }

  decreaseFontSize() {
    this.accessibilityService.decreaseFontSize();
  }
}
