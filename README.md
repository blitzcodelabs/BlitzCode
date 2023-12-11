# Blitz Code
Become fluent in a new programming language. Live on https://blitzcode.org
## Table of Contents
- [About](#about)
- [Requirements](#requirements)
- [Running](#running)
  - [Production](#production)
  - [Development](#development)
  - [Question Generation](#question-generation)
- [Authors](#authors)

## About
Blitz Code is a web application that helps you learn a new programming language by generating questions and answers for you to practice.  

**Tech Stack:**  
- Frontend: Next.js
- Backend: Spring Boot
- Database: PostgreSQL
- Deployment: Railway
- Question Generation: Spring Shell, OpenAI
- Authentication: Firebase
- CI/CD: Github Actions
## Requirements
- Java 21
- Node 20
- NPM 10
- PostgreSQL 13
## Running
### Production
[![Deploy on Railway](https://railway.app/button.svg)](https://railway.app/template/0R46xb?referralCode=sc7zXc)
### Development
```
cd frontend/
npm i
npm run dev
cd ..
source .env && ./gradlew bootRun
```
### Question Generation
Question Generation CLI can be set up using:
https://github.com/blitzcodelabs/BlitzCodeQuestionGen
## Authors
- [@blitzcodelabs](https://www.github.com/blitzcodelabs)
- [@airsquared](https://github.com/airsquared)
- [@ahmetmutlugun](https://github.com/ahmetmutlugun)
- [@ricedust](https://github.com/ricedust)
- [@nickfaylor](https://github.com/nickfaylor)