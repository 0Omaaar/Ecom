import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { MatDialog } from '@angular/material/dialog';
import { PlaceOrderComponent } from '../place-order/place-order.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {

  cartItems: any[] = [];
  order: any;
  couponForm!: FormGroup;

  constructor(private router: Router,
    private fb: FormBuilder,
    private snackbar: MatSnackBar,
    private customerService: CustomerService,
    private dialog: MatDialog
  ){
  }

  ngOnInit(): void{
    this.couponForm = this.fb.group({
      code: [null, [Validators.required]]
    })

    this.getCart();
  }

  applyCoupon(){
    this.customerService.applyCoupon(this.couponForm.get('code')!.value).subscribe(res => {
      this.snackbar.open("Coupon Applied Successfully !", 'Close', {
        duration: 5000
      });
      this.getCart();
    }, error => {
      this.snackbar.open(error.error, 'Close', {
        duration: 5000
      });
    });
  }

  increaseProductQuantity(productId: any){
    this.customerService.increaseProductQuantity(productId).subscribe(res => {
      this.snackbar.open("Product Increased Successfully !", 'Close', {
        duration: 5000
      });
      this.getCart();
    })
  }

  decreaseProductQuantity(productId: any){
    this.customerService.decreaseProductQuantity(productId).subscribe(res => {
      this.snackbar.open("Product Decreased Successfully !", 'Close', {
        duration: 5000
      });
      this.getCart();
    })
  }

  getCart(){
    this.cartItems = [];
    this.customerService.getCartByUserId().subscribe(res => {
      this.order = res;
      res.cartItems.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
        this.cartItems.push(element)
      });
    })
  }

  placeOrder(){
    this.dialog.open(PlaceOrderComponent);
  }

}
