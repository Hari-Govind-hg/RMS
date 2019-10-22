import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-jobdetails',
  templateUrl: './jobdetails.component.html',
  styleUrls: ['./jobdetails.component.css']
})
export class JobdetailsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
  onApplyNowHandler(){
    console.log("applied successfully");
    alert("applied Successfully");
  }
}
