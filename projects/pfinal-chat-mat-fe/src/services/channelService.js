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
          role: member.role,
        };
      });
    }
    throw new Error("Failed to fetch channel members");
  } catch (error) {
    console.log(error);
    return [];
  }
}

export async function removeGuest(channelId, ownerId, guestId) {
  try {
    const response = await axios.delete(
      `${baseUrl}/channels/${channelId}/remove-guest`,
      null,
      {
        params: { ownerId, guestId },
      }
    );
    return response.data;
  } catch (error) {
    console.log(error);
    return null;
  }
}

export async function promoteToAdmin(channelId, ownerId, guestId) {
  try {
    await axios.post(
      `${baseUrl}/channels/${channelId}/promote-to-admin`,
      null,
      {
        params: { ownerId, guestId },
      }
    );
    throw new Error("Failed to promote guest to admin");
  } catch (error) {
    console.log(error);
  }
}

export async function addGuest(channelId, userId, guestId) {
  try {
    await axios.post(`${baseUrl}/channels/${channelId}/add-guest`, null, {
      params: { userId, guestId },
    });

    throw new Error("Failed to add guest");
  } catch (error) {
    console.error("Error adding guest:", error);
  }
}
