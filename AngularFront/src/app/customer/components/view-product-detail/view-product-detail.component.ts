import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute } from '@angular/router';
import { UserStorageService } from '../../../services/storage/user-storage.service';

@Component({
  selector: 'app-view-product-detail',
  templateUrl: './view-product-detail.component.html',
  styleUrl: './view-product-detail.component.scss',
})
export class ViewProductDetailComponent {
  productId: number = this.activatedRoute.snapshot.params['productId'];
  product: any;
  faqList: any[] = [];
  reviewsList: any[] = [];

  constructor(
    private snackBar: MatSnackBar,
    private customerService: CustomerService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(){
    this.getProductDetails();
  }

  getProductDetails(){
    this.customerService.getProductDetailById(this.productId).subscribe( res => {
      this.product = res.productDto;
      this.product.processedImg = 'data:image/jpeg;base64,' + res.productDto.byteImg;

      this.faqList = res.faqDtoList;
      res.reviewDtoList.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
        this.reviewsList.push(element);
      });
    })
  }

  addToWishlist(){
    const wishlistDto = {
      productId: this.productId,
      userId: UserStorageService.getUserId()
    }
    this.customerService.addWishlist(wishlistDto).subscribe( res => {
      if(res.id != null){
        this.snackBar.open("Product Added to Wishlist !", 'Close', {duration: 5000});
      }else{
        this.snackBar.open("Something Went Wrong !", 'ERROR', {duration: 5000});
      }
    })
  }
}
