import axios from "axios";
import { globalConstants } from "../utils/constants";

const baseUrl = globalConstants.BASE_URL;

export async function getUsers(search = "") {
  try {
    const response = await axios.get(`${baseUrl}/users`, {
      params: { search },
    });

    if (response.data.status === "success" && response.data.code === 200) {
      return response.data.data;
    }
  } catch (error) {
    console.error("Error fetching users:", error);
    return [];
  }
}
