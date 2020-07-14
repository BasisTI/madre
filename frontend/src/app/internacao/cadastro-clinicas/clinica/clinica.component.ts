import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-clinica',
  templateUrl: './clinica.component.html',
  styleUrls: ['./clinica.component.css']
})
export class ClinicaComponent implements OnInit {

    clinica = this.fb.group({
    descricao: [null,Validators.required],
    capacidadeReferencial: [null, Validators.required],
    numeroSUS: [null],
    idadeMinimaInternacao: [null,Validators.required],
    idadeMaximaInternacao:  [null, Validators.required],
    idadeMinimaAmbulatorio: [null, Validators.required],
    idadeMaximaAmbulatorio: [null, Validators.required],
    });

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
  }

  isValid(): boolean{
    return false;
  }

  cadastrar(){

  }

}
