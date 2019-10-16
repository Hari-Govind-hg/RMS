import { Component, OnInit } from '@angular/core';
import { JobServiceService } from '../../job-service.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-jobdetail',
  templateUrl: './jobdetail.component.html',
  styleUrls: []
})
export class JobdetailComponent implements OnInit {
  jobData:any;
  duplicateJobData:any;
  jobId:String;
  jobSubscription:Subscription;
  isSaved:boolean=false;
  constructor(private jobService:JobServiceService,private route:ActivatedRoute) { }

  ngOnInit() {
    const _jobId = this.route.snapshot.params.id;
    this.jobSubscription= this.jobService.getJobById(_jobId)
    .subscribe((res:any)=>{
      console.log(res);
      this.jobData = res;
    });
  }

   onEditHandler(){
    //duplicating object
    this.duplicateJobData=JSON.parse(JSON.stringify(this.jobData));
    console.log(this.duplicateJobData);
    }

  async onUpdateHandler(formData){
    console.log(formData);
    console.log(formData.value);
    var obj= formData.value;
    obj.id=this.jobId;

    //use promise based submission
    let res = await this.jobService.updateJob(this.duplicateJobData);

    if(res){
      this.isSaved = true;
    }
  }

  async onDeleteHandler(){
    const _jobId = this.route.snapshot.params.id;
    console.log(_jobId);
    let res = await this.jobService.deleteJob(_jobId);
  }

  ngOnDestroy(){
    console.log("Inside JobById destroy");
    this.jobSubscription.unsubscribe();
  }

}
