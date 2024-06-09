# Uno Game Project

## Overview
This project aims to implement the popular card game Uno using the Java programming language. Uno is a well-known game played with cards of different colors and values. Our objective is to enable Uno gameplay between a server and two clients. The server will manage functions like starting the game, distributing cards, validating moves, and synchronizing the game state. Clients will provide the user interface and allow players to make card selections. Communication between the server and clients will be facilitated using a TCP/IP-based communication protocol.

## Features
- User-friendly interface for playing Uno.
- Real-time interaction between players.
- Centralized server for game state management.
- Two client applications for player interaction.
- TCP/IP communication protocol for data exchange between server and clients.

## Components
1. **Card.java**: Represents an Uno card with attributes such as color and value.
2. **Game.form**: GUI form for the client application.
3. **Game.java**: Manages the main game logic, including shuffling the deck, dealing cards, and handling turns.
4. **Message.java**: Handles the messages exchanged between the server and clients.
5. **Server.java**: Sets up the server to manage client connections and game state.

## Acknowledgments
- The Uno card game concept and rules.
- Java programming resources and documentation.

Enjoy playing Uno!
