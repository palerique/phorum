import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class CategoryService {

  public API = '//localhost:8080/api';
  public CATEGORIES_API = this.API + '/categories';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.CATEGORIES_API);
  }

  get(id: string) {
    return this.http.get(this.CATEGORIES_API + '/' + id);
  }

  save(category: any): Observable<any> {
    let result: Observable<Object>;
    if (category['href']) {
      result = this.http.put(category.href, category);
    } else {
      result = this.http.post(this.CATEGORIES_API, category);
    }
    return result;
  }

  remove(href: string) {
    return this.http.delete(href);
  }

}
