import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {ActivatedRoute, Router} from '@angular/router';
import {TopicService} from '../shared/topic/topic.service';
import {GiphyService} from '../shared/giphy/giphy.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-topic-edit',
  templateUrl: './topic-edit.component.html',
  styleUrls: ['./topic-edit.component.css']
})
export class TopicEditComponent implements OnInit, OnDestroy {
  topic: any = {};

  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private topicService: TopicService,
              private giphyService: GiphyService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.topicService.get(id).subscribe((topic: any) => {
          if (topic) {
            this.topic = topic;
            this.topic.href = topic._links.self.href;
            this.giphyService.get(topic.name).subscribe(url => topic.giphyUrl = url);
          } else {
            console.log(`Topic with id '${id}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/topic-list']);
  }

  save(form: NgForm) {
    this.topicService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }

  remove(href) {
    this.topicService.remove(href).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }
}
