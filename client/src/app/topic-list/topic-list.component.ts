import {Component, OnInit} from '@angular/core';
import {TopicService} from '../shared/topic/topic.service';
import {GiphyService} from '../shared/giphy/giphy.service';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

  topics: Array<any>;

  constructor(private topicService: TopicService, private giphyService: GiphyService) {
  }

  ngOnInit() {
    this.topicService.getAll().subscribe(data => {
      this.topics = data;
      for (const topic of this.topics) {
        this.giphyService.get(topic.name).subscribe(url => topic.giphyUrl = url);
      }
    });
  }
}
