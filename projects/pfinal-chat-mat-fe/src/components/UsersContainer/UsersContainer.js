import React, { useEffect, useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import * as userService from "../../services/userService";

const UsersContainer = ({ friends, onAddFriend }) => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    async function fetchUsers() {
      try {
        const response = await userService.getUsers();

        const filteredUsers = response.filter(
          (user) => !friends.some((friend) => friend.id === user.id)
        );

        setUsers(filteredUsers);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    }

    fetchUsers();
  }, [friends]);

  return (
    <div className="w-3/4 h-full bg-light p-4">
      <h2>Find Friends</h2>
      <Row>
        {users.map((user) => (
          <Col key={user.id} sm={6} className="mb-3">
            <div className="p-3 border rounded">
              <strong>{user.email}</strong>
              <Button
                variant="primary"
                size="sm"
                className="ml-2"
                onClick={() => onAddFriend(user.id)}
              >
                Add Friend
              </Button>
            </div>
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default UsersContainer;
