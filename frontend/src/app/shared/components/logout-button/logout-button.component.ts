import {Component} from '@angular/core';
import {AuthService} from '../../../features/auth/services/auth.service';

@Component({
  selector: 'app-logout-button',
  templateUrl: './logout-button.component.html',
  standalone: true,
  styleUrls: ['./logout-button.component.css']
})
export class LogoutButtonComponent {

  constructor(private authService: AuthService) {
  }

  logout() {
    this.authService.logout();
  }
}
