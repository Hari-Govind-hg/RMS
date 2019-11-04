import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { contactReplyService } from '../contact-reply.service';

@Component({
  selector: 'app-reply',
  templateUrl: './reply.component.html',
  styleUrls: ['./reply.component.css']
})
export class ReplyComponent implements OnInit {

  //Step1: Create Form Group
  contactReplySubscription:Subscription;
  replyForm: FormGroup;
  isSaved:boolean;
  submitted=false;
  constructor(private contactReplyService: contactReplyService) {
    this.replyForm = new FormGroup({
      //Step2: Create Form Controls 
      email: new FormControl('', Validators.required),
      subject: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required),
      
    });
  }

  ngOnInit() {
  }
  async onSubmit() {
    this.submitted=true;
    // console.log(this.replyForm);
    // console.log(this.replyForm.value);
    console.log("click on submit");
    if (this.replyForm.invalid) {
                    console.log("invalid");
                    return;
          }
else{
    //1. send the data to service
    let res: any = await this.contactReplyService.contactReplyCreate(this.replyForm.value);
    console.log(res+"hii");
    //2. get the resp from service

    if(res){
      this.isSaved=true;
      alert("Message sent Successfully");
    }
  }
}
}
