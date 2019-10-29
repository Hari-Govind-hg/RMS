import { Component, OnInit, Directive } from '@angular/core';
import { Subscription, Observable } from 'rxjs';
import { JobServiceService } from '../HRservice/job-service.service';
import { AuthenticationService, UserService } from '../HRservice/loginservice';
import { User } from '../HRservice/models';
import { NavService } from '../../nav/nav.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
@Directive({
  exportAs:'{{job.jobId}}'
})
export class JobComponent implements OnInit {
  jobList:any[];
  myControl:FormGroup;
  jobSubscription:Subscription;
  currentUser: User;
  jobId:String;
  currentUserSubscription: Subscription;
  isLoggedIn$: Observable<boolean>;
  duplicateJobData:any;
  isDeleted:boolean;
  isSaved:boolean;
  jobData:any;
  skill:String;
  isEmpty:boolean;

  constructor(private jobService: JobServiceService,private authenticationService: AuthenticationService,
    private userService: UserService,public nav: NavService,private router:Router,private route:ActivatedRoute) 
    { const _jobId = this.route.snapshot.params.id;
      this.myControl=new FormGroup({
        skill:new FormControl('')
      });
      this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
  });
}

  ngOnInit() {
    this.nav.show();
    console.log("inside ngOnInit");
    this.isLoggedIn$ = this.authenticationService.loggedIn;
    console.log(this.isLoggedIn$);

    // Calls all the jobs in the db
    this.jobSubscription= this.jobService.getJobs()
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
  });
  const _jobId = this.route.snapshot.params.id;
  console.log(_jobId);
  }

  onEditHandler(id){
    this.jobSubscription= this.jobService.getJobById(id)
    .subscribe((res:any)=>{
      console.log(res);
      this.duplicateJobData = res;
      console.log(this.duplicateJobData.jAppliedCandidateList);
      if(this.duplicateJobData.jAppliedCandidateList==""){
        this.isEmpty = true;
      }
      else{
        this.isEmpty = false;
      }
    });
    }

    onSearchHandler(searchForm){
      console.log(searchForm.value.skill.toLowerCase());
      this.skill = searchForm.value.skill;
      if(this.skill==""){
        this.jobSubscription= this.jobService.getJobs()
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
  });
      }
      else{
      this.jobSubscription= this.jobService.getJobBySkill(this.skill)
      .subscribe((res:any[])=>{
        console.log(res);
        this.jobList = res;
    });
  }
  
    }

  async onUpdateHandler(formData){
    console.log(formData);
    console.log(formData.value);
    var obj= formData.value;
    obj.jId=this.jobId;

    //use promise based submission
    let res = await this.jobService.updateJob(this.duplicateJobData);

    const _jobId = this.route.snapshot.params.id;
    console.log("The id is:"+_jobId);

    if(res){
      this.isSaved = true;
      console.log(res);
      this.jobSubscription= this.jobService.getJobs()
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
  });
    }
  }

  async onDeleteHandler(id){
    console.log(id);
    let res = await this.jobService.deleteJob(id);
    if(res){
      this.isDeleted = true;
      this.jobSubscription= this.jobService.getJobs()
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
  });
    }
  }


  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.currentUserSubscription.unsubscribe();
    this.isLoggedIn$ = this.authenticationService.loggedIn;
    console.log(this.isLoggedIn$);
}
}