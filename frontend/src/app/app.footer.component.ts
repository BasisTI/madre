import { Component } from '@angular/core';

@Component({
    selector: 'app-footer',
    template: `
        <div class="layout-footer p-d-flex p-ai-center p-p-4 p-shadow-2">
            <span class="footer-text-left">BASIS Tecnologia - </span>
            <app-version-tag cssClass="footer-text-left"></app-version-tag>
            <span class="p-ml-auto p-mr-2">
                <span class="p-icon p-icon-copyright"></span>
                <span>Todos os direitos reservados</span>
            </span>
        </div>
    `,
})
export class AppFooterComponent {}
