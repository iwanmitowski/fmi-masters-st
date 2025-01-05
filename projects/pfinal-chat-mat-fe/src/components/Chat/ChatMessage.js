import React from "react";

const ChatMessage = ({ user, content }) => {
  return (
    <div className="mb-2">
      <span className="font-bold">{user}: </span>
      <span>{content}</span>
    </div>
  );
};

export default ChatMessage;
