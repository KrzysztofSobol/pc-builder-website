import {createContext, type ReactNode, useContext,} from 'react';
import type { userResponse } from './dtos/response/userResponse.ts';

interface AuthContextType {
    user: userResponse | null;
    getUserId: () => string | null;
    getUserRole: () => string | null;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children, user }: { children: ReactNode; user: userResponse | null }) => {
    const getUserId = () => user?.userID || null;
    const getUserRole = () => user?.role || null;

    return (
        <AuthContext.Provider value={{ user, getUserId, getUserRole }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};