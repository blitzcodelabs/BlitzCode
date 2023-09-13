# skeleton-starter-hilla-react-gradle

This project can be used as a starting point to create your own Hilla application with React, Spring Boot, and Gradle.
It contains all the necessary configuration and some placeholder files to get you started.

## Running the application

The project is a standard Gradle Spring Boot project.
To run it from the command line, type `gradlew bootRun` (Windows), or `./gradlew bootRun` (Mac & Linux), then open http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any Gradle project.
Then you can run the main method of `Application.java`.

## Deploying to Production

To create a production build, call `gradlew -Philla.productionMode=true build` (Windows), or `./gradlew -Philla.productionMode=true build` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources, ready to be deployed.
The file can be found in the `build/libs/` folder after the build completes.

Once the JAR file is built, you can run it using:

`java -jar build/libs/skeleton-starter-hilla-react-gradle.jar`

## Project structure

<table style="width:100%; text-align: left;">
  <tr><th>Directory</th><th>Description</th></tr>
  <tr><td><code>frontend/</code></td><td>Client-side source directory</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.html</code></td><td>HTML template</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.ts</code></td><td>Frontend 
entrypoint, bootstraps a React application</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>routes.tsx</code></td><td>React Router routes definition</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>MainLayout.tsx</code></td><td>Main 
layout component, contains the navigation menu, uses <a href="https://hilla.dev/docs/react/components/app-layout">
App Layout</a></td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>views/</code></td><td>UI view 
components</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>themes/</code></td><td>Custom  
CSS styles</td></tr>
  <tr><td><code>src/main/java/&lt;groupId&gt;/</code></td><td>Server-side 
source directory, contains the server-side Java views</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>Application.java</code></td><td>Server entry-point</td></tr>
</table>

## Useful links

- Read the documentation at [hilla.dev/docs](https://hilla.dev/docs/).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/hilla) or join our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/hilla).
