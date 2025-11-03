import { CanActivateFn, Router } from '@angular/router';

import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
   
  const router = inject(Router);

  const username = localStorage.getItem('loggedInUser');
  if (username) {
    return true; // user is logged in
  } else {
    alert('You must be logged in to access this page!');
    router.navigate(['/login']); // redirect to login
    return false;
    
  }
};
