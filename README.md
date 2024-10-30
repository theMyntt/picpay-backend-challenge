# picpay-backend-challenge

- Developed with Java 21 and Springboot 3.3.2
- Maven based project

<div style="display: flex; gap: 5px">
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg"  width="48px"/>      
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/spring/spring-original.svg" width="48px"/>
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/mysql/mysql-original.svg" width="48px"/>      
</div>

## Requirements

- Java 21
- MySQL Local Instance

## Configuration

- By default, the application will try connect to `localhost:3306` at `picpay_challenge` database
- If you using another MySQL port/server, just put them at [`application.yaml`](./src/main/resources/application.yaml) in datasource section
- Before you initalizate project, run the create database in your MySQL
- After, just run Springboot (he will create all tables at database)

## Development use

- By default, application will run at `localhost:8080` port

## Architeture

- This project use Clean Arch, so the app was divided by 3 layers: Infrastructure, Domain and Application.
