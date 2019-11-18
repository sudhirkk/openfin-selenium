package sk.samplers.fix;

import quickfix.*;


public class FixApplication extends MessageCracker implements Application
{
    @Override
    public void onCreate(SessionID sessionID) {

    }

    @Override
    public void onLogon(SessionID sessionID) {

    }

    @Override
    public void onLogout(SessionID sessionID) {

    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {

    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {

    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {

    }

    public void fromApp(Message message, SessionID sessionID)
            throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        crack(message, sessionID);
    }

    // Using annotation
    @Handler
    public void myEmailHandler(quickfix.fix50.Email email, SessionID sessionID) {
        // handler implementation
    }

    // By convention (notice different version of FIX. It's an error to have two handlers for the same message)
    // Convention is "onMessage" method with message object as first argument and SessionID as second argument
    public void onMessage(quickfix.fix44.Email email, SessionID sessionID) {
        // handler implementation
    }
}