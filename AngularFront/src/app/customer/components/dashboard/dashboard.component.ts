import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent {
  products: any[] = [];
  searchProductForm!: FormGroup;

  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
      title: [null, [Validators.required]],
    });
  }

  getAllProducts() {
    this.products = [];
    this.customerService.getAllProducts().subscribe((res) => {
      res.forEach((element) => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImg;
        this.products.push(element);
      });
    });
  }

  submitForm() {
    const title = this.searchProductForm.get('title').value;
    this.products = [];
    // console.log(title)
    this.customerService.getAllProductsByName(title).subscribe((res) => {
      res.forEach((element) => {
        element.processedImg = 'data:image/jpeg;base64,' + element.byteImg;
        this.products.push(element);
      });
    });
  }

  addToCart(productId: any) {
    this.customerService.addToCart(productId).subscribe({
      next: (res) => {
        this.snackBar.open('Product Added To Cart Successfully !', 'Close', {
          duration: 5000,
        });
      },
      error: (error) => {
        this.snackBar.open('Error adding product to cart.', 'Close', {
          duration: 5000,
          panelClass: 'error-snackbar',
        });
        console.error('Error adding product to cart:', error);
      },
    });
  }
}
