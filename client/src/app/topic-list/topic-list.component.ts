import {Component, Inject, OnInit} from '@angular/core';
import {TopicService} from '../shared/topic/topic.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {AuthenticationService} from '../shared/auth/authentication.service';

@Component({
  selector: 'app-comment-dialog',
  templateUrl: 'dialog-comment.html',
})
export class CommentDialogComponent {
  constructor(public dialogRef: MatDialogRef<CommentDialogComponent>,
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

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

  topics: Array<any>;

  constructor(private topicService: TopicService,
              private authenticationService: AuthenticationService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.topicService.getAll().subscribe(data => {
      this.topics = data;
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
    this.dialog.open(CommentDialogComponent, {
      width: '80%',
      data: {
        'topic': topic,
        'comment': {}
      }
    });
  }

  userCanChange(topic: any) {
    if (topic && topic.author && topic.author.id) {
      const currentUserId = this.authenticationService.getCurrentUserId();
      if (currentUserId) {
        return currentUserId === topic.author.id;
      }
    }
    return true;
  }
}
