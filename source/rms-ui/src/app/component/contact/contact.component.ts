import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  contactForm: FormGroup;
  loading = false;
  submitted = false;
  constructor( private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.contactForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', Validators.required],
      subject: ['', Validators.required],
      message: ['', Validators.required]
     
  });
  }
  get f() { return this.contactForm.controls; }

  onSubmit(){
    this.submitted = true;
        console.log("Called the Submit");
        if (this.contactForm.invalid) {
          console.log("invalid");
          return;
      }
      else{
        this.loading=true;
       
      }
      alert("Sent Successfully");
    
  }
}
