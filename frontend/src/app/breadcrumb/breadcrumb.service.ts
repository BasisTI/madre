import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { MenuItem } from 'primeng/primeng';

@Injectable()
export class BreadcrumbService {

  private itemsSource = new Subject<MenuItem[]>();

  itemsHandler = this.itemsSource.asObservable();

  setItems(items: MenuItem[]) {
    this.itemsSource.next(items);
  }

  reset() {
    this.itemsSource.next([]);
  }
}
