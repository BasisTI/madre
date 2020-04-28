import { ArvoreCidService } from './arvore-cid.service';
import { TreeNode } from 'primeng/api';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-arvore-cid',
    templateUrl: './arvore-cid.component.html',
    styles: [],
})
export class ArvoreCidComponent implements OnInit {
    constructor(public service: ArvoreCidService) {}

    ngOnInit() {}
}
