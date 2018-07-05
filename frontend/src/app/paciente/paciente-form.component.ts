import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';
import {DropdownModule} from 'primeng/dropdown';
import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Paciente } from './paciente.model';
import { PacienteService } from './paciente.service';



@Component({
  selector: 'jhi-paciente-form',
  templateUrl: './paciente-form.component.html'
})
export class PacienteFormComponent implements OnInit, OnDestroy {
  paciente: Paciente;
  isSaving: boolean;
  estadoCivil: SelectItem[];
  sexo: SelectItem[];
  nacionalidade: SelectItem[];
  racaCor: SelectItem[];
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private pacienteService: PacienteService,
  ) {}


  ngOnInit() {
    
    
    this.sexo = [
      {label: 'Masculino', value: 'M'},
      {label: 'Feminino', value: 'F'}
  ]

    this.nacionalidade = [
      {label: 'Brasileira', value: 'brasileira'},
      {label: 'Estrangeira', value: 'estrangeira'}
]

    this.estadoCivil = [
      {label: 'Solteiro', value: 'solteiro'},
      {label: 'Casado', value: 'casado'},
      {label: 'Separado', value: 'separado'},
      {label: 'Divorciado', value: 'divorciado'},
      {label: 'Viúvo', value: 'viuvo'}
]

    this.racaCor = [
      {label: 'Branco', value: 'branco'},
      {label: 'Preto', value: 'preto'},
      {label: 'Pardo', value: 'pardo'},
      {label: 'Amarelo', value: 'amarelo'},
      {label: 'Indígena', value: 'indigena'}
]



    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.paciente = new Paciente();
      if (params['id']) {
        this.isEdit = true;
        this.pacienteService.find(params['id']).subscribe(paciente => this.paciente = paciente);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Pacientes', routerLink: '/paciente' },
        { label: title }
      ]);
    });
  }

  save() {
    console.log(this.paciente);
    this.isSaving = true;
    if (this.paciente.id !== undefined) {
      this.subscribeToSaveResponse(this.pacienteService.update(this.paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(this.paciente));
    }
  }

  private subscribeToSaveResponse(result: Observable<Paciente>) {
    result.subscribe((res: Paciente) => {
      this.isSaving = false;
      this.router.navigate(['/paciente']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
      this.pageNotificationService.addErrorMessage('Dados inválidos!');
    });
  }

  private addConfirmationMessage() {
    if (this.isEdit) {
      this.pageNotificationService.addUpdateMsg();
    } else {
      this.pageNotificationService.addCreateMsg();
    }
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
    this.breadcrumbService.reset();
  }
}