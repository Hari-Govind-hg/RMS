import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { contactReplyService } from '../contact-reply.service';
import { ActivatedRoute } from '@angular/router';

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
  emailId: any;
  tempForm:any;

  constructor(private contactReplyService: contactReplyService,private route:ActivatedRoute) 
  {
    this.emailId = route.snapshot.params.id;
    this.replyForm = new FormGroup({
      //Step2: Create Form Controls 
      email:new FormControl(''),
      subject: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required),
      
    });
  }
  ngOnInit() {
    
  }
  
  
 
  async onSubmit() {
    this.submitted=true;
   
    if (this.replyForm.invalid) {
                    
                    return;
          }
else{
    //1. send the data to service
    let res: any = await this.contactReplyService.contactReplyCreate(this.replyForm.value);
  
    //2. get the resp from service

    if(res){
      this.isSaved=true;
      alert("Message sent Successfully");
    }
  }
}
}
