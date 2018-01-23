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
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  save(data: any) {
    // this.topicService.save(form).subscribe(result => {
    //   this.gotoList();
    // }, error => console.error(error));
    console.log(data);

    if (data.topic.comments) {
      data.topic.comments.push(data.comment);
    } else {
      data.topic.comments = [data.comment];
    }

    this.dialogRef.close();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
