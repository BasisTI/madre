import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { SituacaoAtivo } from "../../models/dropdowns/situacao.dropdown";
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

  teste: boolean;

  situacaoExame = SituacaoAtivo;

  materialSelecionado: number;
  amostraSelecionada: number;

  cadastrarExame = this.fb.group({
    nome: [null, Validators.required],
    nomeUsual: [null, Validators.required],
    sigla: [null, Validators.required],
    ativo: [true, Validators.required],
    impressao: [null, Validators.required],
    consisteInterfaceamento: [null, Validators.required],
    anexaDocumentos: [null, Validators.required]
  });


  valid(): boolean {
    if(this.cadastrarExame.valid && this.amostraSelecionada && this.materialSelecionado)
      return true;
    else
    return false;
  }

  limpar() {
    this.cadastrarExame.reset();
  }

  cadastrar(){
    const cadastroExame = this.cadastrarExame.value;

    const cadastro: ExamModel = {
      nome: cadastroExame.nome,
      nomeUsual: cadastroExame.nomeUsual,
      sigla: cadastroExame.sigla,
      materialExameId: this.materialSelecionado,
      amostraExameId: this.amostraSelecionada,
      ativo: cadastroExame.ativo,
      impressao: cadastroExame.impressao,
      consisteInterfaceamento: cadastroExame.consisteInterfaceamento,
      anexaDocumentos: cadastroExame.anexaDocumentos
    };

    console.log(cadastroExame);

    this.exameService.cadastrarExame(cadastro).subscribe();
    this.cadastrarExame.reset();
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