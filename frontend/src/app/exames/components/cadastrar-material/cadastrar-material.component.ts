import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { atividadeDropdown } from '../../models/dropdowns/atividade.dropdown';
import { MaterialDeAnalise } from '../../models/subjects/material-de-analise';
import { MaterialDeAnaliseService } from '../../services/material-de-analise.service';

@Component({
  selector: 'app-cadastrar-material',
  templateUrl: './cadastrar-material.component.html',
  styleUrls: ['./cadastrar-material.component.css']
})
export class CadastrarMaterialComponent implements OnInit {

  atividade = atividadeDropdown;

  constructor(
    private fb: FormBuilder,
    private materialService: MaterialDeAnaliseService,
  ) { }

  materialDeAnalise = this.fb.group({
    nome: [null, Validators.required],
    ativo: [null, Validators.required],
    coletavel: [null, Validators.required],
    exigeInformacao: [null, Validators.required],
    urina: [null, Validators.required],
  })

  ngOnInit(): void {
  }

  validarCampos(): Boolean{
    return this.materialDeAnalise.valid;
  }

  cadastrar() {

    let materialAnalise = this.materialDeAnalise.value;

    let material: MaterialDeAnalise = {
      nome: materialAnalise.nome,
      ativo: materialAnalise.ativo,
      coletavel: materialAnalise.coletavel,
      exigeInformacao: materialAnalise.exigeInformacao,
      urina: materialAnalise.urina,
    }

    this.materialService.cadastrarMaterial(material).subscribe();

    this.materialDeAnalise.reset();

  }

  limpar() {
    this.materialDeAnalise.reset();
  }

}