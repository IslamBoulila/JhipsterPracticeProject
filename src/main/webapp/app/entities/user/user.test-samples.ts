import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 9969,
  login: 'xB!}@bh\\ SirY-j',
};

export const sampleWithPartialData: IUser = {
  id: 27515,
  login: '5U',
};

export const sampleWithFullData: IUser = {
  id: 6982,
  login: 'S',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
