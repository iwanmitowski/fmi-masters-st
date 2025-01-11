import { createContext } from "react";
import { useLocalStorage } from "../hooks/useLocalStorage";

export const IdentityContext = createContext();

export const IdentityProvider = ({ children }) => {
  const [user, setUser] = useLocalStorage("auth", null);

  const userLogin = (data) => {
    setUser(data);
  };

  return (
    <IdentityContext.Provider
      value={{
        user,
        userLogin,
        isLogged: !!user,
      }}
    >
      {children}
    </IdentityContext.Provider>
  );
};
