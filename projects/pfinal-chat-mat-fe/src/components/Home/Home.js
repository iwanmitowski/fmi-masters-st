import React, { useEffect, useState } from "react";
import { Col, Row, Button, Modal, Form } from "react-bootstrap";
import Sidebar from "../shared/Sidebar/Sidebar";
import ChatContainer from "../Chat/ChatContainer";
import UsersContainer from "../UsersContainer/UsersContainer";
import { useIdentityContext } from "../../hooks/useIdentityContext";
import * as userService from "../../services/userService";
import * as channelService from "../../services/channelService";
import * as messageService from "../../services/messageService";
import { chatTypes, roles, views } from "../../utils/constants";

const Home = () => {
  const [messages, setMessages] = useState([]);
  const [channels, setChannels] = useState([]);
  const [friends, setFriends] = useState([]);
  const [channelMembers, setChannelMembers] = useState([]);
  const [selectedChannelId, setSelectedChannelId] = useState(null);
  const [view, setView] = useState(views.CHANNELS);
  const [showCreateChannelModal, setShowCreateChannelModal] = useState(false);
  const [newChannelName, setNewChannelName] = useState("");
  const { user } = useIdentityContext();

  useEffect(() => {
    async function fetchData() {
      try {
        const users = await userService.getUsers();
        const currentUser = users.find((u) => user.email === u.email);

        if (currentUser) {
          setFriends(currentUser.friendshipsInitiatedUsers);
        }

        const channels = await channelService.getChannels(currentUser.id);
        setChannels(channels);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }

    fetchData();
  }, [user]);

  const handleSelect = async (id, type) => {
    try {
      const currentUserId = user.id;

      if (type === chatTypes.FRIEND) {
        const smallerId = Math.min(currentUserId, id);
        const largerId = Math.max(currentUserId, id);
        const channelName = `${smallerId}-${largerId}`;

        const channel = channels.find((ch) => ch.name === channelName);
        if (channel) {
          setSelectedChannelId(channel.id);
          const messages = await messageService.getMessages(channel.id);
          setMessages(messages);

          const members = await channelService.getChannelMembers(channel.id);
          setChannelMembers(members);
        }
      } else if (type === chatTypes.CHANNEL) {
        setSelectedChannelId(id);
        const messages = await messageService.getMessages(id);
        setMessages(messages);

        const members = await channelService.getChannelMembers(id);
        setChannelMembers(members);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleSendMessage = async (content) => {
    try {
      if (!selectedChannelId) {
        return;
      }

      const messagePayload = {
        content,
        channelId: selectedChannelId,
        userId: user.id,
      };
      setMessages((prevMessages) => [
        ...prevMessages,
        { ...messagePayload, user, timestamp: new Date().toISOString() },
      ]);
      await messageService.sendMessage(messagePayload);
    } catch (error) {
      console.error("Error sending message:", error);
    }
  };

  const handleAddFriend = async (friendId) => {
    try {
      await userService.addFriend(user.id, friendId);

      const updatedUsers = await userService.getUsers();
      const currentUser = updatedUsers.find((u) => u.id === user.id);

      if (currentUser) {
        setFriends(currentUser.friendshipsInitiatedUsers);
      }

      const updatedChannels = await channelService.getChannels(user.id);
      setChannels(updatedChannels);
    } catch (error) {
      console.error("Error adding friend:", error);
    }
  };

  const handleCreateChannel = async () => {
    try {
      const newChannel = await channelService.createChannel(
        user.id,
        newChannelName
      );
      setChannels((prevChannels) => [...prevChannels, newChannel]);
      setShowCreateChannelModal(false);
      setNewChannelName("");
      handleSelect(newChannel.id, chatTypes.CHANNEL);
      setView(views.CHANNELS);
    } catch (error) {
      console.error("Error creating channel:", error);
    }
  };

  const handleFindFriends = () => {
    setView(views.USERS);
  };

  const handleChannels = () => {
    setView(views.CHANNELS);
  };

  const handleAddGuestMember = async (guestId) => {
    try {
      if (!selectedChannelId) {
        return;
      }

      const guestUser = friends.find((friend) => friend.id === guestId);
      if (!guestUser) {
        console.error("Guest not found among friends");
        return;
      }

      await channelService.addGuest(selectedChannelId, user.id, guestUser.id);

      const updatedMembers = await channelService.getChannelMembers(
        selectedChannelId
      );
      setChannelMembers(updatedMembers);
    } catch (error) {
      console.error("Error adding guest member:", error);
    }
  };

  const handleDeleteChannel = async () => {
    try {
      if (!selectedChannelId) {
        return;
      }

      await channelService.deleteChannel(selectedChannelId, user.id);
      setChannels((prevChannels) =>
        prevChannels.filter((channel) => channel.id !== selectedChannelId)
      );
      setSelectedChannelId(null);
      setMessages([]);
      setChannelMembers([]);
    } catch (error) {
      console.error("Error deleting channel:", error);
    }
  };

  const handlePromoteToAdmin = async (guestId) => {
    try {
      if (!selectedChannelId) {
        return;
      }

      await channelService.promoteToAdmin(selectedChannelId, user.id, guestId);

      setChannelMembers((prevMembers) =>
        prevMembers.map((member) =>
          member.id === guestId
            ? { ...member, role: { id: roles.ADMIN, roleName: "ADMIN" } }
            : member
        )
      );
    } catch (error) {
      console.error("Unexpected error:", error);
    }
  };

  const handleRemoveGuest = async (guestId) => {
    try {
      if (!selectedChannelId) {
        return;
      }

      await channelService.removeGuest(selectedChannelId, user.id, guestId);

      setChannelMembers((prevMembers) =>
        prevMembers.filter((member) => member.id !== guestId)
      );
    } catch (error) {
      console.error("Unexpected error:", error);
    }
  };

  const handleChangeChannelName = async (newName) => {
    try {
      if (!selectedChannelId) {
        return;
      }

      await channelService.changeChannelName(
        selectedChannelId,
        user.id,
        newName
      );

      setChannels((prevChannels) =>
        prevChannels.map((ch) =>
          ch.id === selectedChannelId ? { ...ch, name: newName } : ch
        )
      );
    } catch (error) {
      console.error("Error changing channel name:", error);
    }
  };

  useEffect(() => {
    if (selectedChannelId) {
      const interval = setInterval(async () => {
        try {
          const messages = await messageService.getMessages(selectedChannelId);
          setMessages(messages);
        } catch (error) {
          console.error(error);
        }
      }, 3000);

      return () => clearInterval(interval);
    }
  }, [selectedChannelId]);

  return (
    <div className="d-flex w-100">
      <Col sm={2}>
        <Sidebar
          channels={channels}
          friends={friends}
          onSelect={handleSelect}
          onFindFriends={
            view === views.CHANNELS ? handleFindFriends : handleChannels
          }
          onCreateChannel={() => setShowCreateChannelModal(true)}
          view={view}
        />
      </Col>
      <Col sm={10}>
        {view === views.CHANNELS ? (
          <ChatContainer
            channelName={
              channels.find((ch) => ch.id === selectedChannelId)?.name
            }
            channelMembers={channelMembers}
            isOwner={channelMembers.some(
              (u) => u.id === user.id && u.role.id === roles.OWNER
            )}
            isAdmin={channelMembers.some(
              (u) => u.id === user.id && u.role.id === roles.ADMIN
            )}
            messages={messages}
            friends={friends}
            onChangeChannelName={handleChangeChannelName}
            onPromoteToAdmin={handlePromoteToAdmin}
            onRemoveGuest={handleRemoveGuest}
            onAddGuestMember={handleAddGuestMember}
            onSendMessage={handleSendMessage}
            onDeleteChannel={handleDeleteChannel}
          />
        ) : (
          <UsersContainer friends={friends} onAddFriend={handleAddFriend} />
        )}
      </Col>

      <Modal
        show={showCreateChannelModal}
        onHide={() => setShowCreateChannelModal(false)}
      >
        <Modal.Header closeButton>
          <Modal.Title>Create Channel</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group controlId="channelName">
            <Form.Label>Channel Name</Form.Label>
            <Form.Control
              type="text"
              value={newChannelName}
              onChange={(e) => setNewChannelName(e.target.value)}
              placeholder="Enter channel name"
              required
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => setShowCreateChannelModal(false)}
          >
            Cancel
          </Button>
          <Button variant="primary" onClick={handleCreateChannel}>
            Create
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default Home;
