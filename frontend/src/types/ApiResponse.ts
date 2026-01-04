export interface ApiResponse<T> {
  status: 'SUCCESS' | 'ERROR';
  statusCode: number;
  message: string;
  data: T;
}
