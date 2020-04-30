import { CID } from '@internacao/models/cid';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { CidService } from '@internacao/services/cid.service';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';

@Component({
    selector: 'app-cid',
    templateUrl: './cid.component.html',
})
export class CidComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public name = 'cid';
    @Input() public label = 'CID';
    public filhos = new Array<CID>();

    constructor(private cidService: CidService) {}

    ngOnInit(): void {
        this.cidService.getCIDS(true, 'descricao').subscribe((filhos: Array<CID>) => {
            this.filhos = filhos.filter((cid: CID) => cid.pai);
        });
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.cidService
            .getCIDSPorDescricao(evento.query, true, 'descricao')
            .subscribe((filhos: Array<CID>) => {
                this.filhos = filhos.filter((cid: CID) => cid.pai);
            });
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get('cid').value) {
            this.parentFormGroup.get('cid').setValue(null);
        }
    }
}
