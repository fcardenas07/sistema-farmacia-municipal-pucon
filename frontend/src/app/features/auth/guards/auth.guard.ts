import {Injectable} from '@angular/core';
import {Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (!this.authService.isAuthenticated()) {
      return this.router.createUrlTree(['/login']);
    }

    const requiredRoles = route.data['roles'] as string[];
    if (requiredRoles && requiredRoles.length > 0) {
      const userRol = this.authService.getCurrentUserRol();
      if (!userRol || !requiredRoles.includes(userRol)) {
        const homeRoute = this.authService.getHomeRoute();
        return this.router.createUrlTree([homeRoute]);
      }
    }

    return true;
  }
}
