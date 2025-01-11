import React from "react";
import { Button } from "react-bootstrap";
import { chatTypes, views } from "../../../utils/constants";

const Sidebar = ({ channels, friends, onSelect, onFindFriends, view }) => {
  return (
    <div className="w-1/4 h-full p-4">
      <h2 className="font-bold text-lg mb-4">Channels</h2>
      <ul className="mb-8">
        {channels.map((channel) => (
          <li
            key={channel.id}
            onClick={() => onSelect(channel.id, chatTypes.CHANNEL)}
            className="cursor-pointer p-2"
          >
            {channel.name}
          </li>
        ))}
      </ul>

      <h2 className="font-bold text-lg mb-4">Friends</h2>
      <ul className="mb-8">
        {friends.map((friend) => (
          <li
            key={friend.id}
            onClick={() => onSelect(friend.id, chatTypes.FRIEND)}
            className="cursor-pointer p-2"
          >
            {friend.email}
          </li>
        ))}
      </ul>

      <Button
        variant="dark"
        onClick={onFindFriends}
        className="bg-blue-500 text-white px-4 py-2 rounded w-full"
      >
        {view === views.CHANNELS ? "Find Friends" : "Channels"}
      </Button>
    </div>
  );
};

export default Sidebar;
