import { Component, OnInit,OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import { contactServiceService } from '../contact-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ReplyComponent } from '../reply/reply.component';

@Component({
  selector: 'app-query-list',
  templateUrl: './query-list.component.html',
  styleUrls: ['./query-list.component.css']
})
export class QuerylistComponent implements OnInit,OnDestroy {
  contactList:FormGroup;
  duplicateContactData:any;
  id:String;
  contactSubscription:Subscription;
  isSaved:boolean=false;
  isDeleted:boolean=false;

 
  constructor(private  replycomp: ReplyComponent,private contactServiceService:contactServiceService,private route:ActivatedRoute,private router:Router) { }

  ngOnInit() {
    
    this.contactSubscription= this.contactServiceService.getContacts()
    .subscribe((res:any)=>{
     
      this.contactList = res;
    });
  }

  
  async onDeleteHandler(id){
    
    
    let res = await this.contactServiceService.deleteContact(id);
    if(res){
      this.isDeleted = true;
      this.ngOnInit();
    }
  }

  async onReplyHandler(email){
   
    this.router.navigate(['/reply/'+email]);
  }

  ngOnDestroy(){
    
    this.contactSubscription.unsubscribe();
  }
  
 

}