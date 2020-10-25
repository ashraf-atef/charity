import {User} from "./user";

export class Donation {
  id: number;
  donor: User;
  beneficiary: User=new User();
  createdDate: string;
  lastModifiedDate: string;
}
