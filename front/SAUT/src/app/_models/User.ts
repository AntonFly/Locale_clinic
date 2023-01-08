export interface User {
    id: number;
    email: string;
    name: string;
    surname: string;
    patronymic: string;
    role: string;
    password: string;    
  }

export interface Token {
  token: string,
  id: number,
  email: string,
  username: string,
  type: string,
  roles: string[]

}
  