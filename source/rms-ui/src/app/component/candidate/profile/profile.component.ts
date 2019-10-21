import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
    profile: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        
    ) {
    
    }

    ngOnInit() {
        this.profile = this.formBuilder.group({
            name: ['', Validators.required],
            email: ['', Validators.required],
            phone:['',Validators.required],
            street:['',Validators.required],
            town:['',Validators.required],
            state:['',Validators.required],
            country:['',Validators.required],
            pincode:['',Validators.required],
            experience:['',Validators.required]


        });

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() { return this.profile.controls; }

    onSave(){
      this.submitted = true;

        // stop here if form is invalid
        if (this.profile.invalid) {
            return;
        }

        this.loading = true;
    }
}
