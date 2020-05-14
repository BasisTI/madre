import { ModalidadeAssistencial } from '@internacao/models/modalidade-assistencial';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { ModalidadeAssistencialService } from '@internacao/services/modalidade-assistencial.service';

@Component({
    selector: 'app-modalidade-assistencial',
    templateUrl: './modalidade-assistencial.component.html',
})
export class ModalidadeAssistencialComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Hospital';
    @Input() public name = 'hospital';
    public modalidadesAssistenciais = new Array<ModalidadeAssistencial>();

    constructor(private modalidadeAssistencialService: ModalidadeAssistencialService) {}

    ngOnInit(): void {
        this.modalidadeAssistencialService
            .getModalidadesAssistenciais(true, 'nome')
            .subscribe(
                (modalidadesAssistenciais: Array<ModalidadeAssistencial>) =>
                    (this.modalidadesAssistenciais = modalidadesAssistenciais),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.modalidadeAssistencialService
            .getModalidadesAssistenciaisPorNome(evento.query, true, 'nome')
            .subscribe(
                (modalidadesAssistenciais: Array<ModalidadeAssistencial>) =>
                    (this.modalidadesAssistenciais = modalidadesAssistenciais),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
