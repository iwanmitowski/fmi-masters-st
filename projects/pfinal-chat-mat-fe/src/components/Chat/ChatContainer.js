import React, { useState } from "react";
import { Col, Row } from "react-bootstrap";
import { roles } from "../../utils/constants";

const ChatContainer = ({
  channelName,
  channelMembers,
  isOwner,
  messages,
  onSendMessage,
  onRemoveGuest,
  onPromoteToAdmin,
}) => {
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (message.trim()) {
      onSendMessage(message);
      setMessage("");
    }
  };

  return (
    <div className="w-3/4 h-full bg-yellow-100 p-4 flex flex-col">
      <Row>
        <h2>{channelName}</h2>
      </Row>
      <Row>
        <Col sm={8}>
          <div className="flex-1 overflow-y-auto mb-4">
            {messages.map((msg, index) => (
              <div key={index} className="mb-2">
                <strong>
                  <span>{msg.user.email}: </span>
                </strong>
                <span>{msg.content}</span>
              </div>
            ))}
          </div>
          <form onSubmit={handleSubmit} className="flex">
            <input
              type="text"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder="Enter your message..."
              className="flex-1 p-2 border border-gray-400 rounded-l"
            />
            <button
              type="submit"
              className="bg-blue-500 text-white px-4 rounded-r"
            >
              Send
            </button>
          </form>
        </Col>
        <Col sm={4}>
          <h4>Members</h4>
          {channelMembers.map((member, index) => (
            <div key={index} className="mb-2">
              <strong>
                <span>{member.email}</span>{" "}
                <span>{member.role.roleName} - </span>
                {isOwner && member.role.id === roles.GUEST ? (
                  <button
                    style={{ color: "red", cursor: "pointer" }}
                    onClick={() => onRemoveGuest(member.id)}
                  >
                    X
                  </button>
                ) : null}
                {isOwner && member.role.id === roles.GUEST ? (
                  <button
                    className="ml-2"
                    style={{ color: "green", cursor: "pointer" }}
                    onClick={() => onPromoteToAdmin(member.id)}
                  >
                    To Admin
                  </button>
                ) : null}
              </strong>
            </div>
          ))}
        </Col>
      </Row>
    </div>
  );
};

export default ChatContainer;
