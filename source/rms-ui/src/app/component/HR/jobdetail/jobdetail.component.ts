import { Component, OnInit } from '@angular/core';
import { JobServiceService } from '../HRservice/job-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-jobdetail',
  templateUrl: './jobdetail.component.html',
  styleUrls: ['./jobdetail.component.css']
})
export class JobdetailComponent implements OnInit {
  jobData:any;
  duplicateJobData:any;
  jobId:String;
  jobSubscription:Subscription;
  isSaved:boolean=false;
  isDeleted:boolean=false;
  constructor(private jobService:JobServiceService,private route:ActivatedRoute,private router:Router) { }

  ngOnInit() {
    const _jobId = this.route.snapshot.params.id;
    this.jobSubscription= this.jobService.getJobById(_jobId)
    .subscribe((res:any)=>{
      
      this.jobData = res;
    });
  }

   onEditHandler(){
    //duplicating object
    this.duplicateJobData=JSON.parse(JSON.stringify(this.jobData));
   }

  async onUpdateHandler(formData){
   
    var obj= formData.value;
    obj.id=this.jobId;

    //use promise based submission
    let res = await this.jobService.updateJob(this.duplicateJobData);

    const _jobId = this.route.snapshot.params.id;
    

    if(res){
      this.isSaved = true;
      
      this.router.navigate(['/jobs']);
    }
  }

  async onDeleteHandler(){
    const _jobId = this.route.snapshot.params.id;
    
    let res = await this.jobService.deleteJob(_jobId);
    if(res){
      this.isDeleted = true;
      this.router.navigate(['/jobs']);
    }
  }

  ngOnDestroy(){
    
    this.jobSubscription.unsubscribe();
  }

}
