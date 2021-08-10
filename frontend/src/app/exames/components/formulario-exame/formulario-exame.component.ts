import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Amostra } from '../../models/subjects/amostra';
import { ExamModel } from '../../models/subjects/exames-model';
import { Material } from '../../models/subjects/material';
import { ExamesService } from '../../services/exames.service';

@Component({
  selector: 'app-formulario-exame',
  templateUrl: './formulario-exame.component.html',
  styleUrls: ['./formulario-exame.component.css']
})
export class FormularioExameComponent implements OnInit {

  constructor(private fb: FormBuilder,
              private exameService: ExamesService) { }

  materiais: Material[] = [];
  amostras: Amostra[] = [];



  cadastrarExame = this.fb.group({
    nome: [null, Validators.required],
    nomeUsual: [null, Validators.required],
    sigla: [null, Validators.required]
  });


  valid(): boolean {
    return this.cadastrarExame.valid;
  }

  cadastrar(){
    let cadastroExame = this.cadastrarExame.value;

    let cadastro: ExamModel = {
      nome: cadastroExame.nome,
      nomeUsual: cadastroExame.nomeUsual,
      sigla: cadastroExame.sigla
    };

    console.log(cadastroExame);

    this.exameService.cadastrarExame(cadastro).subscribe();
  }


  ngOnInit(): void {
    this.exameService.getMateriais().subscribe((response) => {
      this.materiais = response;
    });

    this.exameService.getAmostras().subscribe((response) => {
      this.amostras = response;
    });
  }

}