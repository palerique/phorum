import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {AuthenticationService} from '../auth/authentication.service';

@Injectable()
export class TopicService {

  public API = '//localhost:8080/api';
  public TOPICS_API = this.API + '/topics';

  private _options = {
    headers: new HttpHeaders({'Authorization': 'Bearer ' + this.authenticationService.token})
  };

  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.TOPICS_API, this._options);
  }

  get(id: string) {
    return this.http.get(this.TOPICS_API + '/' + id, this._options);
  }

  save(topic: any): Observable<any> {
    let result: Observable<Object>;
    if (topic['id']) {
      result = this.http.put(this.TOPICS_API, topic, this._options);
    } else {
      result = this.http.post(this.TOPICS_API, topic, this._options);
    }
    return result;
  }

  addComment(topic: any, comment: any): Observable<any> {
    let result: Observable<Object>;
    result = this.http.post(this.TOPICS_API + '/' + topic.id + '/comments', comment, this._options);
    return result;
  }

  remove(id: string) {
    return this.http.delete(this.TOPICS_API + '/' + id, this._options);
  }
}
