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
    this.isSaving = true;
    //   let unidadedeSaude = [
    //     document.getElementById("pesquisaUS").nodeValue,
    //     document.getElementById("incluirUS").nodeValue,
    //     document.getElementById("alterarUS").nodeValue,
    //     document.getElementById("exluirUS").nodeValue,
    //     document.getElementById("visualizarUS").nodeValue
    //   ];
    // unidadedeSaude.forEach(cb =>{
    //   console.log("checkboxs: "+cb);
    // });
    // var pes = document.getElementById("pesquisaUS");
    // var inc = document.getElementById("incluirUS");
    // var alt = document.getElementById("alterarUS");
    
    let p = document.getElementById("pesquisaUS");
    let i = document.getElementById("incluirUS");
    let a = document.getElementById("alterarUS");
    console.log(p);
    console.log(i);
    console.log(a);

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

// let unidadedeSaude = [
    let p = document.getElementById("pesquisaUS");
    let i = document.getElementById("incluirUS");
    let a = document.getElementById("alterarUS");
    console.log(p);
    console.log(i);
    console.log(a);
//   document.getElementById("exluirUS"),
//   document.getElementById("visualizarUS")
// ];

// unidadedeSaude.forEach(cb =>{
//   console.log("checkboxs: "+cb);
// });

// function pegaCheckBox(){
//   let r;
//   if (document.getElementById("pesquisaUS").isChe){
//     r = true;
//   }else{
//     r = false;
//   }
//   console.log("Valor da funcao: " + r);
// }

// function ligarEvento(event){
//   if(event.target.checked){
//     this.contentEditable = true;
//   }
// }

function ligarEvento(){
  document.getElementById("pesquisaUS").click();
  console.log("cliclk");
}