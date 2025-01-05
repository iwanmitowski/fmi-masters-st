import axios from "axios";
import { globalConstants } from "../utils/constants";

const baseUrl = globalConstants.BASE_URL;

export async function getMessages(channelId) {
  try {
    const response = await axios.get(
      `${baseUrl}/channels/${channelId}/messages`
    );

    if (response.data.code === 200) {
      return response.data.data;
    } else {
      throw new Error("Failed to fetch messages");
    }
  } catch (error) {
    console.log(error);
    return [];
  }
}

export async function sendMessage(messagePayload) {
  try {
    const response = await axios.post(`${baseUrl}/messages`, messagePayload);
    if (response.data.code === 200) {
      return response.data.data;
    } else {
      throw new Error("Failed to fetch messages");
    }
  } catch (error) {
    console.log(error);
    throw error;
  }
}
