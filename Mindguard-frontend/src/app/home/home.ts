import { Component } from '@angular/core';
//import { Moodauth } from '../moodauth';
import { RouterOutlet } from "@angular/router";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [RouterOutlet,CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  isAdmin = false;

  /*constructor(private moodauth: Moodauth) {}

  ngOnInit(): void {
    this.moodauth.user$.subscribe(user => {
      if (!user) {
        this.isAdmin = false;
        return;
      }
      const role = user.role.replace(/^ROLE_/, '').toUpperCase();
      this.isAdmin = role === 'ADMIN' || role === 'PARENT';
    });
  }
    */


}
