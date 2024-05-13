import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '59eb7904-5c5e-433a-a55b-e4d5d596a212',
};

export const sampleWithPartialData: IAuthority = {
  name: '46282547-4fea-4462-be39-24bb99a72b7c',
};

export const sampleWithFullData: IAuthority = {
  name: 'a3acfc90-5496-4c03-a29c-a673ca064150',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
