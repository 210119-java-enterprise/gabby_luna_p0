package com.revature.screens;

public class HomeScreen extends Screen{

    public HomeScreen(){
        super("HomeScreen", "/home");
    }

    @Override
    public void render() {
        //Welcome message:
        System.out.println("/************************************************************************/");
        System.out.println("/*                      Welcome to the BasicATM!                        */");
        System.out.println("/* 1.) Login                                                            */");
        System.out.println("/* 2.) Register                                                         */");
        System.out.println("/* 3.) Exit                                                             */");
        System.out.println("/************************************************************************/");
        System.out.println("");

        try{
            System.out.println("> ");
            //String choice = app().getConsole().readLine();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
