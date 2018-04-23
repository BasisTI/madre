import { Component, Input, OnInit, EventEmitter, ViewChild, trigger, state, transition, style, animate } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/primeng';

@Component({
  selector: 'app-inline-profile',
  template: `
    <div class="profile" [ngClass]="{'profile-expanded':active}">
      <a href="#" (click)="onClick($event)">
        <img class="profile-image" src="assets/layout/images/c3po.jpg" />
        <span class="profile-name">C-3PO</span>
        <i class="material-icons">keyboard_arrow_down</i>
      </a>
    </div>

    <ul class="ultima-menu profile-menu" [@menu]="active ? 'visible' : 'hidden'">
      <li role="menuitem">
        <a href="#" class="ripplelink" [attr.tabindex]="!active ? '-1' : null">
          <i class="material-icons">person</i>
          <span>Profile</span>
        </a>
      </li>
      <li role="menuitem">
        <a href="#" class="ripplelink" [attr.tabindex]="!active ? '-1' : null">
          <i class="material-icons">security</i>
          <span>Privacy</span>
        </a>
      </li>
      <li role="menuitem">
        <a href="#" class="ripplelink" [attr.tabindex]="!active ? '-1' : null">
          <i class="material-icons">settings_application</i>
          <span>Settings</span>
        </a>
      </li>
      <li role="menuitem">
        <a href="#" class="ripplelink" [attr.tabindex]="!active ? '-1' : null">
          <i class="material-icons">power_settings_new</i>
          <span>Logout</span>
        </a>
      </li>
    </ul>
  `,
  animations: [
    trigger('menu', [
      state('hidden', style({
        height: '0px'
      })),
      state('visible', style({
        height: '*'
      })),
      transition('visible => hidden', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
      transition('hidden => visible', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'))
    ])
  ]
})
export class InlineProfileComponent {

  active: boolean;

  onClick(event) {
    this.active = !this.active;
    event.preventDefault();
  }
}
