import { Component, OnInit } from '@angular/core';
import { NavService } from '../../nav/nav.service';

@Component({
  selector: 'app-home-image',
  templateUrl: './home-image.component.html',
  styleUrls: ['./home-image.component.css']
})
export class HomeImageComponent implements OnInit {

  constructor(public nav:NavService) { }

  ngOnInit() {
    this.nav.hide();
  }

}
