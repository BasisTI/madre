import { Component, Input, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { MenuItem } from 'primeng/primeng';
import { AppComponent } from './app.component';

@Component({
    selector: 'app-menu',
    template: `
        <ul
            app-submenu
            [item]="model"
            root="true"
            class="ultima-menu ultima-main-menu clearfix"
            [reset]="reset"
            visible="true"
            parentActive="true"
        ></ul>
    `,
})
export class AppMenuComponent implements OnInit {
    @Input() reset: boolean;

    model: any[];

    constructor(public app: AppComponent) {}

    ngOnInit() {
        this.model = [
            { label: 'Dashboard', icon: 'dashboard', routerLink: ['/'] },
            {
                label: 'Pacientes',
                icon: 'dashboard',
                routerLink: ['/pacientes'],
                items: [
                    {
                        label: 'Cadastro de Paciente',
                        icon: 'add',
                        routerLink: ['/pacientes/cadastro'],
                    },
                    {
                        label: 'Solicitação de Internação',
                        icon: 'add',
                        routerLink: ['/pacientes/solicitacao-de-internacao'],
                    },
                ],
            },
            {
                label: 'Emergência',
                icon: 'dashboard',
                routerLink: ['/paciente'],
                items: [
                    {
                        label: 'Triagem',
                        icon: 'add',
                        routerLink: ['pacientes/triagem'],
                    },
                ],
            },

            {
                label: 'Prescrição',
                icon: 'assignment_ind',
                items: [
                    {
                        label: 'Médica',
                        icon: 'remove',
                        items: [{ label: 'Prescrever', routerLink: ['/prescricao-medica'] }],
                    },
                    {
                        label: 'Enfermagem',
                        icon: 'remove',
                        items: [{ label: 'Prescrever' }],
                    },
                    {
                        label: 'Multiprofisisonal',
                        icon: 'remove',
                        items: [{ label: 'Prescrever' }],
                    },
                ],
            },
        ];
    }

    changeTheme(theme) {
        const themeLink: HTMLLinkElement = <HTMLLinkElement>document.getElementById('theme-css');
        const layoutLink: HTMLLinkElement = <HTMLLinkElement>document.getElementById('layout-css');

        themeLink.href = 'assets/theme/theme-' + theme + '.css';
        layoutLink.href = 'assets/layout/css/layout-' + theme + '.css';
    }
}

@Component({
    /* tslint:disable:component-selector */
    selector: '[app-submenu]',
    /* tslint:enable:component-selector */
    template: `
        <ng-template ngFor let-child let-i="index" [ngForOf]="root ? item : item.items">
            <li
                [ngClass]="{ 'active-menuitem': isActive(i) }"
                [class]="child.badgeStyleClass"
                *ngIf="child.visible === false ? false : true"
            >
                <a
                    [href]="child.url || '#'"
                    (click)="itemClick($event, child, i)"
                    (mouseenter)="onMouseEnter(i)"
                    class="ripplelink"
                    *ngIf="!child.routerLink"
                    [attr.tabindex]="!visible ? '-1' : null"
                    [attr.target]="child.target"
                >
                    <i *ngIf="child.icon" class="material-icons">{{ child.icon }}</i>
                    <span>{{ child.label }}</span>
                    <span class="menuitem-badge" *ngIf="child.badge">{{ child.badge }}</span>
                    <i class="material-icons submenu-icon" *ngIf="child.items"
                        >keyboard_arrow_down</i
                    >
                </a>

                <a
                    (click)="itemClick($event, child, i)"
                    (mouseenter)="onMouseEnter(i)"
                    class="ripplelink"
                    *ngIf="child.routerLink"
                    [routerLink]="child.routerLink"
                    routerLinkActive="active-menuitem-routerlink"
                    [routerLinkActiveOptions]="{ exact: true }"
                    [attr.tabindex]="!visible ? '-1' : null"
                    [attr.target]="child.target"
                >
                    <i *ngIf="child.icon" class="material-icons">{{ child.icon }}</i>
                    <span>{{ child.label }}</span>
                    <span class="menuitem-badge" *ngIf="child.badge">{{ child.badge }}</span>
                    <i class="material-icons submenu-icon" *ngIf="child.items"
                        >keyboard_arrow_down</i
                    >
                </a>
                <div class="layout-menu-tooltip">
                    <div class="layout-menu-tooltip-arrow"></div>
                    <div class="layout-menu-tooltip-text">
                        {{ child.label }}
                    </div>
                </div>
                <ul
                    app-submenu
                    [item]="child"
                    *ngIf="child.items"
                    [visible]="isActive(i)"
                    [reset]="reset"
                    [parentActive]="isActive(i)"
                    [@children]="
                        (app.isSlim() || app.isHorizontal()) && root
                            ? isActive(i)
                                ? 'visible'
                                : 'hidden'
                            : isActive(i)
                            ? 'visibleAnimated'
                            : 'hiddenAnimated'
                    "
                ></ul>
            </li>
        </ng-template>
    `,
    animations: [
        trigger('children', [
            state(
                'hiddenAnimated',
                style({
                    height: '0px',
                }),
            ),
            state(
                'visibleAnimated',
                style({
                    height: '*',
                }),
            ),
            state(
                'visible',
                style({
                    height: '*',
                    'z-index': 100,
                }),
            ),
            state(
                'hidden',
                style({
                    height: '0px',
                    'z-index': '*',
                }),
            ),
            transition(
                'visibleAnimated => hiddenAnimated',
                animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'),
            ),
            transition(
                'hiddenAnimated => visibleAnimated',
                animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'),
            ),
        ]),
    ],
})
export class AppSubMenuComponent {
    @Input() item: MenuItem;

    @Input() root: boolean;

    @Input() visible: boolean;

    _reset: boolean;

    _parentActive: boolean;

    activeIndex: number;

    constructor(public app: AppComponent) {}

    itemClick(event: Event, item: MenuItem, index: number) {
        if (this.root) {
            this.app.menuHoverActive = !this.app.menuHoverActive;
        }

        // avoid processing disabled items
        if (item.disabled) {
            event.preventDefault();
            return true;
        }

        // activate current item and deactivate active sibling if any
        this.activeIndex = this.activeIndex === index ? null : index;

        // execute command
        if (item.command) {
            item.command({ originalEvent: event, item: item });
        }

        // prevent hash change
        if (item.items || (!item.url && !item.routerLink)) {
            setTimeout(() => {
                this.app.layoutMenuScrollerViewChild.moveBar();
            }, 450);
            event.preventDefault();
        }

        // hide menu
        if (!item.items) {
            if (this.app.isHorizontal() || this.app.isSlim()) {
                this.app.resetMenu = true;
            } else {
                this.app.resetMenu = false;
            }

            this.app.overlayMenuActive = false;
            this.app.staticMenuMobileActive = false;
            this.app.menuHoverActive = !this.app.menuHoverActive;
        }
    }

    onMouseEnter(index: number) {
        if (
            this.root &&
            this.app.menuHoverActive &&
            (this.app.isHorizontal() || this.app.isSlim()) &&
            !this.app.isMobile() &&
            !this.app.isTablet()
        ) {
            this.activeIndex = index;
        }
    }

    isActive(index: number): boolean {
        return this.activeIndex === index;
    }

    @Input() get reset(): boolean {
        return this._reset;
    }

    set reset(val: boolean) {
        this._reset = val;

        if (this._reset && (this.app.isHorizontal() || this.app.isSlim())) {
            this.activeIndex = null;
        }
    }

    @Input() get parentActive(): boolean {
        return this._parentActive;
    }

    set parentActive(val: boolean) {
        this._parentActive = val;

        if (!this._parentActive) {
            this.activeIndex = null;
        }
    }
}
