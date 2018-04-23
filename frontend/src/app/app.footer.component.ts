import { Component, Inject, forwardRef } from '@angular/core';
import { AppComponent } from './app.component';

@Component({
  selector: 'app-footer',
  template: `
    <div class="footer">
        <div class="card clearfix">
          <span class="footer-text-left">BASIS Tecnologia</span>
          <span class="footer-text-right"><span class="ui-icon ui-icon-copyright"></span>  <span>All Rights Reserved</span></span>
        </div>
    </div>
  `
})
export class AppFooterComponent { }
