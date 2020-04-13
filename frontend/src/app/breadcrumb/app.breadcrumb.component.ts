import { Component, OnDestroy, AfterViewInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { MenuItem, MessageService, Message } from 'primeng/primeng';
import { AccessbilityService } from '@nuvem/angular-base';

import { BreadcrumbService } from './breadcrumb.service';

@Component({
    selector: 'app-breadcrumb',
    templateUrl: './app.breadcrumb.component.html',
})
export class AppBreadcrumbComponent implements OnDestroy, AfterViewInit {
    subscription: Subscription;

    items: MenuItem[];

    highContrastEnabled = false;

    constructor(
        public breadcrumbService: BreadcrumbService,
        private messages: MessageService,
        private accessibilityService: AccessbilityService,
    ) {
        this.subscription = breadcrumbService.itemsHandler.subscribe((response) => {
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

    ngAfterViewInit() {
        this.messages.messageObserver.subscribe((msg: Message) => {
            this.accessibilityService.addAccessibilityMessages({ severity: msg.severity });
        });
        this.accessibilityService.addAccessibilityIcons();
    }
}
