import React from "react";

const Sidebar = ({ channels, friends, onSelect }) => {
  return (
    <div className="w-1/4 h-full p-4">
      <h2 className="font-bold text-lg mb-4">Channels</h2>
      <ul className="mb-8">
        {channels.map((channel) => (
          <li
            key={channel.id}
            onClick={() => onSelect(channel.id, "channel")}
            className="cursor-pointer p-2"
          >
            {channel.name}
          </li>
        ))}
      </ul>
      <h2 className="font-bold text-lg mb-4">Friends</h2>
      <ul>
        {friends.map((friend) => (
          <li
            key={friend.id}
            onClick={() => onSelect(friend.id, "friend")}
            className="cursor-pointer p-2"
          >
            {friend.email}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
