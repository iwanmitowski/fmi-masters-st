import React, { useEffect, useState } from "react";
import { Col, Row, Button, Modal } from "react-bootstrap";
import { roles } from "../../utils/constants";

const ChatContainer = ({
  channelName,
  channelMembers,
  isOwner,
  isAdmin,
  messages,
  friends,
  onSendMessage,
  onRemoveGuest,
  onPromoteToAdmin,
  onAddGuestMember,
  onChangeChannelName,
}) => {
  const [message, setMessage] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [nonMembers, setNonMembers] = useState([]);
  const [isEditingName, setIsEditingName] = useState(false);
  const [editedChannelName, setEditedChannelName] = useState(channelName);

  useEffect(() => {
    async function fetchUsers() {
      try {
        const memberIds = channelMembers.map((member) => member.id);
        const filteredUsers = friends.filter(
          (user) => !memberIds.includes(user.id)
        );
        setNonMembers(filteredUsers);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    }

    if (showModal) {
      fetchUsers();
    }
  }, [showModal, channelMembers, friends]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (message.trim()) {
      onSendMessage(message);
      setMessage("");
    }
  };

  const handleInvite = (guestId) => {
    onAddGuestMember(guestId);
    setShowModal(false);
  };

  const handleSaveName = () => {
    onChangeChannelName(editedChannelName);
    setIsEditingName(false);
  };

  return (
    <div className="w-3/4 h-full bg-yellow-100 p-4 flex flex-col">
      <Row>
        <Col sm={8}>
          <h2>
            {isOwner && isEditingName ? (
              <div className="d-flex align-items-center">
                <input
                  type="text"
                  value={editedChannelName}
                  onChange={(e) => setEditedChannelName(e.target.value)}
                  className="form-control"
                />
                <Button
                  variant="primary"
                  className="ml-2"
                  onClick={handleSaveName}
                >
                  Save
                </Button>
              </div>
            ) : (
              <span
                onClick={isOwner ? () => setIsEditingName(true) : undefined}
              >
                {channelName}
              </span>
            )}
          </h2>
        </Col>
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
            <Button
              variant="primary"
              type="submit"
              className="bg-blue-500 text-white px-4 rounded-r"
            >
              Send
            </Button>
          </form>
        </Col>
        <Col sm={4}>
          <h4>Members</h4>
          {(isOwner || isAdmin) && (
            <Button variant="primary" onClick={() => setShowModal(true)}>
              Add
            </Button>
          )}
          {channelMembers.map((member, index) => (
            <div key={index} className="mb-2">
              <strong>
                <span>{member.email}</span> <span>{member.role.roleName} </span>
                {isOwner && member.role.id === roles.GUEST ? (
                  <>
                    <Button
                      variant="danger"
                      className="ml-2"
                      onClick={() => onRemoveGuest(member.id)}
                    >
                      X
                    </Button>
                    <Button
                      variant="success"
                      className="ml-2"
                      onClick={() => onPromoteToAdmin(member.id)}
                    >
                      To Admin
                    </Button>
                  </>
                ) : null}
              </strong>
            </div>
          ))}
        </Col>
      </Row>

      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add New Member</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {nonMembers.length ? (
            nonMembers.map((user) => (
              <div
                key={user.id}
                className="d-flex justify-content-between mb-2"
              >
                <span>{user.email}</span>
                <Button
                  variant="success"
                  size="sm"
                  onClick={() => handleInvite(user.id)}
                >
                  Invite
                </Button>
              </div>
            ))
          ) : (
            <p>No users available to invite.</p>
          )}
        </Modal.Body>
      </Modal>
    </div>
  );
};

export default ChatContainer;
