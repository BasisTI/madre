import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';
import {NgxMaskModule} from 'ngx-mask';
import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { PreCadastro } from './pre-cadastro.model';
import { PreCadastroService } from './pre-cadastro.service';

@Component({
  selector: 'jhi-pre-cadastro-form',
  templateUrl: './pre-cadastro-form.component.html'
})
export class PreCadastroFormComponent implements OnInit, OnDestroy {
  preCadastro: PreCadastro;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private preCadastroService: PreCadastroService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.preCadastro = new PreCadastro();
      this.preCadastro.ativo = true;
      if (params['id']) {
        this.isEdit = true;
        this.preCadastroService.find(params['id']).subscribe(preCadastro => this.preCadastro = preCadastro);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Pre Cadastros', routerLink: '/preCadastro' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.preCadastro.id !== undefined) {
      this.subscribeToSaveResponse(this.preCadastroService.update(this.preCadastro));
    } else {
      this.subscribeToSaveResponse(this.preCadastroService.create(this.preCadastro));
    }
  }

  private subscribeToSaveResponse(result: Observable<PreCadastro>) {
    result.subscribe((res: PreCadastro) => {
      this.isSaving = false;
      this.router.navigate(['/preCadastro']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;

      if (res.headers.toJSON()['x-cadastrosbasicosapp-errorsusexists'] == "Cartão do SUS já cadastrado" ){
        this.pageNotificationService.addErrorMessage('Cartão do SUS já cadastrado para outro paciente!');
      } else if (res.headers.toJSON()['x-cadastrosbasicosapp-errorpacienteexists'] == "Paciente já cadastrado" ){
        this.pageNotificationService.addErrorMessage('Paciente já cadastrado(a)!');
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

  private addErrorMessage() {
    this.pageNotificationService.addErrorMessage("Dados inválidos!");
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
    this.breadcrumbService.reset();
  }
}
