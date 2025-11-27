export interface RespuestaPaginada<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number; // página actual
}
