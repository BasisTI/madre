import { Injectable } from '@angular/core';
import { Subject ,  Observable } from 'rxjs';
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
