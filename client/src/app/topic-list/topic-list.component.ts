import {Component, Inject, OnInit} from '@angular/core';
import {TopicService} from '../shared/topic/topic.service';
import {GiphyService} from '../shared/giphy/giphy.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

  topics: Array<any>;

  constructor(private topicService: TopicService, private giphyService: GiphyService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.topicService.getAll().subscribe(data => {
      this.topics = data;
      for (const topic of this.topics) {
        this.giphyService.get(topic.name).subscribe(url => topic.giphyUrl = url);
      }
    });
  }

  remove(id) {
    this.topicService.remove(id).subscribe(result => {
      for (let i = 0; i < this.topics.length; i++) {
        if (this.topics[i].id === id) {
          this.topics.splice(i, 1);
          break;
        }
      }
    }, error => console.error(error));
  }

  openCommentDialog(topic) {
    this.dialog.open(DialogDataExampleDialog, {
      width: '80%',
      data: {
        'topic': topic,
        'comment': {}
      }
    });
  }
}

@Component({
  selector: 'dialog-data-example-dialog',
  templateUrl: 'dialog-comment.html',
})
export class DialogDataExampleDialog {
  constructor(public dialogRef: MatDialogRef<DialogDataExampleDialog>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private topicService: TopicService) {
  }

  save(data: any) {
    this.topicService.addComment(data.topic, data.comment).subscribe(result => {
      data.topic.comments = result.comments;
      this.dialogRef.close();
    }, error => console.error(error));
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
