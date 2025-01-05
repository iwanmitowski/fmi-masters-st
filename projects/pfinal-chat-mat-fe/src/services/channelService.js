import axios from "axios";
import { globalConstants } from "../utils/constants";

const baseUrl = globalConstants.BASE_URL;

export async function getChannels(userId) {
  try {
    const response = await axios.get(`${baseUrl}/users/${userId}/channels`);
    if (response.data.code === 200) {
      return response.data.data;
    } else {
      throw new Error("Failed to fetch channels");
    }
  } catch (error) {
    console.log(error);
    return [];
  }
}

export async function getChannelMembers(channelId) {
  try {
    const response = await axios.get(
      `${baseUrl}/channels/${channelId}/members`
    );
    if (response.data.code === 200) {
      return response.data.data.map((member) => {
        return {
          ...member.user,
          ...member.role,
        };
      });
    }
    throw new Error("Failed to fetch channel members");
  } catch (error) {
    console.log(error);
    return [];
  }
}
