import { Component, OnInit, Input } from '@angular/core';
import { CID } from '@internacao/models/cid';
import { CidService } from '@internacao/services/cid.service';
import { SelectItem } from 'primeng';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'app-cid',
    templateUrl: './cid.component.html',
})
export class CidComponent implements OnInit {
    @Input() parentFormGroup: FormGroup;
    @Input() required = false;
    @Input() showClear = false;
    @Input() name: string;
    @Input() label: string;
    cids: Array<SelectItem>;

    constructor(private cidService: CidService) {}

    ngOnInit(): void {
        this.cidService.getCids().subscribe((cids: Array<CID>) => {
            this.cids = this.cidService.getSelectItemArrayFrom(cids);
        });
    }

    onSelect(cid: CID): void {
        this.parentFormGroup.get(this.name).setValue(cid);
    }
}
