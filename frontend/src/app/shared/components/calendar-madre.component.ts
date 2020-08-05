import { Component, Output, EventEmitter } from "@angular/core";
import { provideValueAccessor } from "@nuvem/angular-base";
import { CalendarComponent } from '@nuvem/primeng-components';

@Component({
    selector: "[madreCalendar]",
    templateUrl: "./calendar-madre.component.html",
    providers: [ provideValueAccessor(CalendarMadreComponent) ],
})
export class CalendarMadreComponent extends CalendarComponent {

    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;
    monthNavigator = true;
    yearNavigator = true;

    @Output() onBlur: EventEmitter<any> = new EventEmitter<any>();

    @Output() onSelect: EventEmitter<any> = new EventEmitter<any>();

    onBlurEvent(event) {
        this.onBlur.emit(event);
    }

    onSelectEvent(event) {
        this.onSelect.emit(event);
    }

}
