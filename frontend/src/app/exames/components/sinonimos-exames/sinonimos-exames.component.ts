import { Sinonimos } from './../../models/subjects/sinonimos';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SituacaoAtivo } from "../../models/dropdowns/situacao.dropdown";
import { SinonimosExamesService } from "../../services/sinonimo-exames.service";
import { ExamesService } from "../../services//exames.service";
import { ExamModel } from '../../models/subjects/exames-model';
import { ConfirmationService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';
import { Input } from '@angular/core';

@Component({
  selector: 'app-sinonimos-exames',
  templateUrl: './sinonimos-exames.component.html',
  styleUrls: ['./sinonimos-exames.component.css']
})
export class SinonimosExamesComponent implements OnInit {
  @Input() formGroup: FormGroup;
  sinonimos: Sinonimos[];
  exames: ExamModel[] = [];
  situacaoExame = SituacaoAtivo; 
  cadastroSinonimoExames :FormGroup;
  searchUrl = '/madreexames/api/sinonimos';
  


  constructor(
    private SinonimosService: SinonimosExamesService,
    private fb: FormBuilder,
    private examesService: ExamesService,
    private confirmationService: ConfirmationService,
    private route: ActivatedRoute,
    private router: Router, 
  
    ) { }

  ngOnInit(): void {
    this.cadastroSinonimoExames = this.fb.group({

      id:[null],
      nome:[null, Validators.required],
      situacao: [null, Validators.required],
  });

    this.SinonimosService.GetSinonimos().subscribe((response)=>{
      this.sinonimos = response; 

    this.SinonimosService.GetSinonimos()
    });
    
    
  }
  valid(): boolean {
    return this.cadastroSinonimoExames.valid;
  }

  cadastrar() { 

    let sinonimosExames = this.cadastroSinonimoExames.value;

    this.SinonimosService.cadastrarSinonimos(sinonimosExames).subscribe();
  }
   
}
