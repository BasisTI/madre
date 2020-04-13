import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-formulario-cadastro',
  templateUrl: './formulario-cadastro.component.html',
})
export class FormularioCadastroComponent implements OnInit, OnDestroy {
  formularioDeCadastro = this.fb.group({
    dadosPessoais: this.fb.group({
      nome: ['', Validators.required],
      nomeSocial: [''],
      sexo: ['', Validators.required],
      raca: ['', Validators.required],
      etnia: ['', Validators.required],
      estadoCivil: ['', Validators.required],
      prontuarioDaMae: [''],
      nomeDaMae: ['', Validators.required],
      nomeDoPai: ['', Validators.required],
      dataDeNascimento: ['', Validators.required],
      horaDoNascimento: [''],
      nacionalidade: ['', Validators.required],
      naturalidade: ['', Validators.required],
      grauDeInstrucao: ['', Validators.required],
      ocupacao: [''],
      religiao: [''],
      email: [''],
    }),
  });

  constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.breadcrumbService.setItems([
      { label: 'Pacientes', routerLink: 'pacientes' },
      { label: 'Cadastro de Paciente' },
    ]);
  }

  ngOnDestroy(): void {
    this.breadcrumbService.reset();
  }
}
