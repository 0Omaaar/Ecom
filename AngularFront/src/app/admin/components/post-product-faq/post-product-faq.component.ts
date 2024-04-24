import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-post-product-faq',
  templateUrl: './post-product-faq.component.html',
  styleUrl: './post-product-faq.component.scss',
})
export class PostProductFaqComponent {
  productId: number = this.activatedRoute.snapshot.params['productId'];
  FAQForm!: FormGroup;

  constructor(
    private router: Router,
    private adminService: AdminService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.FAQForm = this.fb.group({
      question: [null, [Validators.required]],
      answer: [null, [Validators.required]],
    });
  }

  postFAQ() {
    this.adminService
      .postFaq(this.productId, this.FAQForm.value)
      .subscribe((res) => {
        if (res.id != null) {
          this.snackBar.open('FAQ Posted Successfully !', 'Close', {
            duration: 5000,
          });
          this.router.navigateByUrl('/admin/dashboard');
        } else {
          this.snackBar.open('Something Went Wrong !', 'Close', {
            duration: 5000,
            panelClass: 'error-snackbar',
          });
        }
      });
  }
}
