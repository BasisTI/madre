import { Telefone } from './../paciente.model';
import { OPCOES_DE_TIPO_DE_TELEFONE } from './../../models/dropdowns/opcoes-de-tipo-de-telefone';
import { DatatableClickEvent, BreadcrumbService } from '@nuvem/primeng-components';
import { Component, Input } from "@angular/core";
import { FormArray, FormBuilder } from "@angular/forms";
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
    selector: 'paciente-form-telefone',
    templateUrl: './paciente-form-telefone.component.html',
})
export class PacienteTelefoneFormComponent {

    @Input()
    public telefones: any = FormArray;

    public controle: boolean;

    public valueInput: string = "";
    public numero: string = "";
    public ddd: string = "";

    public opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    public selectedTypePhone: string;

    public typeMask: string = "(99) 9999-9999"; 

    public telefone = this.fb.group({
        id: [null],
        ddd: [null],
        numero: [null],
        tipo: [null],
        observacao: [null],
        indice: [null]
    });

    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService) {
    }


    adicionarTelefoneALista() {
        if (this.telefone.valid) {
            this.telefone.patchValue({indice: this.telefones.length});
            this.telefones.push(this.telefone);
            this.telefone = this.fb.group({
                id: [null],
                ddd: [null],
                numero: [null],
                tipo: [null],
                observacao: [null],
                indice: [null]
            });
        }
        this.telefone.reset();
    }

    datatableClick(event: DatatableClickEvent) {
        if (event.selection) {
            switch (event.button) {
                case "edit":
                    this.controle = true;
                    this.telefone.patchValue(this.telefones.controls[event.selection.indice].value);
                    break;
                case "delete":
                    this.telefones.removeAt(event.selection.indice);
                    var i = 0;
                    this.telefones.controls.forEach((atual) => atual.patchValue({indice: i++}));
                    break;
            }
        }
    }

    atualizarEdicao(): void {
        let atual = this.telefone.value;
        this.telefones.controls[atual.indice].patchValue(atual);
        this.controle = false;
        this.telefone.reset();
      }

    getPhoneFromInput(event:any){
        this.valueInput =  JSON.stringify(<HTMLElement>(event.target).value); 
        this.ddd = this.valueInput.substring(2,4);
        this.numero = this.valueInput.substring(5, this.valueInput.length-1);
        console.log(this.ddd)
        console.log(this.numero)
    }

    setTypePhone(event){
        this.selectedTypePhone = event.value;
        if(this.selectedTypePhone === 'CELULAR'){
            this.typeMask = "(99) 9 9999-9999";
        }
        else{
            this.typeMask = "(99) 9999-9999";
        }

    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
