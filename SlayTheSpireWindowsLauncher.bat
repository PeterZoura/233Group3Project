@echo off
javac buttonHandlers/*.java game/*.java logic/*.java -cp buttonHandlers;logic;game;files;RawCards
java game.GameGUI -cp RawCards
