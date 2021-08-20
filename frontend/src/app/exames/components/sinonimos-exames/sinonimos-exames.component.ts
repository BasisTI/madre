import { Sinonimos } from './../../models/subjects/sinonimos';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SituacaoExame } from "../../models/dropdowns/situacao.dropdown";
import { sinonimosExamesService } from "../../services/sinonimo-exames.service";
import { ExamesService } from "../../services//exames.service";
import { ExamModel } from '../../models/subjects/exames-model';
import { DatatableClickEvent, DatatableComponent } from '@nuvem/primeng-components';
import { ElasticQuery } from '@shared/elastic-query'
import { BreadcrumbService } from '@nuvem/primeng-components';
import { ViewChild } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-sinonimos-exames',
  templateUrl: './sinonimos-exames.component.html',
  styleUrls: ['./sinonimos-exames.component.css']
})
export class SinonimosExamesComponent implements OnInit {

  sinonimos: Sinonimos[];
  exames: ExamModel[] = [];
  situacaoExame = SituacaoExame; 
  cadastroSinonimoExames :FormGroup;
  searchUrl = '/madreexames/api/sinonimos';
  


  constructor(
    private sinonimosService: sinonimosExamesService,
    private fb: FormBuilder,
    private examesService: ExamesService,
    private confirmationService: ConfirmationService,
    private route: ActivatedRoute,
    private router: Router, 
    private service: sinonimosExamesService,
    private breadcrumbService: BreadcrumbService,
    ) { }
    elasticQuery: ElasticQuery = new ElasticQuery();
    @ViewChild(DatatableComponent) datatable: DatatableComponent;
    

  ngOnInit(): void {
    this.cadastroSinonimoExames = this.fb.group({

      id:[null],
      nome:[null, Validators.required],
      situacao: [null, Validators.required],
  });

    this.sinonimosService.GetSinonimos().subscribe((response)=>{
      this.sinonimos = response; 

    this.sinonimosService.GetSinonimos()
    });
    
    
  }
  valid(): boolean {
    return this.cadastroSinonimoExames.valid;
  }

  cadastrar() { 

    let sinonimosExames = this.cadastroSinonimoExames.value;

  this.sinonimosService.cadastrarSinonimos(sinonimosExames).subscribe();
  }
  pesquisar(){
    this.datatable.refresh(this.elasticQuery.query);
    
  } 

  ngOnDestroy(): void {
    this.breadcrumbService.reset();
}
  
  
}
