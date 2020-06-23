import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-cirurgia',
    templateUrl: './cirurgia.component.html',
    styleUrls: ['./cirurgia.component.css'],
})
export class CirurgiaComponent implements OnInit {
    @Input() cirurgia: FormGroup;
    constructor() {}

    ngOnInit(): void {}
}
