import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class TopicService {

  public API = '//localhost:8080/api';
  public TOPICS_API = this.API + '/topics';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.TOPICS_API);
  }

  get(id: string) {
    return this.http.get(this.TOPICS_API + '/' + id);
  }

  save(topic: any): Observable<any> {
    let result: Observable<Object>;
    if (topic['href']) {
      result = this.http.put(topic.href, topic);
    } else {
      result = this.http.post(this.TOPICS_API, topic);
    }
    return result;
  }

  remove(href: string) {
    return this.http.delete(href);
  }
}
