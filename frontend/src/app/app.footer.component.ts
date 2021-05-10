import { Component } from '@angular/core';

@Component({
    selector: 'app-footer',
    template: `
        <div class="footer">
            <div class="card clearfix">
                <app-version-tag cssClass="footer-text-left"></app-version-tag>
                <span class="footer-text-left">BASIS Tecnologia</span>
                <span class="footer-text-right">
                    <span class="p-icon p-icon-copyright"></span>
                    <span>All Rights Reserved</span>
                </span>
            </div>
        </div>
    `,
})
export class AppFooterComponent {}
