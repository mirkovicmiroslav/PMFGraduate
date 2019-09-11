export class TopMentorsList {
  mentors: TopMentors[];

  constructor() {
    this.mentors = [];
  }
}

interface TopMentors {
  name: string;
  count: number;
}
