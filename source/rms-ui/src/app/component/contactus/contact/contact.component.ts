import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { contactServiceService } from '../contact-service.service';


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  mySubscription: any;
  contactForm: FormGroup;
  isSaved: boolean;
  loading = false;
  submitted = false;
  constructor(private contactServiceService: contactServiceService) {
  }

  ngOnInit() {
    this.contactForm = new FormGroup({
      name: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      subject: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required),

    });
  }

  get f() { return this.contactForm.controls; }
  async onSubmit() {
    this.submitted = true;
    console.log("Called the Submit");
    if (this.contactForm.invalid) {
      console.log("invalid");
      return;
    }
    else {

      //1. send the data to service
      let res: any = await this.contactServiceService.createContact(this.contactForm.value);
      console.log(res);
      //2. get the resp from service

      if (res) {
        this.isSaved = true;
        this.loading = true;
        alert("Message sent Successfully");
        this.contactForm = new FormGroup({
          name: new FormControl(''),
          email: new FormControl(''),
          subject: new FormControl(''),
          message: new FormControl(''),
    
        });
      }
    }
  }
}
