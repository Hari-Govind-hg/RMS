import { Component, OnInit,OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import { contactServiceService } from '../contact-service.service';
import { ActivatedRoute, Router } from '@angular/router';

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
  constructor(private contactServiceService:contactServiceService,private route:ActivatedRoute,private router:Router) { }

  ngOnInit() {
    // const _contactId = this.route.snapshot.params.id;
    this.contactSubscription= this.contactServiceService.getContacts()
    .subscribe((res:any)=>{
      console.log(res);
      this.contactList = res;
    });
  }

  
  async onDeleteHandler(id){
    // const id = this.route.snapshot.params.id;
    console.log(id);
    let res = await this.contactServiceService.deleteContact(id);
    if(res){
      this.isDeleted = true;
      this.ngOnInit();
    }
  }

  ngOnDestroy(){
    console.log("Inside ContactById destroy");
    this.contactSubscription.unsubscribe();
  }
  
 

}