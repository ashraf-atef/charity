export class User {
  enabled: boolean;
  authorities: string  [];
  id: number;
  firstName: string
  lastName: string
  email: string
  password: string
  writeOnly: true
  phone: string
  username: string;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
}
