import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Especialidade } from './especialidade.model';
import { EspecialidadeService } from './especialidade.service';

@Component({
  selector: 'jhi-especialidade-form',
  templateUrl: './especialidade-form.component.html'
})
export class EspecialidadeFormComponent implements OnInit, OnDestroy {
  especialidade: Especialidade;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private especialidadeService: EspecialidadeService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.especialidade = new Especialidade();
      if (params['id']) {
        this.isEdit = true;
        this.especialidadeService.find(params['id']).subscribe(especialidade => this.especialidade = especialidade);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Especialidades', routerLink: '/especialidade' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.especialidade.id !== undefined) {
      this.subscribeToSaveResponse(this.especialidadeService.update(this.especialidade));
    } else {
      this.subscribeToSaveResponse(this.especialidadeService.create(this.especialidade));
    }
  }

  private subscribeToSaveResponse(result: Observable<Especialidade>) {
    result.subscribe((res: Especialidade) => {
      this.isSaving = false;
      this.router.navigate(['/especialidade']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
      
      if (res.headers.toJSON()['x-cadastrosbasicosapp-errorespecialidadeexists'] == "Especialidade já cadastrada" ){
        this.pageNotificationService.addErrorMessage('Especialidade já cadastrada!');
      } else {
        this.pageNotificationService.addErrorMessage('Dados inválidos!');
      }

     
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
