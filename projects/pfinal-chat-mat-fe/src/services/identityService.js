import { globalConstants } from "../utils/constants";
import axios from "axios";

const baseUrl = globalConstants.BASE_URL;

export async function login(user) {
  try {
    const response = await axios.post(`${baseUrl}/login`, user);
    return response.data.data;
  } catch (error) {
    console.log(error);
  }
}

export async function register(user) {
  try {
    await axios.post(`${baseUrl}/register`, user);
  } catch (error) {
    console.log(error);
  }
}
