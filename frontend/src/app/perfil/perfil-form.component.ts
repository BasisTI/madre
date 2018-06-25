import { Component, OnInit, OnDestroy, TRANSLATIONS_FORMAT } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem, CheckboxModule, CHECKBOX_VALUE_ACCESSOR } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Perfil } from './perfil.model';
import { PerfilService } from './perfil.service';

@Component({
  selector: 'jhi-perfil-form',
  templateUrl: './perfil-form.component.html'
})
export class PerfilFormComponent implements OnInit, OnDestroy {

  perfil: Perfil;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private perfilService: PerfilService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.perfil = new Perfil();
      // Para iniciar com false tem q setar antes, se n fica undefined
      //-------Unidade de Saude-------
      this.perfil.pesquisarUS  = false;
      this.perfil.incluirUS    = false;
      this.perfil.alterarUS    = false;
      this.perfil.excluirUS    = false;
      this.perfil.visualizarUS = false;
      //-------Usuario-------
      this.perfil.pesquisarU  = false;
      this.perfil.incluirU    = false;
      this.perfil.alterarU    = false;
      this.perfil.excluirU    = false;
      this.perfil.visualizarU = false;
      //-------Perfis Permissoes-------
      this.perfil.pesquisarPP  = false;
      this.perfil.incluirPP    = false;
      this.perfil.alterarPP    = false;
      this.perfil.excluirPP    = false;
      this.perfil.visualizarPP = false;
      //-------Pre Cadastro-------
      this.perfil.pesquisarPC  = false;
      this.perfil.incluirPC    = false;
      this.perfil.alterarPC    = false;
      this.perfil.excluirPC    = false;
      this.perfil.visualizarPC = false;
      //-------Triagem-------
      this.perfil.pesquisarT  = false;
      this.perfil.incluirT    = false;
      this.perfil.alterarT    = false;
      this.perfil.excluirT    = false;
      this.perfil.visualizarT = false;
      //-------Paciente-------
      this.perfil.pesquisarP  = false;
      this.perfil.incluirP    = false;
      this.perfil.alterarP    = false;
      this.perfil.excluirP    = false;
      this.perfil.visualizarP = false;
      if (params['id']) {
        this.isEdit = true;
        this.perfilService.find(params['id']).subscribe(perfil => this.perfil = perfil);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Perfils', routerLink: '/perfil' },
        { label: title }
      ]);
    });
  }

  save() {
    // this.perfil.unidadeSaude = [
    //   this.perfil.pesquisarUS, 
    //   this.perfil.incluirUS,
    //   this.perfil.alterarUS,
    //   this.perfil.excluirUS,
    //   this.perfil.visualizarUS
    // ];
    // console.log(this.unidadeSaude[0]);
    
    console.log("----------Unidade de Saude----------");
    console.log("Pesquisar: "  + this.perfil.pesquisarUS);
    console.log("Incluir: "    + this.perfil.incluirUS);
    console.log("Alterar: "    + this.perfil.alterarUS);
    console.log("Excluir: "    + this.perfil.excluirUS);
    console.log("Visualizar: " + this.perfil.visualizarUS);
    console.log("----------Usuario----------");
    console.log("Pesquisar: "  + this.perfil.pesquisarU);
    console.log("Incluir: "    + this.perfil.incluirU);
    console.log("Alterar: "    + this.perfil.alterarU);
    console.log("Excluir: "    + this.perfil.excluirU);
    console.log("Visualizar: " + this.perfil.visualizarU);
    console.log("----------Perfis Permissoes----------");
    console.log("Pesquisar: "  + this.perfil.pesquisarPP);
    console.log("Incluir: "    + this.perfil.incluirPP);
    console.log("Alterar: "    + this.perfil.alterarPP);
    console.log("Excluir: "    + this.perfil.excluirPP);
    console.log("Visualizar: " + this.perfil.visualizarPP);
    console.log("----------Pre Cadastro----------");
    console.log("Pesquisar: "  + this.perfil.pesquisarPC);
    console.log("Incluir: "    + this.perfil.incluirPC);
    console.log("Alterar: "    + this.perfil.alterarPC);
    console.log("Excluir: "    + this.perfil.excluirPC);
    console.log("Visualizar: " + this.perfil.visualizarPC);
    console.log("----------Triagem----------");
    console.log("Pesquisar: "  + this.perfil.pesquisarT);
    console.log("Incluir: "    + this.perfil.incluirT);
    console.log("Alterar: "    + this.perfil.alterarT);
    console.log("Excluir: "    + this.perfil.excluirT);
    console.log("Visualizar: " + this.perfil.visualizarT);
    console.log("----------Paciente----------");
    console.log("Pesquisar: "  + this.perfil.pesquisarP);
    console.log("Incluir: "    + this.perfil.incluirP);
    console.log("Alterar: "    + this.perfil.alterarP);
    console.log("Excluir: "    + this.perfil.excluirP);
    console.log("Visualizar: " + this.perfil.visualizarP);
    this.isSaving = true;
    if (this.perfil.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilService.update(this.perfil));
    } else {
      this.subscribeToSaveResponse(this.perfilService.create(this.perfil));
    }
  }

  private subscribeToSaveResponse(result: Observable<Perfil>) {
    result.subscribe((res: Perfil) => {
      this.isSaving = false;
      this.router.navigate(['/perfil']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
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
