namespace Dto {
  interface Department {
    id: number;
    name: string;
    description: string;
    createTime: Date;
    updateTime: Date;
  }

  interface Page<T> {
    currentPage: number;
    data: Array<T>;
    totalPage: number;
  }

  interface Group {
    id: number;
    name: string;
    description: string;
    createTime: Date;
    updateTime: Date;
  }

  interface Method {
    id: number;
    name: string;
    description: string;
    createTime: Date;
    updateTime: Date;
  }

  interface News {
    id: number;
    name: string;
    description: string;
    category: Category;
    createTime: Date;
    updateTime: Date;
  }

  interface Position {
    id: number;
    name: string;
    description: string;
    createTime: Date;
    updateTime: Date;
  }

  interface Record {
    id: number;
    createTime: Date;
    updateTime: Date;
    owner: number;
    audience: number;
    schemeId: number;
    scoreMap: Map;
  }

  interface Category {
    id: number;
    name: string;
    description: string;
    createTime: Date;
    updateTime: Date;
  }

  interface Scheme {
    id: number;
    name: string;
    methodId: number;
    groupId: number;
    quotaIds: number[];
    frequency: string;
    description: string;
    createTime: Date;
    updateTime: Date;
  }

  interface Quota {
    id: number;
    name: string;
    description: string;
    score: number;
    weight: number;
    createTime: Date;
    updateTime: Date;
    hasScore: number;
  }

  interface User {
    id: number;
    name: string;
    username: string;
    mobile: string;
    position: Position;
    department: Department;
    gender: boolean;
  }
}
