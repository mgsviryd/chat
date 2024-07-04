# Chat
## ✨Spring Web: Anonymous online chat✨

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/mgsviryd/chat)

## Installation

Chat requires [MYSQL](https://www.mysql.com/) driver is being installed.

Before running in IntelliJ IDEA change Run/Debug configuration (click ChatApplication on 'Runner panel') -> Edit Configurations... -> Modify options -> Override configuration properties -> (Click +) -> add:

Override configuration properties:

| Name                       | Value (for example)                                                                                                                         |
|----------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| SPRING_DATASOURCE_URL      | jdbc:mysql://localhost:3306/database?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true 
| SPRING_DATASOURCE_NAME     | root                                                                                                                            
| SPRING_DATASOURCE_PASSWORD | ****                                                                                                                            



