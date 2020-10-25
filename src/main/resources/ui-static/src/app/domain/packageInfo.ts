import {Donation} from "./donation";

export class PackageInfo {
  id: number;
  createdDate: string;
  lastModifiedDate: string;
  donation: Donation = new Donation();
  packageType: string;
  packageStatus: string;
}
