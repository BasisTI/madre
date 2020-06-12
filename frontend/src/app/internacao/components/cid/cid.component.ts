import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CID } from '@internacao/models/cid';
import { CidService } from '@internacao/services/cid.service';
import { SelectItem } from 'primeng';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'app-cid',
    templateUrl: './cid.component.html',
})
export class CidComponent implements OnInit {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public showClear = false;
    @Input() public name: string;
    @Input() public label: string;
    @Input() public showTree = false;
    @Output() public select = new EventEmitter();
    public cids: SelectItem[];

    constructor(private cidService: CidService) {
    }

    ngOnInit(): void {
        this.cidService.getPais().subscribe(cids => this.cids = this.cidService.getSelectItemArrayFrom(cids));
    }

    public getFilhosPeloIdDoPai(id: number): void {
        this.cidService.getFilhosPeloIdDoPai(id).subscribe(cids => this.cids = this.cidService.getSelectItemArrayFrom(cids));
    }

    public aoSelecionarCid(evento: { originalEvent: MouseEvent, value: CID }): void {
        this.select.emit(evento.value);
    }

    public aoSelecionarCidNaArvore(cid: CID): void {
        this.select.emit(this.parentFormGroup.get(this.name).value);
        this.parentFormGroup.get(this.name).setValue(cid);
    }
}
