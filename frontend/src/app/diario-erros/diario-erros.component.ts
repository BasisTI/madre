import { Component, OnDestroy, OnInit } from '@angular/core';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';

@Component({
  selector: 'app-diario-erros',
  templateUrl: './diario-erros.component.html'
})
export class DiarioErrosComponent implements OnInit, OnDestroy {

  constructor(private breadcrumbService: BreadcrumbService) {}

  ngOnInit() {
    this.breadcrumbService.setItems([{ label: 'Diário de Erros' }]);
  }

  ngOnDestroy() {
    this.breadcrumbService.reset();
  }
}
