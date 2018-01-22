import {Component, OnInit} from '@angular/core';
import {TopicService} from '../shared/topic/topic.service';


@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

  topics: Array<any>;

  constructor(private topicService: TopicService) {
  }

  ngOnInit() {
    this.topicService.getAll().subscribe(data => {
      this.topics = data;
    })
  }
}
