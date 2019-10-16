import { JobComponent } from './component/HR/job/job.component';
import { AddjobComponent } from './component/HR/addjob/addjob.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { JobdetailComponent } from './component/HR/jobdetail/jobdetail.component';


const routes: Routes = [

  {path : '', component : JobComponent},
  {path : 'addjob', component : AddjobComponent},
  {path : 'jobs/:id', component : JobdetailComponent}
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
