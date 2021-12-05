From openjdk:11.0.11
copy ./target/getirapp-1.0.jar getirapp-1.0.jar
CMD ["java","-jar","getirapp-1.0.jar"]