import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-medicamento',
  templateUrl: './medicamento.component.html',
  styleUrls: ['./medicamento.component.css']
})
export class MedicamentoComponent implements OnInit, OnDestroy {

  constructor(private breadcrumbService: BreadcrumbService) { }

  ngOnInit() {
    this.breadcrumbService.setItems([
        { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
        { label: 'Medicamento' }
    ]);
  }

  ngOnDestroy() {
    this.breadcrumbService.reset();
}

}
