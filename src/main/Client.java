package main;

import userFacade.GUIWindow;
import userFacade.UserFacade;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        UserFacade menu = new UserFacade(new GUIWindow());
        menu.mainDialog();
    }
}