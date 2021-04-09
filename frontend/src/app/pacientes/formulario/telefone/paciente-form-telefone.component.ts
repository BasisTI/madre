import { DDDService } from './ddd.service';
import { OPCOES_DE_TIPO_DE_TELEFONE } from './../../models/dropdowns/opcoes-de-tipo-de-telefone';
import { DatatableClickEvent, BreadcrumbService } from '@nuvem/primeng-components';
import { Component, Input } from "@angular/core";
import { FormArray, FormBuilder } from "@angular/forms";
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { DDD } from '../../models/dropdowns/types/DDD';

@Component({
    selector: 'paciente-form-telefone',
    templateUrl: './paciente-form-telefone.component.html',
})
export class PacienteTelefoneFormComponent {

    @Input()
    public telefones: any = FormArray;

    public validaTelefone: boolean;

    public listaDDD: DDD[] = [];

    public opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    public tipoDeTelefoneSelecionado: string;

    public tipoMascara: string = "9999-9999"; 

    public telefone = this.fb.group({
        id: [null],
        ddd:[null],
        numero: [null],
        tipo: [null],
        observacao: [null],
        indice: [null]
    });

    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService, protected dddService: DDDService) {
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
                    this.validaTelefone = true;
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
        this.validaTelefone = false;
        this.telefone.reset();
    }

    tipoTelefone(event){
        this.tipoDeTelefoneSelecionado = event.value;
        if(this.tipoDeTelefoneSelecionado === 'CELULAR'){
            this.tipoMascara = "9 9999-9999";
        }
        else{
            this.tipoMascara = "9999-9999";
        }
    }

    atualizarDDD(evento){
        this.telefone.patchValue({
            ddd: evento.value.valor
        })
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
