const ins: string = "Habibullah";

interface Info {
  age: number,
  name: string,
  info: object
}

class Dd implements Info {
  age = 20;
  info: object;
  name: string;

  constructor(a: number) {
    this.age = a;
  }
}

const s: object = new Dd(15);
console.log(s)
