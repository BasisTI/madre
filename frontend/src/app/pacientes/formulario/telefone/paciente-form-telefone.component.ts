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
    public telefones: FormArray;

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
                    console.log(this.telefones);
                    this.telefone.patchValue({
                        id: event.selection.id,
                        ddd: event.selection.ddd,
                        numero: event.selection.numero,
                        tipo: event.selection.tipo,
                        observacao: event.selection.observacao,
                    });
                    break;
                case "delete":
                    console.log(event.selection);
                    this.telefones.removeAt(event.selection.indice);
                    console.log(this.telefones);
                    break;
            }
        }
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
