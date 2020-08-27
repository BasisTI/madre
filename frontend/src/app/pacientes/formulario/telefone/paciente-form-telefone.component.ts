import { DatatableClickEvent, BreadcrumbService } from '@nuvem/primeng-components';
import { Component, Input } from "@angular/core";
import { Validators, FormArray, FormBuilder } from "@angular/forms";

import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-telefone';

@Component({
    selector: 'paciente-form-telefone',
    templateUrl: './paciente-form-telefone.component.html',
})
export class PacienteTelefoneFormComponent {

    @Input()
    public telefones: any = FormArray;

    public opcoesDeTipoDeTelefone = OPCOES_DE_TIPO_DE_TELEFONE;

    public telefone = this.fb.group({
        id: [null],
        ddd: [null, Validators.required],
        numero: [null, Validators.required],
        tipo: [null, Validators.required],
        observacao: [null],
        indice: [null],
    });

    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService) {
    }


    adicionarTelefoneALista() {
        if (this.telefone.valid) {
            this.telefone.patchValue({indice: this.telefones.length});
            this.telefones.push(this.telefone);
            this.telefone = this.fb.group({
                id: [null],
                ddd: [null, Validators.required],
                numero: [null, Validators.required],
                tipo: [null, Validators.required],
                observacao: [null],
                indice: [null],
            });
        }
        this.telefone.reset();
    }

    datatableClick(event: DatatableClickEvent) {
        if (event.selection) {
            switch (event.button) {
                case "edit":
                    this.telefone.patchValue(this.telefones.controls[event.selection.indice].value);
                    console.log(this.telefones.controls[event.selection.indice].value);
                    console.log(event.selection);
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
        this.telefone.reset();
      }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
