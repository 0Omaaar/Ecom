import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-view-wish-list',
  templateUrl: './view-wish-list.component.html',
  styleUrl: './view-wish-list.component.scss'
})
export class ViewWishListComponent {

  products: any[] = [];

  constructor(private customerService: CustomerService){}

  ngOnInit(): void{
    this.getWishList();
  }

  getWishList(){
    this.customerService.getWishlistByUserId().subscribe( res => {
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
        this.products.push(element);
      });
    })
  }
}
